package com.langtuo.teamachine.web.testor;

import com.langtuo.teamachine.dao.helper.SqlSessionFactoryHelper;
import com.langtuo.teamachine.dao.mapper.user.AdminMapper;
import com.langtuo.teamachine.dao.po.user.AdminPO;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

public class POTestor {
    public static void main(String args[]) {
//        insert();
        select();
//        update();
    }

    public static void insert() {
        SqlSession sqlSession = SqlSessionFactoryHelper.getSqlSession();
        AdminMapper mapper = sqlSession.getMapper(AdminMapper.class);

        AdminPO po = null;

        po = new AdminPO();
        po.setLoginName("jiaqing001");
        po.setLoginPass("pass001");
        po.setRoleCode("role_sys_super_admin");
        po.setOrgName("总公司");
        po.setComment("111222");
        po.setTenantCode("tenant_001");
        po.setExtraInfo(new HashMap<String, String>(){{}});
        mapper.insert(po);

        po = new AdminPO();
        po.setLoginName("jiaqing002");
        po.setLoginPass("pass002");
        po.setRoleCode("role_sys_super_admin");
        po.setOrgName("总公司");
        po.setComment("333444");
        po.setTenantCode("tenant_001");
        po.setExtraInfo(new HashMap<String, String>(){{}});
        mapper.insert(po);

        sqlSession.commit();
        sqlSession.close();
    }

    public static void select() {
        SqlSession sqlSession = SqlSessionFactoryHelper.getSqlSession();
        AdminMapper mapper = sqlSession.getMapper(AdminMapper.class);

        List<AdminPO> list = mapper.selectList("tenant_001");
        for (AdminPO po : list) {
            System.out.printf("$$$$$ list->po: %s\n", po);
        }

        AdminPO po = mapper.selectOne("tenant_001", "role_tenant_admin");
        System.out.printf("$$$$$ po: %s\n", po);

        sqlSession.commit();
        sqlSession.close();
    }

    public static void update() {
        SqlSession sqlSession = SqlSessionFactoryHelper.getSqlSession();
        AdminMapper mapper = sqlSession.getMapper(AdminMapper.class);

        AdminPO po = new AdminPO();
        po.setRoleCode("role_tenant_admin");
        po.setTenantCode("tenant_001");
        po.setExtraInfo(new HashMap<String, String>(){{
            put("aaa", "bbb");
        }});
        mapper.update(po);

        sqlSession.commit();
        sqlSession.close();
    }

//    public static void testBySpring() {
//        ApplicationContext context = startBySpring();
//
//        AdminMapper mapper =
//                (AdminMapper) context.getBean(AdminMapper.class);
//        AdminPOjo AdminPOjo = mapper.getOne("machine_001");
//        System.out.printf("DaoDemo#testBySpring hotelGuestPojo=%s\n", AdminPOjo);
//
//        stopBySpring(context);
//    }
//
//    public static ApplicationContext startBySpring() {
//        ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-config.xml");
//        if (context == null) {
//            throw new RuntimeException("DaoDemo#startBySpring初始化Spring失败");
//        }
//        return context;
//    }
//
//    public static void stopBySpring(ApplicationContext context) {
//        if (context == null) {
//            return;
//        }
//        if (context instanceof ClassPathXmlApplicationContext) {
//            ((ClassPathXmlApplicationContext) context).close();
//        } else {
//            System.out.println("DaoDemo#stopBySpring上下文参数异常");
//        }
//    }
}
