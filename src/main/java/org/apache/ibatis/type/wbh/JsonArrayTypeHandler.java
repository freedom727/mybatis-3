/**
 *    Copyright 2009-2025 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.type.wbh;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * xml中#{ids, typeHandler=com.xxx.JsonArrayTypeHandler}
 * 或<result column="ids"
 *         property="ids"
 *         typeHandler="com.xxx.JsonArrayTypeHandler"/>
 */
public class JsonArrayTypeHandler extends BaseTypeHandler<List<Integer>> {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  @Override
  public void setNonNullParameter(
          PreparedStatement ps,
          int i,
          List<Integer> parameter,
          JdbcType jdbcType) throws SQLException {

    try {
      ps.setString(i, MAPPER.writeValueAsString(parameter));
    } catch (Exception e) {
      throw new SQLException("Failed to convert List<Integer> to JSON.", e);
    }
  }

  @Override
  public List<Integer> getNullableResult(
          ResultSet rs,
          String columnName) throws SQLException {

    return parse(rs.getString(columnName));
  }

  @Override
  public List<Integer> getNullableResult(
          ResultSet rs,
          int columnIndex) throws SQLException {

    return parse(rs.getString(columnIndex));
  }

  @Override
  public List<Integer> getNullableResult(
          CallableStatement cs,
          int columnIndex) throws SQLException {

    return parse(cs.getString(columnIndex));
  }

  private List<Integer> parse(String json) throws SQLException {
    if (json == null || json.isEmpty()) {
      return Collections.emptyList();
    }
    try {
      return MAPPER.readValue(
              json,
              new com.fasterxml.jackson.core.type.TypeReference<List<Integer>>() {}
      );
    } catch (Exception e) {
      throw new SQLException("Failed to parse JSON array: " + json, e);
    }
  }
}
