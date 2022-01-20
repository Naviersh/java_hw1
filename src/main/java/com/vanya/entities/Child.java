package com.vanya.entities;

import com.vanya.requests.ChildRequests;

import javax.persistence.*;

@Table(name = "children")
@Entity
public class Child {
    @Id
    @GeneratedValue
    public int id;

    @ManyToOne
    @JoinColumn(name = "parentsId")
    public Parents parents;

    public String fullname;

    public Child() {
    }

    public Child(String fullname, Parents parents, int age) {
        this.fullname = fullname;
        this.parents = parents;
        this.age = age;
    }

    public int age;

    @ManyToOne
    @JoinColumn(name = "schoolId")
    public School school;

    public void save_child() {
        new ChildRequests().saveChild(this);
    }

    public void update_child() {
        new ChildRequests().updateChild(this);
    }

}