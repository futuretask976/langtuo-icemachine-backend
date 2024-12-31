package com.langtuo.teamachine.dao.helper;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;

public class SqlSessionFactoryHelper {
    private static volatile SqlSessionFactory sqlSessionFactory;

    /**
     * 从 SqlSessionFactory 中获取 SqlSession
     * @return SqlSession
     */
    public static SqlSession getSqlSession() {
        if (sqlSessionFactory == null) {
            synchronized (SqlSessionFactoryHelper.class) {
                if (sqlSessionFactory == null) {
                    initSqlSession();
                }
            }
        }
        return sqlSessionFactory.openSession();
    }

    /**
     * 从 XML 中构建 SqlSessionFactory
     */
    private static void initSqlSession() {
        InputStream inputStream = null;
        try {
            ResourceLoader resourceLoader = new DefaultResourceLoader();
            inputStream = resourceLoader.getResource("mybatis/mybatis-config.xml").getInputStream();
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
