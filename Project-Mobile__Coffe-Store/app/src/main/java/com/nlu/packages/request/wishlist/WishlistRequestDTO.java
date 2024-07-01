package com.nlu.packages.request.wishlist;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WishlistRequestDTO {
    List<Long> productIds;
}
