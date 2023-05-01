package com.example.effective_software_testing;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ShoppingCartTest {

    // 필드로 만듦으로서 테스트마다 인스턴스를 생성할 필요가 없다
    private final ShoppingCart cart = new ShoppingCart();

    // 빈 카드는 0 반환
    @Test
    void noItems() {
        assertThat(cart.totalPrice()).isEqualTo(0);
    }

    @Test
    void itemsInTheCart() {
        // 카트에 품목이 하나만 있을 때
        cart.add(new CartItem("TV", 1, 120));
        assertThat(cart.totalPrice()).isEqualTo(120);
        // 카트에 품목이 여러 개 있을 때
        cart.add(new CartItem("Chocolate", 2, 2.5));
        assertThat(cart.totalPrice()).isEqualTo(120 + 2.5*2);
    }
}