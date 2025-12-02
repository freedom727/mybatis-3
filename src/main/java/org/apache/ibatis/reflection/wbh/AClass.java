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
