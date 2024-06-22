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
@Entity(name = "order_add_ingredients")
public class OrderAddIngredients {
    @Id
    @ManyToOne
    @JoinColumn(name = "orderItemId")
    private OrderItems orderItems;

    @Id
    @ManyToOne
    @JoinColumn(name = "ingredientId")
    private Ingredients ingredients;
}
