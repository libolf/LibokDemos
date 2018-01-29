package com.libo.libokdemos.Bean;

/**
 * Created by libok on 2018-01-26.
 */

public class Person {
    private int id;
    private String name;
    private int age;

    public int getId() {
        return id;
    }

    public Person setId(int id) {
        this.id = id;
        return this;
    }

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
}
