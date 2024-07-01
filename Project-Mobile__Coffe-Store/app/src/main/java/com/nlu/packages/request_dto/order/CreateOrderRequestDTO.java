package com.nlu.packages.request_dto.order;

import com.nlu.packages.enums.EPaymentMethod;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CreateOrderRequestDTO implements Serializable {
    Long storeId;
    EPaymentMethod method;
    List<Long> chosenProductIds;
}
