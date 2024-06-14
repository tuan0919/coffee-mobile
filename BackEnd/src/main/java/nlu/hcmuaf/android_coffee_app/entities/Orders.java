package nlu.hcmuaf.android_coffee_app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlu.hcmuaf.android_coffee_app.enums.EOrderStatus;

import java.io.Serializable;

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

    @ManyToOne
    @JoinColumn(name = "paymentId")
    private Payments payments;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EOrderStatus status;

    private double totalPrice;

    @ManyToOne
    @JoinColumn(name = "storedId")
    private Stores stores;
}
