/**
 *    Copyright 2009-2016 the original author or authors.
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
package org.apache.ibatis.type;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 匹配的 Java Type 类型的注解。
 *
 * 为什么 MyBatis 不删掉 @MappedTypes？
 * 原因 1️⃣：TypeReference 有局限
 * TypeReference 只能解决：BaseTypeHandler<T>这种 单泛型、直接继承 的情况。
 *
 * 但下面这些情况不行：
 * class MyHandler extends BaseTypeHandler<List<String>> // 泛型嵌套
 * class MyHandler<T> extends BaseTypeHandler<T>         // 泛型未绑定
 * class MyHandler implements TypeHandler<String>        // 不继承 BaseTypeHandler
 *
 * 👉 这些场景 必须用 @MappedTypes
 *
 * 原因 2️⃣：一个 TypeHandler 映射多个 Java 类型
 * @MappedTypes({Integer.class, int.class})
 * public class IntTypeHandler extends BaseTypeHandler<Integer> {
 * }
 * @MappedTypes({LocalDate.class, LocalDateTime.class})
 * public class TimeTypeHandler extends BaseTypeHandler<Object> {
 * }
 * TypeReference 只能推断一个类型
 *
 * 原因 3️⃣：非泛型写法 / 老代码兼容
 * MyBatis 非常重视向后兼容：iBatis 时代/大量历史 TypeHandler/注解方式更稳定、显式
 *
 * @author Eduardo Macarron
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)// 注册到类
public @interface MappedTypes {
  /**
   * @return 匹配的 Java Type 类型的数组
   */
  Class<?>[] value();
}
