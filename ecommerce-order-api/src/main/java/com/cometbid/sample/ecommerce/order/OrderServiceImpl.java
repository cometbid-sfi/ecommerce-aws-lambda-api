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
package org.cometbid.sample.ecommerce.api.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.apache.logging.log4j.util.Strings;
import org.cometbid.sample.ecommerce.api.entity.OrderEntity;
import org.cometbid.sample.ecommerce.api.exceptions.ResourceNotFoundException;
import org.cometbid.sample.ecommerce.api.order.OrderRepository;
import org.cometbid.swaggerCodeGen.model.NewOrder;
import org.springframework.stereotype.Service;

/**
 *
 * @author samueladebowale
 */
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    //private PaymentRepository paymentRepository;

    public OrderServiceImpl(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<OrderEntity> addOrder(@Valid NewOrder newOrder) {
        if (Strings.isEmpty(newOrder.getCustomerId())) {
            throw new ResourceNotFoundException("Invalid customer id.");
        }
        if (Objects.isNull(newOrder.getAddress()) || Strings.isEmpty(newOrder.getAddress().getId())) {
            throw new ResourceNotFoundException("Invalid address id.");
        }
        if (Objects.isNull(newOrder.getCard()) || Strings.isEmpty(newOrder.getCard().getId())) {
            throw new ResourceNotFoundException("Invalid card id.");
        }
        // 1. Save Order
        return repository.insert(newOrder);
        // Ideally, here it will trigger the rest of the process
        // 2. Initiate the payment
        // 3. Once the payment is authorized, change the status to paid
        // 4. Initiate the Shipment and changed the status to Shipment Initiated or
        // Shipped
    }

    @Override
    public Iterable<OrderEntity> getOrdersByCustomerId(@NotNull @Valid String customerId) {
        return repository.findByCustomerId(UUID.fromString(customerId));
    }

    @Override
    public Optional<OrderEntity> getByOrderId(String id) {
        return repository.findById(UUID.fromString(id));
    }

    public Payment pay(Long orderId, String creditCardNumber) {
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);

        if (order.isPaid()) {
            throw new OrderAlreadyPaid();
        }

        orderRepository.save(order.markPaid());
        return paymentRepository.save(new Payment(order, creditCardNumber));
    }

    public Receipt getReceipt(Long orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId).orElseThrow(EntityNotFoundException::new);
        return new Receipt(payment.getOrder().getDate(),
                payment.getCreditCardNumber(),
                Money.of(payment.getOrder().getAmount(), Monetary.getCurrency("EUR")));
    }
}
