package com.gizwits.generic_test;


public class Person<T> {
    private String name;
    private int age;
    private T extInfo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public T getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(T extInfo) {
        this.extInfo = extInfo;
    }
}
