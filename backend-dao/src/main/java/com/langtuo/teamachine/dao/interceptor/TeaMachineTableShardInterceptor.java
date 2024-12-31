package com.langtuo.teamachine.dao.interceptor;

import cn.hutool.core.bean.BeanUtil;
import com.langtuo.teamachine.dao.annotation.TeaMachineTableShard;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static com.langtuo.teamachine.internal.constant.CommonConsts.LIST_INDEX_FIRST;
import static com.langtuo.teamachine.internal.constant.CommonConsts.MAP_KEY_PO_LIST;

@Intercepts({@Signature(
    type = StatementHandler.class,
    method = "prepare",
    args = {Connection.class, Integer.class}
)})
@Slf4j
public class TeaMachineTableShardInterceptor implements Interceptor {
    /**
     *
     */
    private static final ReflectorFactory defaultReflectorFactory = new DefaultReflectorFactory();

    /**
     *
     */
    private static final String DELEGATE_MAPPER_STATEMENT = "delegate.mappedStatement";
    private static final String DELEGATE_BOUND_SQL = "delegate.boundSql";
    private static final String DELEGATE_BOUND_SQL_SQL = "delegate.boundSql.sql";

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {}

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        String originalSql = statementHandler.getBoundSql().getSql();

        // 获取 Mapper 元信息
        MetaObject metaObject = MetaObject.forObject(statementHandler,
                SystemMetaObject.DEFAULT_OBJECT_FACTORY,
                SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY,
                defaultReflectorFactory);
        // 获取 MappedStatement
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue(DELEGATE_MAPPER_STATEMENT);
        // 获取 SQL 元数据
        BoundSql boundSql = (BoundSql) metaObject.getValue(DELEGATE_BOUND_SQL);

        // 拦截分表逻辑，获取注解
        TeaMachineTableShard annotation = getAnnotation(mappedStatement);
        // 当前方法不包含注解，走默认
        if (annotation == null || !annotation.tableShardOpen()) {
            return invocation.proceed();
        }
        System.out.printf("$$$$$ teaMachineTableShardInterceptor|getSql|originalSql=%s\n", originalSql);

        // 获取入参值，根据入参值进行分表
        Map<String, String> columnValueMap = getColumnValue(annotation.columns(), boundSql);
        // 获取替换表名称
        String shardName = annotation.shardName();
        // 获取原始表名称
        String originName = annotation.originName();
        // 获取新表名称
        String newName = originName;
        // 新表名称
        String suffix = computeTableSuffix(columnValueMap); // 根据实际逻辑计算表后缀;
        if (!StringUtils.isBlank(suffix)) {
            newName = shardName + suffix;
        }

        String modifiedSql = originalSql.replaceAll(originName, newName);
        System.out.printf("$$$$$ teaMachineTableShardInterceptor|replaceSql|modifiedSql=%s\n", modifiedSql);

        // 拦截并替换 SQL
        metaObject.setValue(DELEGATE_BOUND_SQL_SQL, modifiedSql);
        return invocation.proceed();
    }

    private String computeTableSuffix(Map<String, String> columnValueMap) {
        String shopGroupCode = columnValueMap.get("shopGroupCode");
        if (StringUtils.isBlank(shopGroupCode)) {
            return null;
        }

        int tmp = 0;
        for (char c : shopGroupCode.toCharArray()) {
            tmp = tmp + c;
        }

        int suffix = tmp % 4;
        return "0" + String.valueOf(suffix);
    }

    private TeaMachineTableShard getAnnotation(MappedStatement mappedStatement) throws ClassNotFoundException {
        String id = mappedStatement.getId();
        String className = id.substring(0, id.lastIndexOf("."));
        String methodName = id.substring(id.lastIndexOf(".") + 1);
        // 获取 pageHelper 拦截方法
        if (methodName.endsWith("_COUNT")) {
            methodName = methodName.replace("_COUNT", "");
        }
        Class<?> aClass = Class.forName(className);
        Method[] declaredMethods = aClass.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.getName().equals(methodName)) {
                return declaredMethod.getAnnotation(TeaMachineTableShard.class);
            }
        }
        return null;
    }

    /**
     * 获取 SQL 参数值
     *
     * @param columns
     *     参数名称
     * @param boundSql
     *     SQL元数据
     * @return
     *     参数列表
     */
    private Map<String, String> getColumnValue(String[] columns, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        if (parameterObject == null) {
            throw new IllegalArgumentException("illegal argument for table shard");
        }

        // 判断参数类型获取参数值
        Map<String, Object> paramMap;
        if (parameterObject instanceof MapperMethod.ParamMap) { // 查询条件是 ParamMap
            paramMap = (MapperMethod.ParamMap) parameterObject;
        } else if (parameterObject instanceof Map) { // 查询条件是 Map
            paramMap = (Map) parameterObject;
        } else { // 查询条件是其他 Bean
            paramMap = BeanUtil.beanToMap(parameterObject);
        }

        // 为了批量插入记录的分库分表添加的额外逻辑
        if (paramMap.containsKey(MAP_KEY_PO_LIST)) {
            List<Object> poList = (List) paramMap.get(MAP_KEY_PO_LIST);
            Object obj = poList.get(LIST_INDEX_FIRST);
            paramMap = convert(obj);
        }

        Map<String, String> resultMap = new HashMap<>();
        for (int i = 0; i < columns.length; i++) {
            if (paramMap.containsKey(columns[i])) {
                resultMap.put(columns[i], String.valueOf(paramMap.get(columns[i])));
            } else {
                resultMap.put(columns[i], null);
            }
        }
        return resultMap;
    }

    private Map<String, Object> convert(Object obj) {
        Map<String, Object> map = new HashMap<>();
        try {
            Class<?> cls = obj.getClass();
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        } catch (Exception e) {
            log.error("convertObjToMap|fatal|" + e.getMessage());
        }
        return map;
    }
}
