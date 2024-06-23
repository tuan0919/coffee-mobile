package nlu.hcmuaf.android_coffee_app.dto.request.order;
import lombok.*;
import lombok.experimental.FieldDefaults;
import nlu.hcmuaf.android_coffee_app.enums.EPaymentMethod;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CreateOrderRequestDTO {
    Long storeId;
    EPaymentMethod method;
    List<Long> chosenProductIds;
}
