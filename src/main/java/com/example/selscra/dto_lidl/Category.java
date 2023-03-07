package com.example.selscra.dto_lidl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {

    @Id
    private long id;
    private String name;
    @Transient
    private List<SubCategory> submenu = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<SubCategory> getSubmenu() {
        return submenu;
    }

    public void addSubmenu(SubCategory submenu) {
        this.submenu.add(submenu);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", submenu=" + submenu +
                '}';
    }
}
