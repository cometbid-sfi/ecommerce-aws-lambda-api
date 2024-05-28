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
package org.cometbid.sample.ecommerce.api.address;

import jakarta.persistence.*;
import java.util.UUID;

/**
 *
 * @author samueladebowale
 */
@Entity
@Table(name = "address")
public class AddressEntity {

    @Id
    @GeneratedValue
    @Column(name = "ID", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "NUMBER")
    private String number;

    @Column(name = "RESIDENCY")
    private String residency;

    @Column(name = "STREET")
    private String street;

    @Column(name = "CITY")
    private String city;

    @Column(name = "STATE")
    private String state;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "POSTAL_CODE")
    private String postalCode;

    /*
    @OneToMany(mappedBy = "addressEntity", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<OrderEntity> orders;
    */

    public UUID getId() {
        return this.id;
    }

    public AddressEntity setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getNumber() {
        return this.number;
    }

    public AddressEntity setNumber(String number) {
        this.number = number;
        return this;
    }

    public String getResidency() {
        return this.residency;
    }

    public AddressEntity setResidency(String residency) {
        this.residency = residency;
        return this;
    }

    public String getStreet() {
        return this.street;
    }

    public AddressEntity setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getCity() {
        return this.city;
    }

    public AddressEntity setCity(String city) {
        this.city = city;
        return this;
    }

    public String getState() {
        return this.state;
    }

    public AddressEntity setState(String state) {
        this.state = state;
        return this;
    }

    public String getCountry() {
        return this.country;
    }

    public AddressEntity setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public AddressEntity setPostalcode(String postalcode) {
        this.postalCode = postalcode;
        return this;
    }

    /*
    public List<OrderEntity> getOrders() {
        return this.orders;
    }

    public AddressEntity setOrders(List<OrderEntity> orders) {
        this.orders = orders;
        return this;
    }
    */
}
