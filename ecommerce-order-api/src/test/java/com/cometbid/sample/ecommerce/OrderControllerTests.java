/*
 * The MIT License
 *
 * Copyright 2024 samueladebowale.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.cometbid.sample.ecommerce;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.money.Monetary;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 *
 * @author samueladebowale
 */
@WebMvcTest(OrderController.class)
class OrderControllerTests {
    @MockBean
    private OrderService orderService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getReceiptForOrder() throws Exception {
        givenOrderIsPaid(1L);

        mockMvc.perform(get("/order/{id}/receipt", 1L))
                .andExpect(jsonPath("$.date").isNotEmpty())
                .andExpect(jsonPath("$.creditCardNumber").isNotEmpty())
                .andExpect(jsonPath("$.amount").isNotEmpty());
    }

    private void givenOrderIsPaid(Long orderId) {
        Receipt receipt = new Receipt(
                LocalDateTime.now(),
                "4532756279624064",
                Money.of(100.0, Monetary.getCurrency("EUR")));
        when(orderService.getReceipt(eq(orderId))).thenReturn(receipt);
    }
}
