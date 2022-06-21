package ru.job4j.hibernate.lazy.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "l_models")
public class LazyModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "car_brand_id")
    private LazyCarBrand lazyCarBrand;

    public static LazyModel of(String name, LazyCarBrand lazyCarBrand) {
        LazyModel model = new LazyModel();
        model.name = name;
        model.lazyCarBrand = lazyCarBrand;
        return model;
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

    public LazyCarBrand getCarBrand() {
        return lazyCarBrand;
    }

    public void setCarBrand(LazyCarBrand lazyCarBrand) {
        this.lazyCarBrand = lazyCarBrand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LazyModel model = (LazyModel) o;
        return id == model.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "LazyModel{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", lazyCarBrand=" + lazyCarBrand.getName()
                + '}';
    }
}
