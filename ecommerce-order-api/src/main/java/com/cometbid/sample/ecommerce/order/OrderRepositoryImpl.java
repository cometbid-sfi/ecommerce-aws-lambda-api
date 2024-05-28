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

import org.cometbid.sample.ecommerce.api.cart.CartRepository;
import org.cometbid.sample.ecommerce.api.product.ItemRepository;
import static java.util.stream.Collectors.toList;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;
import org.cometbid.sample.ecommerce.api.cart.CartEntity;
import org.cometbid.sample.ecommerce.api.entity.OrderEntity;
import org.cometbid.sample.ecommerce.api.entity.OrderItemEntity;
import org.cometbid.sample.ecommerce.api.exceptions.ResourceNotFoundException;
import org.cometbid.sample.ecommerce.api.product.ItemEntity;
import org.cometbid.swaggerCodeGen.model.NewOrder;
import org.cometbid.swaggerCodeGen.model.Order.StatusEnum;
import org.springframework.stereotype.Repository;

/**
 *
 * @author samueladebowale
 */
@Repository
@Transactional
public class OrderRepositoryImpl implements OrderRepositoryExt {

    @PersistenceContext
    private final EntityManager em;

    private final ItemRepository itemRepo;
    private final CartRepository cRepo;
    private final OrderItemRepository oiRepo;

    public OrderRepositoryImpl(
            EntityManager em, ItemRepository itemRepo, CartRepository cRepo, OrderItemRepository oiRepo) {
        this.em = em;
        this.itemRepo = itemRepo;
        this.cRepo = cRepo;
        this.oiRepo = oiRepo;
    }

    @Override
    public Optional<OrderEntity> insert(NewOrder m) {
        // Items are already in cart and saved in db when user places the order
        // Here, you can also populate the other Order details like address, card etc.
        Iterable<ItemEntity> dbItems = itemRepo.findByCustomerId(m.getCustomerId());
        List<ItemEntity> items = StreamSupport.stream(dbItems.spliterator(), false).toList();

        if (items.size() < 1) {
            throw new ResourceNotFoundException(
                    String.format("There is no item found in customer's (ID: %s) cart.", m.getCustomerId()));
        }
        BigDecimal total = BigDecimal.ZERO;

        for (ItemEntity i : items) {
            total = (BigDecimal.valueOf(i.getQuantity()).multiply(i.getPrice())).add(total);
        }

        Timestamp orderDate = Timestamp.from(Instant.now());
        em.createNativeQuery(
                """
        INSERT INTO ecomm.orders (address_id, card_id, customer_id, order_date, total, status)
         VALUES(?, ?, ?, ?, ?, ?)
        """)
                .setParameter(1, m.getAddress().getId())
                .setParameter(2, m.getCard().getId())
                .setParameter(3, m.getCustomerId())
                .setParameter(4, orderDate)
                .setParameter(5, total)
                .setParameter(6, StatusEnum.CREATED.getValue())
                .executeUpdate();

        Optional<CartEntity> oCart = cRepo.findByCustomerId(UUID.fromString(m.getCustomerId()));
        CartEntity cart
                = oCart.orElseThrow(
                        ()
                        -> new ResourceNotFoundException(
                                String.format(
                                        "Cart not found for given customer (ID: %s)", m.getCustomerId())));
        itemRepo.deleteCartItemJoinById(
                cart.getItems().stream().map(ItemEntity::getId).collect(toList()), cart.getId());
        OrderEntity entity
                = (OrderEntity) em.createNativeQuery(
                        """
        SELECT o.* FROM ecomm.orders o WHERE o.customer_id = ? AND o.order_date >= ?
        """,
                        OrderEntity.class)
                        .setParameter(1, m.getCustomerId())
                        .setParameter(
                                2,
                                OffsetDateTime.ofInstant(orderDate.toInstant(), ZoneId.of("Z"))
                                        .truncatedTo(ChronoUnit.MICROS))
                        .getSingleResult();
        oiRepo.saveAll(
                cart.getItems().stream()
                        .map(i -> new OrderItemEntity().setOrderId(entity.getId()).setItemId(i.getId()))
                        .collect(toList()));
        return Optional.of(entity);
    }
}
