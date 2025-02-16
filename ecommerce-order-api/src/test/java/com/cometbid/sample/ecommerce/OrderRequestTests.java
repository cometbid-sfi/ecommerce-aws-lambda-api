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

import com.cometbid.sample.ecommerce.order.model.OrderRequest;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author samueladebowale
 */
@JsonTest
public class OrderRequestTests {

    @Autowired
    private JacksonTester<OrderRequest> jacksonTester;

    @Test
    void deserializeFromCorrectFormat() throws IOException {
        String json = "{\"amount\": \"USD50.00\"}";
        MonetaryAmount expectedAmount = Money.of(50.0, Monetary.getCurrency("USD"));

        OrderRequest orderRequest = jacksonTester.parseObject(json);

        assertThat(orderRequest.getAmount()).isEqualTo(expectedAmount);
    }

    @Test
    void deserializeFromJson() throws IOException {
        MonetaryAmount expectedAmount = Money.of(100.0, Monetary.getCurrency("EUR"));

        OrderRequest orderRequest = jacksonTester.readObject("order.json");

        assertThat(orderRequest.getAmount()).isEqualTo(expectedAmount);
    }
}
