package com.langtuo.teamachine.dao.typehandler;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于将表中的extra_info字段的值，在varchar和Map之间相互转换
 */
public class MapStringTypeHandler extends BaseTypeHandler<Map<String, String>> {
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, Map<String, String> stringStringMap,
            JdbcType jdbcType) throws SQLException {
        if (stringStringMap == null) {
            preparedStatement.setString(i, StringUtils.EMPTY);
        } else {
            preparedStatement.setString(i, buildString(stringStringMap));
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Map<String, String> parameter,
            JdbcType jdbcType) throws SQLException {
        //won't go here.
    }

    @Override
    public Map<String, String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return parseMapString(rs.getString(columnName));
    }

    @Override
    public Map<String, String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return parseMapString(rs.getString(columnIndex));
    }

    @Override
    public Map<String, String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return parseMapString(cs.getString(columnIndex));
    }

    private Map<String, String> parseMapString(String str) {
        try {
            Map<String, String> properties = new HashMap<>();
            if (StringUtils.isBlank(str)) {
                return properties;
            }
            String[] kvPairs = StringUtils.split(str, ";");
            for (String pair : kvPairs) {
                if (StringUtils.isBlank(pair)) {
                    continue;
                }
                String[] kv = StringUtils.split(pair, ":");
                String k = decode(kv[0]);
                String v = decode(kv[1]);

                properties.put(k, v);
            }

            return properties;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String buildString(Map<String, String> properties) {
        if (properties == null || properties.isEmpty()) {
            return StringUtils.EMPTY;
        }

        StringBuilder builder = new StringBuilder();

        for (Map.Entry<String, String> entry : properties.entrySet()) {
            if (StringUtils.isBlank(entry.getKey()) || StringUtils.isBlank(entry.getValue())) {
                continue;
            }
            builder.append(";").append(encode(entry.getKey())).append(":").append(encode(entry.getValue()));
        }
        return builder.length()==0?"":builder.substring(1);
    }

    private static String encode(String str) {
        str = StringUtils.replace(str, "#", "#23");
        str = StringUtils.replace(str, ":", "#3A");
        str = StringUtils.replace(str, ";", "#3B");

        return str;
    }

    private static String decode(String str) {
        str = StringUtils.replace(str, "#3A", ":");
        str = StringUtils.replace(str, "#3B", ";");
        str = StringUtils.replace(str, "#23", "#");

        return str;
    }
}
