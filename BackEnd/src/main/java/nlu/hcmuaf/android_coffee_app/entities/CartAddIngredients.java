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
@Entity(name = "cart_add_ingredients")
public class CartAddIngredients {
    @Id
    @ManyToOne
    @JoinColumn(name = "cartItemId")
    private CartItems cartItems;

    @Id
    @ManyToOne
    @JoinColumn(name = "ingredientId")
    private Ingredients ingredients;
}
