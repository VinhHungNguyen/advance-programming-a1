package com.hung;

/**
 * Created by hungnguyen on 4/1/17.
 */
public abstract class Participant {

    protected Integer id;
    protected String name;
    protected String state;
    protected int age;

    public Participant(Integer id, String name, String state, int age) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.age = age;
    }

    @Override
    public boolean equals(Object obj) {
        Participant p = (Participant) obj;
        return id == p.getId();
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
