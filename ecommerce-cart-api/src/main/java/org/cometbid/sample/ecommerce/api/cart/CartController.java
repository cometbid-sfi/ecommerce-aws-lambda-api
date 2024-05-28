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
package org.cometbid.sample.ecommerce.api.cart;

import java.util.List;
import org.cometbid.sample.ecommerce.api.config.ConfigurationFactory;
import org.cometbid.swaggerCodeGen.api.CartApi;
import org.cometbid.swaggerCodeGen.model.Cart;
import org.cometbid.swaggerCodeGen.model.Item;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.cometbid.component.api.response.ApiMetadata;
import org.cometbid.component.api.response.AppResponse;
import static org.springframework.http.ResponseEntity.accepted;
import static org.springframework.http.ResponseEntity.ok;

/**
 *
 * @author samueladebowale
 */
@Log4j2
@RestController
public class CartController implements CartApi {

    private final CartService service;
    private final CartRepresentationModelAssembler assembler;
    private final ConfigurationFactory config;

    private String traceId;
    private ApiMetadata apiMetadata;

    public CartController(CartService service, CartRepresentationModelAssembler assembler, ConfigurationFactory config) {
        this.service = service;
        this.assembler = assembler;
        this.config = config;
        
        this.traceId = config.getTraceId();
        this.apiMetadata = config.createResponseMetadata();
    }

    /**
     *
     * @param customerId
     * @param item
     * @return
     */
    @Override
    public ResponseEntity<AppResponse> addCartItemsByCustomerId(String customerId, @Valid Item item) {
        log.info("Request for customer ID: {}\nItem: {}", customerId, item);
        List<Item> itemlist = service.addCartItemsByCustomerId(customerId, item);
        return ok(AppResponse.success(itemlist, this.traceId, apiMetadata));
    }

    /**
     *
     * @param customerId
     * @param item
     * @return
     */
    @Override
    public ResponseEntity<AppResponse> addOrReplaceItemsByCustomerId(String customerId, @Valid Item item) {
        List<Item> itemlist = service.addOrReplaceItemsByCustomerId(customerId, item);
        return ok(AppResponse.success(itemlist, this.traceId, apiMetadata));
    }

    /**
     *
     * @param customerId
     * @return
     */
    @Override
    public ResponseEntity<AppResponse> deleteCart(String customerId) {
        service.deleteCart(customerId);
        return accepted().build();
    }

    /**
     *
     * @param customerId
     * @param itemId
     * @return
     */
    @Override
    public ResponseEntity<AppResponse> deleteItemFromCart(String customerId, String itemId) {
        service.deleteItemFromCart(customerId, itemId);

        return accepted().build();
    }

    /**
     *
     * @param customerId
     * @return
     */
    @Override
    public ResponseEntity<AppResponse> getCartByCustomerId(String customerId) {
        Cart cart = assembler.toModel(service.getCartByCustomerId(customerId));
        return ok(AppResponse.success(cart, this.traceId, apiMetadata));
    }

    /**
     *
     * @param customerId
     * @return
     */
    @Override
    public ResponseEntity<AppResponse> getCartItemsByCustomerId(String customerId) {
        List<Item> itemlist = service.getCartItemsByCustomerId(customerId);
        return ok(AppResponse.success(itemlist, this.traceId, apiMetadata));
    }

    /**
     *
     * @param customerId
     * @param itemId
     * @return
     */
    @Override
    public ResponseEntity<AppResponse> getCartItemsByItemId(String customerId, String itemId) {
        Item item = service.getCartItemsByItemId(customerId, itemId);
        return ok(AppResponse.success(item, this.traceId, apiMetadata));
    }
}
