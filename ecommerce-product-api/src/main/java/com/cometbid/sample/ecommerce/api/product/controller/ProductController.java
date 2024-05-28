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
package com.cometbid.sample.ecommerce.api.product.controller;

import lombok.extern.log4j.Log4j2;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
/**
 *
 * @author samueladebowale
 */
@Log4j2
@RestController
public class ProductController {

    private final Counter pageViewsCounter;
    private final Timer productTimer;
    private final Gauge priceGauge;

    private final MeterRegistry meterRegistry;
    private final PricingEngine pricingEngine;

    ProductController(MeterRegistry meterRegistry, PricingEngine pricingEngine) {

        this.meterRegistry = meterRegistry;
        this.pricingEngine = pricingEngine;

        priceGauge = Gauge
                .builder("product.price", pricingEngine,
                        (pe) -> {
                            return pe != null ? pe.getProductPrice() : null;
                        })
                .description("Product price")
                .baseUnit("ms")
                .register(meterRegistry);

        pageViewsCounter = meterRegistry
                .counter("PAGE_VIEWS.ProductList");

        productTimer = meterRegistry
                .timer("execution.time.fetchProducts");
    }

    /**
     * 
     * @return 
     */
    @GetMapping("/products")
    @ResponseBody
    public List<Product> fetchProducts() {
        long startTime = System.currentTimeMillis();

        List<Product> products = fetchProductsFromStore();

        // increment page views counter
        pageViewsCounter.increment();

        // record time to execute the method
        log.info("productTimer {} {} {}", productTimer, pageViewsCounter,priceGauge);
        productTimer.record(Duration.ofMillis(System.currentTimeMillis() - startTime));

        return products;
    }

    private List<Product> fetchProductsFromStore() {
        List<Product> products = new ArrayList<>();
        products.add(Product.builder().name("Television").build());
        products.add(Product.builder().name("Book").build());
        return products;
    }

}
