package nlu.hcmuaf.android_coffee_app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlu.hcmuaf.android_coffee_app.enums.EProductSize;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "order_items")
public class OrderItems implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderItemId")
    private int orderItemId;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Products products;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private Orders orders;

    @Column(name = "productSize")
    @Enumerated(EnumType.STRING)
    private EProductSize size;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private double price;

    @OneToMany(mappedBy = "orderItems", cascade = CascadeType.ALL)
    private Set<AddIngredients> ingredientsSet;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItems that)) return false;
        return orderItemId == that.orderItemId && Objects.equals(products, that.products) && Objects.equals(orders, that.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderItemId, products, orders);
    }
}
