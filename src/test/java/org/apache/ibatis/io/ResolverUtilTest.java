package org.apache.ibatis.io;

import org.apache.ibatis.type.TypeHandler;
import org.junit.jupiter.api.Test;

public class ResolverUtilTest {

    @Test
    public void test() {
        ResolverUtil<Object> resolver = new ResolverUtil<>();
        resolver.find(new ResolverUtil.IsA(TypeHandler.class), "org.apache.ibatis.type");

    }
}
