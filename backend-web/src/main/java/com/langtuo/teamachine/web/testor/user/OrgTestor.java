package com.langtuo.teamachine.web.testor.user;

import com.google.common.collect.Maps;
import com.langtuo.teamachine.dao.helper.SqlSessionFactoryHelper;
import com.langtuo.teamachine.dao.mapper.user.OrgMapper;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.assertj.core.util.Lists;

import java.util.List;
import java.util.Map;

public class OrgTestor {
    public static void main(String args[]) {
//        insert();
        select();
    }

    public static void insert() {
        SqlSession sqlSession = SqlSessionFactoryHelper.getSqlSession();
        OrgMapper mapper = sqlSession.getMapper(OrgMapper.class);

        sqlSession.commit();
        sqlSession.close();
    }

    public static void select() {
        SqlSession sqlSession = SqlSessionFactoryHelper.getSqlSession();
        OrgMapper mapper = sqlSession.getMapper(OrgMapper.class);

        Map<String, OrgNode> orgNodeMap = Maps.newHashMap();
        List<OrgPO> list = mapper.selectList("tenant_001");
        for (OrgPO po : list) {
            OrgNode orgNode = new OrgNode();
            orgNode.orgName = po.getOrgName();
            orgNode.parentOrgName = po.getParentOrgName();
            orgNodeMap.put(orgNode.orgName, orgNode);
        }
        for (Map.Entry<String, OrgNode> entry : orgNodeMap.entrySet()) {
            OrgNode orgNode = entry.getValue();
            String parentOrgName = orgNode.getParentOrgName();
            if (StringUtils.isBlank(parentOrgName)) {
                continue;
            }
            OrgNode parentOrgNode = orgNodeMap.get(parentOrgName);
            orgNode.setParent(parentOrgNode);
            if (parentOrgNode.getChildren() == null) {
                parentOrgNode.setChildren(Lists.newArrayList());
            }
            parentOrgNode.getChildren().add(orgNode);
        }

        System.out.println(orgNodeMap);

        sqlSession.commit();
        sqlSession.close();
    }

    @Data
    static class OrgNode {
        private String orgName;

        private String parentOrgName;

        private OrgNode parent;

        private List<OrgNode> children;
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
