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
