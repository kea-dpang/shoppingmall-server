package com.example.shoppingmallserver.dto.cart_wishlist;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ItemIdsRequestDto {
    private List<Long> itemIds;
}
