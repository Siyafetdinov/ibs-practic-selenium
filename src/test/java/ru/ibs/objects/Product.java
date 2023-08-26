package ru.ibs.objects;

import java.util.Objects;

public class Product {
    private int index;
    private final String name;
    private final String type;
    private final boolean exotic;

    public Product(String name, String type, boolean exotic) {
        this.name = name;
        this.type = type;
        this.exotic = exotic;
    }

    public Product(int index, String name, String type, boolean exotic) {
        this.index = index;
        this.name = name;
        this.type = type;
        this.exotic = exotic;
    }

    public String getName() {
        return name;
    }
    public String getType() {
        return type;
    }
    public boolean isExotic() {
        return exotic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product products = (Product) o;
        return index == products.index && exotic == products.exotic && Objects.equals(name, products.name) && Objects.equals(type, products.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, name, type, exotic);
    }

    @Override
    public String toString() {
        return "Product{" +
                "index=" + index +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", exotic=" + exotic +
                '}';
    }
}
