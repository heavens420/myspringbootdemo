//package com.zlx.crud.utils;
//
//import com.sun.deploy.util.StringUtils;
//import org.apache.ibatis.type.BaseTypeHandler;
//import org.apache.ibatis.type.JdbcType;
//
//import java.sql.CallableStatement;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
///**
// * 将数据库中的string类型直接转换为集合或数组等类型
// */
//public class ListTypeHandler extends BaseTypeHandler<List<String>> {
//
//    @Override
//    public void setNonNullParameter(PreparedStatement preparedStatement, int i, List<String> strings, JdbcType jdbcType) throws SQLException {
//        String hobbys = StringUtils.join(Collections.singleton(strings), ",");
//        preparedStatement.setString(i,hobbys);
//    }
//
//    @Override
//    public List<String> getNullableResult(ResultSet resultSet, String s) throws SQLException {
//        return Arrays.asList(resultSet.getString(s).split(","));
//    }
//
//    @Override
//    public List<String> getNullableResult(ResultSet resultSet, int i) throws SQLException {
//        return Arrays.asList(resultSet.getString(i).split(","));
//    }
//
//    @Override
//    public List<String> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
//        String hobbys = callableStatement.getString(i);
//        return Arrays.asList(hobbys.split(","));
//    }
//}
