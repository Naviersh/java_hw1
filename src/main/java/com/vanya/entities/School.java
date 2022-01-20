package com.vanya.entities;

import com.vanya.requests.SchoolRequests;

import javax.persistence.*;

@Table(name = "school")
@Entity
public class School {
    @Id
    @GeneratedValue
    public int id;

    public School() {
    }

    public School(String number, Address address) {
        this.number = number;
        this.address = address;
    }

    public String number;

    @ManyToOne
    @JoinColumn(name = "addressId")
    public Address address;

    public void save_school() {
        new SchoolRequests().saveSchool(this);
    }
}
