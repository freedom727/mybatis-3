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
package org.apache.ibatis.autoconstructor;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AutoConstructorMapper {
  @Select("SELECT * FROM subject WHERE id = #{id}")
  PrimitiveSubject getSubject(final int id);

  @Select("SELECT * FROM subject WHERE id = #{subject.subObjects[0].smallList[0]}")
  PrimitiveSubject getMySubject(@Param("subject") MySubject subject);

  // 如果配置useActualParamName=false，那么参数名就是参数的索引，从0开始,第二个参数就能填2，一般可以填arg2，更保险一点的则是param3，非下标，自然序数
  @Select("SELECT * FROM subject WHERE id = #{arg0} and name = #{param3}")
  PrimitiveSubject getMySubject1(final int id, int id2, String name);

  @Select("SELECT * FROM subject")
  List<PrimitiveSubject> getSubjects();

  @Select("SELECT * FROM subject")
  List<AnnotatedSubject> getAnnotatedSubjects();

  @Select("SELECT * FROM subject")
  List<BadSubject> getBadSubjects();

  @Select("SELECT * FROM extensive_subject")
  List<ExtensiveSubject> getExtensiveSubject();
}
