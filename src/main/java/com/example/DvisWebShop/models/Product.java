package com.example.DvisWebShop.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "company", nullable = false)
    private String company;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    public Product(String name, BigDecimal price, String company) {
        this.name = name;
        this.price = price;
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return name.equals(product.name) &&
                Objects.equals(product.price, price) &&
                company.equals(product.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, company);
    }


    @Override
    public String toString() {
        return "Product{" +
                "product_id = " + productId +
                ", name = '" + name + '\'' +
                ", price = '" + price + '\'' +
                ", company = '" + company + '\'' +
                '}';
    }
}
