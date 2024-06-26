package nlu.hcmuaf.android_coffee_app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlu.hcmuaf.android_coffee_app.enums.EIngredient;
import nlu.hcmuaf.android_coffee_app.enums.EIngredientType;

import java.util.Objects;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ingredients")
public class Ingredients {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredientId")
    private long ingredientId;

    @Column(name = "name")
    private String name;

    @Column(name = "addPrice")
    private double addPrice;

    @Column(name = "ingredientType")
    @Enumerated(EnumType.STRING)
    private EIngredientType ingredientType;

    @Column(name = "ingredientEnum")
    @Enumerated(EnumType.STRING)
    private EIngredient ingredientEnum;

    @OneToMany(mappedBy = "ingredients", cascade = CascadeType.ALL)
    private Set<HavingIngredients> productSet;

    @OneToMany(mappedBy = "ingredients", cascade = CascadeType.ALL)
    private Set<AddIngredients> orderItemSet;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ingredients that)) return false;
        return ingredientId == that.ingredientId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ingredientId);
    }
}
