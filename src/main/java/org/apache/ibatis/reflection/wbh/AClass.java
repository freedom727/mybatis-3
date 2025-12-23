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
package org.apache.ibatis.reflection.wbh;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class AClass implements AInterface<String> {
    @Override
    public void func(String s) {
        System.out.println(s);
    }

    private List<String> list;

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public static void main(String[] args) throws Exception {
        AClass obj = new AClass();
        Method func = AClass.class.getMethod("func", String.class);
        System.out.println();
        func.invoke(obj, "AAA");
        System.out.println(func.isBridge());
        func = AClass.class.getMethod("func", Object.class);
        func.invoke(obj, "BBB");
        System.out.println(func.isBridge());
        System.out.println(AClass.class.isAssignableFrom(AInterface.class));
        // 确定此类对象表示的类或接口是否与指定的class参数表示的类或接口相同，或者此类是该类或接口的超类或超接口。
        // 可转让，表示CLS类型的对象是否可以分配给该类的对象，子类对象可以分配给超类 AInterface a = new AClass();
        System.out.println(AInterface.class.isAssignableFrom(AClass.class));

        Field list = AClass.class.getDeclaredField("list");
        Class<?> type = list.getType();
        System.out.println(type);
        Type genericType = list.getGenericType();
        System.out.println(genericType);
        System.out.println(genericType instanceof ParameterizedType);
    }
}
