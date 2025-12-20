package org.apache.ibatis.type.wbh;

import java.util.Arrays;

public enum CatEnum {
    CAT_NEN("阿恁"),// ordinal=0
    CAT_BAI("小白"),// ordinal=1
    CAT_TIAN("王天");

    private final String name;

    CatEnum(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        System.out.println(CatEnum.CAT_NEN.ordinal());
        System.out.println(CatEnum.CAT_NEN.name());// CAT_NEN
        System.out.println(Enum.valueOf(CatEnum.class, "CAT_NEN").ordinal());
        System.out.println(CatEnum.CAT_BAI.ordinal());
        System.out.println(CatEnum.CAT_TIAN.ordinal());
        System.out.printf(Arrays.toString(CatEnum.class.getEnumConstants()));
    }
}
