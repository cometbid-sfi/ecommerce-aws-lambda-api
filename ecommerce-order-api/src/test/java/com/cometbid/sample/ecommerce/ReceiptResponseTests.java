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
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import javax.money.Monetary;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author samueladebowale
 */
@JsonTest
class ReceiptResponseTests {
    
    @Autowired
    private JacksonTester<ReceiptResponse> jacksonTester;

    @Test
    void serializeInCorrectFormat() throws IOException {
        ReceiptResponse receipt = new ReceiptResponse(
                LocalDateTime.of(2021, 5, 9, 16, 0),
                "4532756279624064",
                Money.of(50.0, Monetary.getCurrency("USD")));

        JsonContent<ReceiptResponse> json = jacksonTester.write(receipt);

        assertThat(json).extractingJsonPathStringValue("$.date").isEqualTo("09.05.2021 16:00");
        assertThat(json).extractingJsonPathStringValue("$.amount").isEqualTo("USD50.00");
    }

    @Test
    void serializeCorrectJson() throws IOException {
        ReceiptResponse receipt = new ReceiptResponse(
                LocalDateTime.of(2021, 3, 30, 17, 5),
                "4532756279624064",
                Money.of(100.0, Monetary.getCurrency("EUR")));

        JsonContent<ReceiptResponse> json = jacksonTester.write(receipt);

        assertThat(json).isEqualToJson("receipt.json");
    }
}
