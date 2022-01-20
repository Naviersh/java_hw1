package com.vanya.entities;

import com.vanya.requests.ParentsRequests;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "parents")
@Entity
public class Parents {
    @Id
    @GeneratedValue
    public int id;
    public String fatherFullname;
    public String motherFullname;

    @OneToMany(mappedBy = "parents", cascade = CascadeType.ALL)
    public List<Child> children = new ArrayList<Child>();

    @ManyToOne
    @JoinColumn(name = "addressId")
    public Address address;

    public void save_parents() {
        new ParentsRequests().saveParents(this);
    }

    public void update_parents() {
        new ParentsRequests().updateParents(this);
    }
}