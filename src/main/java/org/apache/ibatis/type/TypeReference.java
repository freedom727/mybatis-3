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
package org.apache.ibatis.type;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 引用泛型抽象类。目的很简单，就是解析类上定义的泛型
 * 如IntegerTypeHandler，解析的rawType为Integer
 *
 * References a generic type.
 *
 * @param <T> the referenced type
 * @since 3.1.0
 * @author Simone Tripodi
 */
public abstract class TypeReference<T> {

  /**
   * 类定义上的泛型T，如IntegerTypeHandler对应java.lang.Integer
   */
  private final Type rawType;

  protected TypeReference() {
    rawType = getSuperclassTypeParameter(getClass());
  }

  Type getSuperclassTypeParameter(Class<?> clazz) {
    // 【1】从父类中获取 <T>
    // 获取泛型父类，如果父类没有泛型，则获取父类的类
    Type genericSuperclass = clazz.getGenericSuperclass();
    if (genericSuperclass instanceof Class) {
      // 能满足 instanceof Class这个条件的，例如 GenericTypeSupportedInHierarchiesTestCase.CustomStringTypeHandler 这个类
      // public static final class CustomStringTypeHandler extends StringTypeHandler，获取到的genericSuperclass就是StringTypeHandler，然后再往上获取，直到获取到有泛型的父类

      // try to climb up the hierarchy until meet something useful
      if (TypeReference.class != genericSuperclass) {
        return getSuperclassTypeParameter(clazz.getSuperclass());
      }

      throw new TypeException("'" + getClass() + "' extends TypeReference but misses the type parameter. "
        + "Remove the extension or add a type parameter to it.");
    }

    // 【2】获取 <T>
    Type rawType = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
    // TODO remove this when Reflector is fixed to return Types
    if (rawType instanceof ParameterizedType) {
      // 必须是泛型，才获取 <T>
      rawType = ((ParameterizedType) rawType).getRawType();
    }

    return rawType;
  }

  public final Type getRawType() {
    return rawType;
  }

  @Override
  public String toString() {
    return rawType.toString();
  }

}
