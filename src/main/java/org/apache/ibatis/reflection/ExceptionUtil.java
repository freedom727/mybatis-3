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
package org.apache.ibatis.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * 在反射或代理相关的调用中，真实的异常常常被框在包装异常里，例如：
 *
 * InvocationTargetException：Method.invoke(...) 时，如果被调用的方法抛出异常，JVM 会把它包装成 InvocationTargetException，真实异常放在 getTargetException()。
 *
 * UndeclaredThrowableException：动态代理（Proxy）或某些反射/代理框架会把非声明的异常包装在 UndeclaredThrowableException 里，通过 getUndeclaredThrowable() 可取出真实异常。
 *
 * unwrapThrowable 的功能就是 递归剥离这些常见的包装层，返回“最里面”的真实 Throwable，方便上层判断处理（比如判断是不是某个具体业务异常或抛出可读的错误信息）。
 *
 * @author Clinton Begin
 */
public class ExceptionUtil {

  private ExceptionUtil() {
    // Prevent Instantiation
  }

  /**
   * 去掉异常的包装
   *
   * @param wrapped 被包装的异常
   * @return 去除包装后的异常
   */
  public static Throwable unwrapThrowable(Throwable wrapped) {
    Throwable unwrapped = wrapped;
    while (true) {
      if (unwrapped instanceof InvocationTargetException) {
        unwrapped = ((InvocationTargetException) unwrapped).getTargetException();
      } else if (unwrapped instanceof UndeclaredThrowableException) {
        unwrapped = ((UndeclaredThrowableException) unwrapped).getUndeclaredThrowable();
      } else {
        return unwrapped;
      }
    }
  }

}
