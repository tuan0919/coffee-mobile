package nlu.hcmuaf.android_coffee_app.dto.request.cart_controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlu.hcmuaf.android_coffee_app.enums.EPaymentMethod;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrderRequestDTO {
    private long storeId;
    private EPaymentMethod method;
}
