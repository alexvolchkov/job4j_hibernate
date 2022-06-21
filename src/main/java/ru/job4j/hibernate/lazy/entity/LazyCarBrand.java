package ru.job4j.hibernate.lazy.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "l_car_brands")
public class LazyCarBrand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToMany(mappedBy = "lazyCarBrand")
    private List<LazyModel> models = new ArrayList<>();

    public static LazyCarBrand of(String name) {
        LazyCarBrand lazyCarBrand = new LazyCarBrand();
        lazyCarBrand.name = name;
        return lazyCarBrand;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<LazyModel> getModels() {
        return models;
    }

    public void setModels(List<LazyModel> models) {
        this.models = models;
    }

    public void addModel(LazyModel model) {
        models.add(model);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LazyCarBrand lazyCarBrand = (LazyCarBrand) o;
        return id == lazyCarBrand.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "LazyCarBrand{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", models=" + models
                + '}';
    }
}
