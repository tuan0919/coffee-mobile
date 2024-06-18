package nlu.hcmuaf.android_coffee_app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlu.hcmuaf.android_coffee_app.enums.EOrderStatus;
import nlu.hcmuaf.android_coffee_app.enums.EPaymentMethod;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "orders")
public class Orders implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderId")
    private long orderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment")
    private EPaymentMethod payments;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EOrderStatus status;

    private double totalPrice;

    @ManyToOne
    @JoinColumn(name = "storedId")
    private Stores stores;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private Set<OrderItems> orderItemsSet;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Orders orders)) return false;
        return orderId == orders.orderId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(orderId);
    }
}
