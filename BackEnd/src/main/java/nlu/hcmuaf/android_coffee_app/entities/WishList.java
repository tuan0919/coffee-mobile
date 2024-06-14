package nlu.hcmuaf.android_coffee_app.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "wishlist")
public class WishList {
    @Id
    @ManyToOne
    @JoinColumn(name = "userId")
    private Users user;

    @Id
    @ManyToOne
    @JoinColumn(name = "productId")
    private Products product;
}
