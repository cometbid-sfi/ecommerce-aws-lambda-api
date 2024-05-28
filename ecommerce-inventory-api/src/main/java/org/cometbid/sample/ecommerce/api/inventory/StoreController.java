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
package org.cometbid.sample.ecommerce.api.inventory;

import lombok.extern.log4j.Log4j2;

import java.util.Map;
import org.cometbid.component.api.response.AppResponse;
import org.cometbid.swaggerCodeGen.api.StoreApi;
import org.cometbid.swaggerCodeGen.model.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author samueladebowale
 */
@Log4j2
@RestController
public class StoreController implements StoreApi {

    /**
     *
     * @param orderId
     * @return
     */
    @Override
    public ResponseEntity<AppResponse> deleteOrder(Long orderId) {
        return null;
    }

    /**
     *
     * @return
     */
    @Override
    public ResponseEntity<Map<String, Integer>> getInventory() {
        return null;
    }

    /**
     *
     * @param orderId
     * @return
     */
    @Override
    public ResponseEntity<AppResponse> getOrderById(Long orderId) {
        return null;
    }

    /**
     *
     * @param order
     * @return
     */
    @Override
    public ResponseEntity<AppResponse> placeOrder(Order order) {
        return null;
    }
}
