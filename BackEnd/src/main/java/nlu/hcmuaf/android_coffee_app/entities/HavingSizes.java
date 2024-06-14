package nlu.hcmuaf.android_coffee_app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlu.hcmuaf.android_coffee_app.enums.EProductSize;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "having_sizes")
public class HavingSizes {
    @Id
    @ManyToOne
    @JoinColumn(name = "productId")
    private Products products;

    @Id
    @Column(name = "size")
    @Enumerated(EnumType.STRING)
    private EProductSize size;
}
