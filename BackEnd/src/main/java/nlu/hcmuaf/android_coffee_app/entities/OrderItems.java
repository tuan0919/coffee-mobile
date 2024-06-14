package nlu.hcmuaf.android_coffee_app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlu.hcmuaf.android_coffee_app.enums.EProductSize;

import java.io.Serializable;
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

    @OneToMany(mappedBy = "orderItems", cascade = CascadeType.ALL)
    private Set<AddIngredients> ingredientsSet;
}
