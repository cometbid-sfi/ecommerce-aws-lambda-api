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
package org.cometbid.sample.ecommerce.api.entity;

import org.cometbid.sample.ecommerce.api.address.AddressEntity;
import org.cometbid.sample.ecommerce.api.customer.CustomerEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author samueladebowale
 */
@Entity
@Table(name = "\"store\"")
public class StoreEntity {

    @Id
    @GeneratedValue
    @Column(name = "ID", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CAPTION")
    private String caption;

    @OneToOne
    @JoinColumn(name = "CUST_ID", referencedColumnName = "ID")
    private CustomerEntity customer;

    @OneToOne
    @JoinColumn(name = "ADDR_ID", referencedColumnName = "ID")
    private AddressEntity address;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ProductEntity> inventory = new ArrayList<>();

    @OneToMany(mappedBy = "storeEntity", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<OrderEntity> orders;

    public UUID getId() {
        return id;
    }

    public StoreEntity setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public StoreEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getCaption() {
        return caption;
    }

    public StoreEntity setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public StoreEntity setCustomer(CustomerEntity customer) {
        this.customer = customer;
        return this;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public StoreEntity setAddress(AddressEntity address) {
        this.address = address;
        return this;
    }

    public List<ProductEntity> getInventory() {
        return inventory;
    }

    public StoreEntity setInventory(List<ProductEntity> inventory) {
        this.inventory = inventory;
        return this;
    }

    public List<OrderEntity> getOrders() {
        return orders;
    }

    public StoreEntity setOrders(List<OrderEntity> orders) {
        this.orders = orders;
        return this;
    }

}
