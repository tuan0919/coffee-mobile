package nlu.hcmuaf.android_coffee_app.dto.response.wishlist;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WishlistResponseDTO {
    long productId;
}
