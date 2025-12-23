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


import java.util.List;

public class MySubject {
    private List<String> list;
    private String name;
    private List<SubObject> subObjects;

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SubObject> getSubObjects() {
        return subObjects;
    }

    public void setSubObjects(List<SubObject> subObjects) {
        this.subObjects = subObjects;
    }

    public static class SubObject {
        private List<Integer> smallList;

        public List<Integer> getSmallList() {
            return smallList;
        }

        public void setSmallList(List<Integer> smallList) {
            this.smallList = smallList;
        }
    }

}
