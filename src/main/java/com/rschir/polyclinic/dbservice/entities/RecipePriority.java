package com.rschir.polyclinic.dbservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "recipe_priority")
@JsonIgnoreProperties({ "recipes" })
public class RecipePriority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "priorityId")
    private Long priorityId;

    @Column(name = "priorityName")
    private String name;

    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE},
            mappedBy = "recipePriority")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Recipe> recipes;


    public RecipePriority() {
    }

    public RecipePriority(String name) {
        this.name = name;
    }

    public Long getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(Long priorityId) {
        this.priorityId = priorityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipePriority that = (RecipePriority) o;
        return Objects.equals(priorityId, that.priorityId) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(priorityId, name);
    }
}
