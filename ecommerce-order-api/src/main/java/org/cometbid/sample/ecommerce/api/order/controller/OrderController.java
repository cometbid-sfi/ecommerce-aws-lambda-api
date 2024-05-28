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
package org.cometbid.sample.ecommerce.api.order.controller;

import java.util.List;
import java.util.Optional;
import org.cometbid.sample.ecommerce.api.config.ConfigurationFactory;
import org.cometbid.swaggerCodeGen.api.OrderApi;
import org.cometbid.swaggerCodeGen.model.NewOrder;
import org.cometbid.swaggerCodeGen.model.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.log4j.Log4j2;
import org.cometbid.component.api.response.ApiMetadata;
import org.cometbid.component.api.response.AppResponse;
import org.cometbid.sample.ecommerce.api.order.OrderRepresentationModelAssembler;
import org.cometbid.sample.ecommerce.order.OrderService;

import static org.springframework.http.ResponseEntity.accepted;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author samueladebowale
 */
@Log4j2
@RestController
public class OrderController implements OrderApi {

    private final OrderRepresentationModelAssembler assembler;
    private final OrderService orderService;
    private final ConfigurationFactory config;

    private String traceId;
    private ApiMetadata apiMetadata;

    public OrderController(OrderService service, OrderRepresentationModelAssembler assembler,
            ConfigurationFactory config) {
        this.orderService = service;
        this.assembler = assembler;
        this.config = config;
        
        this.traceId = config.getTraceId();
        this.apiMetadata = config.createResponseMetadata();
    }

    /**
     *
     * @param newOrder
     * @return
     */
    @Override
    public ResponseEntity<AppResponse> addOrder(NewOrder newOrder) {
        Optional<Order> orderOpt = orderService.addOrder(newOrder).map(assembler::toModel);

        if (orderOpt.isPresent()) {
            return ok(AppResponse.success(orderOpt.get(), this.traceId, apiMetadata));
        } else {
            return accepted().body(AppResponse.success("operation failed", this.traceId, apiMetadata));
        }
    }

    /**
     *
     * @param customerId
     * @return
     */
    @Override
    public ResponseEntity<AppResponse> getOrderByCustomerId(String customerId) {
        List<Order> orderList = assembler.toListModel(orderService.getOrdersByCustomerId(customerId));
        return ok(AppResponse.success(orderList, this.traceId, apiMetadata));
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<AppResponse> getByOrderId(String id) {
        Optional<Order> orderOpt = orderService.getByOrderId(id).map(assembler::toModel);

        if (orderOpt.isPresent()) {
            return ok(AppResponse.success(orderOpt.get(), this.traceId, apiMetadata));
        } else {
            return notFound().build();
        }
    }
    
    //@PostMapping("/order/{id}/payment")
    public ResponseEntity<AppResponse> pay(
             Long orderId,
            @RequestBody @Valid PaymentRequest paymentRequest,
            UriComponentsBuilder uriComponentsBuilder) {

        Payment payment = orderService.pay(orderId, paymentRequest.getCreditCardNumber());
        URI location = uriComponentsBuilder.path("/order/{id}/receipt").buildAndExpand(orderId).toUri();
        PaymentResponse response = new PaymentResponse(payment.getOrder().getId(), payment.getCreditCardNumber());
        return ResponseEntity.created(location).body(response);
    }
    
    //@GetMapping("/order/{id}/receipt")
    public ResponseEntity<Receipt> getReceipt(@PathVariable("id") Long orderId) {
        Receipt receipt = orderService.getReceipt(orderId);
        return ResponseEntity.ok().body(receipt);
    }

}
