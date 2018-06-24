package io.xiaoyaoyou.xmall.common.mybatis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaoyaoyou on 2016/7/14.
 */
@MappedTypes({Map.class})
@MappedJdbcTypes({JdbcType.VARCHAR})
public class MapTypeHandler implements TypeHandler<Map<String, String>> {
    private static ObjectMapper mapper = new ObjectMapper();

    @Override
    public void setParameter(PreparedStatement ps, int i, Map<String, String> parameter, JdbcType jdbcType) throws SQLException{
        if (parameter == null) {
            ps.setNull(i, Types.VARCHAR);
        } else {
            try{
                ps.setString(i, mapper.writeValueAsString(parameter));
            }catch (JsonProcessingException e) {
                ps.setNull(i, Types.VARCHAR);
            }
        }
    }

    @Override
    public Map<String, String> getResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        Map<String, String> result = new HashMap<>();
        try {
            result = mapper.readValue(value, new TypeReference<Map<String, String>>(){});
        }catch (IOException e) {

        }

        return result;
    }

    @Override
    public Map<String, String> getResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        Map<String, String> result = new HashMap<>();
        try {
            result = mapper.readValue(value, new TypeReference<Map<String, String>>(){});
        }catch (IOException e) {

        }

        return result;
    }

    @Override
    public Map<String, String> getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        Map<String, String> result = new HashMap<>();
        try {
            result = mapper.readValue(value, new TypeReference<Map<String, String>>(){});
        }catch (IOException e) {

        }

        return result;
    }
}
