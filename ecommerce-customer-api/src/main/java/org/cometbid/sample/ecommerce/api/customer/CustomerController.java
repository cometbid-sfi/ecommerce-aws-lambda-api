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
package org.cometbid.sample.ecommerce.api.customer;

import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.cometbid.component.api.response.ApiMetadata;
import org.cometbid.component.api.response.AppResponse;
import org.cometbid.sample.ecommerce.api.address.AddressRepresentationModelAssembler;
import org.cometbid.sample.ecommerce.api.config.ConfigurationFactory;
import org.cometbid.sample.ecommerce.api.order.CardRepresentationModelAssembler;
import org.cometbid.sample.ecommerce.user.api.hateoas.CustomerRepresentationModelAssembler;
import org.cometbid.swaggerCodeGen.api.CustomerApi;
import org.cometbid.swaggerCodeGen.model.Address;
import org.cometbid.swaggerCodeGen.model.Card;
import org.cometbid.swaggerCodeGen.model.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.ResponseEntity.accepted;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

/**
 *
 * @author samueladebowale
 */
@Log4j2
@RestController
public class CustomerController implements CustomerApi {

    private final CustomerRepresentationModelAssembler assembler;
    private final AddressRepresentationModelAssembler addrAssembler;
    private final CardRepresentationModelAssembler cardAssembler;
    private final CustomerService service;
    private final ConfigurationFactory config;

    private String traceId;
    private ApiMetadata apiMetadata;

    public CustomerController(CustomerService service, CustomerRepresentationModelAssembler assembler,
            AddressRepresentationModelAssembler addrAssembler,
            CardRepresentationModelAssembler cardAssembler, ConfigurationFactory config) {
        this.service = service;
        this.assembler = assembler;
        this.addrAssembler = addrAssembler;
        this.cardAssembler = cardAssembler;
        this.config = config;
        
        this.traceId = config.getTraceId();
        this.apiMetadata = config.createResponseMetadata();
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<AppResponse> deleteCustomerById(String id) {
        service.deleteCustomerById(id);
        return accepted().build();
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<AppResponse> getAddressesByCustomerId(String id) {
        Optional<List<Address>> optAddresses = service.getAddressesByCustomerId(id).map(addrAssembler::toListModel);

        if (optAddresses.isPresent()) {
            return ok(AppResponse.success(optAddresses.get(), this.traceId, apiMetadata));
        } else {
            return notFound().build();
        }
    }

    /**
     *
     * @return
     */
    @Override
    public ResponseEntity<AppResponse> getAllCustomers() {
        List<Customer> users = assembler.toListModel(service.getAllCustomers());
        return ok(AppResponse.success(users, this.traceId, apiMetadata));
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<AppResponse> getCardsByCustomerId(String id) {
        Optional<List<Card>> cardsOpt = service.getCardsByCustomerId(id).map(cardAssembler::toListModel);

        if (cardsOpt.isPresent()) {
            return ok(AppResponse.success(cardsOpt.get(), this.traceId,apiMetadata));
        } else {
            return notFound().build();
        }
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<AppResponse> getCustomerById(String id) {
        Optional<Customer> usersOpt = service.getCustomerById(id).map(assembler::toModel);

        if (usersOpt.isPresent()) {
            return ok(AppResponse.success(usersOpt.get(), this.traceId, apiMetadata));
        } else {
            return notFound().build();
        }
    }
}
