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
package org.cometbid.sample.ecommerce.order;

import org.cometbid.sample.ecommerce.api.order.CardEntity;
import org.cometbid.sample.ecommerce.api.order.ShipmentEntity;
import org.cometbid.sample.ecommerce.api.product.ItemEntity;
import org.cometbid.sample.ecommerce.api.customer.CustomerEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.cometbid.swaggerCodeGen.model.Order.StatusEnum;

/**
 *
 * @author samueladebowale
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue
    @Column(name = "ID", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "TOTAL")
    private BigDecimal total;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    private CustomerEntity userEntity;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "STORE_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    private StoreEntity storeEntity;

    /*
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    private AddressEntity addressEntity;
    */

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PAYMENT_ID", referencedColumnName = "ID")
    private PaymentEntity paymentEntity;

    @JoinColumn(name = "SHIPMENT_ID", referencedColumnName = "ID")
    @OneToOne
    private ShipmentEntity shipment;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "CARD_ID", referencedColumnName = "ID")
    private CardEntity cardEntity;

    @Column(name = "ORDER_DATE")
    private Timestamp orderDate;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "ORDER_ITEM", joinColumns = @JoinColumn(name = "ORDER_ID"), inverseJoinColumns = @JoinColumn(name = "ITEM_ID"))
    private List<ItemEntity> items = new ArrayList<>();

    @OneToOne(mappedBy = "orderEntity")
    private AuthorizationEntity authorizationEntity;
    
    @NonNull
    private Boolean paid;

    public boolean isPaid() {
        return paid;
    }

    public OrderEntity markPaid() {
        paid = true;
        return this;
    }

    public UUID getId() {
        return id;
    }

    public OrderEntity setId(UUID id) {
        this.id = id;
        return this;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public OrderEntity setTotal(BigDecimal total) {
        this.total = total;
        return this;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public OrderEntity setStatus(StatusEnum status) {
        this.status = status;
        return this;
    }

    public CustomerEntity getUserEntity() {
        return userEntity;
    }

    public OrderEntity setUserEntity(CustomerEntity userEntity) {
        this.userEntity = userEntity;
        return this;
    }

    /*
    public AddressEntity getAddressEntity() {
        return addressEntity;
    }

    public OrderEntity setAddressEntity(AddressEntity addressEntity) {
        this.addressEntity = addressEntity;
        return this;
    }
    */

    public PaymentEntity getPaymentEntity() {
        return paymentEntity;
    }

    public OrderEntity setPaymentEntity(PaymentEntity paymentEntity) {
        this.paymentEntity = paymentEntity;
        return this;
    }

    public ShipmentEntity getShipments() {
        return shipment;
    }

    public OrderEntity setShipments(ShipmentEntity shipment) {
        this.shipment = shipment;
        return this;
    }

    public CardEntity getCardEntity() {
        return cardEntity;
    }

    public OrderEntity setCardEntity(CardEntity cardEntity) {
        this.cardEntity = cardEntity;
        return this;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public OrderEntity setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public List<ItemEntity> getItems() {
        return items;
    }

    public OrderEntity setItems(List<ItemEntity> items) {
        this.items = items;
        return this;
    }

    public AuthorizationEntity getAuthorizationEntity() {
        return authorizationEntity;
    }

    public OrderEntity setAuthorizationEntity(AuthorizationEntity authorizationEntity) {
        this.authorizationEntity = authorizationEntity;
        return this;
    }

    public StoreEntity getStoreEntity() {
        return storeEntity;
    }

    public OrderEntity setStoreEntity(StoreEntity storeEntity) {
        this.storeEntity = storeEntity;
        return this;
    }

    public ShipmentEntity getShipment() {
        return shipment;
    }

    public OrderEntity setShipment(ShipmentEntity shipment) {
        this.shipment = shipment;
        return this;
    }

    @Override
    public String toString() {
        return "OrderEntity{" 
                + "id=" + id 
                + ", total=" + total 
                + ", status=" + status 
                + ", userEntity=" + userEntity
                //+ ", addressEntity=" + addressEntity 
                + ", paymentEntity=" + paymentEntity 
                + ", shipment=" + shipment
                + ", cardEntity=" + cardEntity 
                + ", orderDate=" + orderDate 
                + ", items=" + items
                + ", authorizationEntity=" + authorizationEntity 
                + '}';
    }
}
