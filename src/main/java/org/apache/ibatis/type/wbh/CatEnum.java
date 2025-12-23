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
