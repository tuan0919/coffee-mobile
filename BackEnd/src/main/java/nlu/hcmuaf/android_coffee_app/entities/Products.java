package nlu.hcmuaf.android_coffee_app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlu.hcmuaf.android_coffee_app.enums.EProductType;

import java.util.Objects;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "products")
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId")
    private long productId;

    @Column(name = "productName")
    private String productName;

    @Column(name = "basePrice")
    private double basePrice;

    @Column(name = "active")
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Categories categories;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "productType")
    @Enumerated(EnumType.STRING)
    private EProductType productType;

    @OneToMany(mappedBy = "products", cascade = CascadeType.ALL)
    private Set<HavingSizes> sizeSet;

    @OneToMany(mappedBy = "products", cascade = CascadeType.ALL)
    private Set<HavingIngredients> ingredientsSet;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<WishList> wishLists;

    @ManyToOne
    @JoinColumn(name = "discountId")
    private Discounts discount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Products products)) return false;
        return productId == products.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(productId);
    }
}
