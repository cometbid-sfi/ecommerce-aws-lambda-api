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

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.accepted;
import static org.springframework.http.ResponseEntity.status;
import java.util.List;
import java.util.Optional;
import org.cometbid.swaggerCodeGen.api.AddressApi;
import org.cometbid.swaggerCodeGen.model.AddAddressReq;
import org.cometbid.swaggerCodeGen.model.Address;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;
import org.cometbid.component.api.response.model.AppResponseMetadata;
import org.cometbid.component.api.response.model.AppResponse;
import org.cometbid.sample.template.payroll.config.ConfigurationFactory;

/**
 *
 * @author samueladebowale
 */
@Log4j2
@RestController
public class AddressController implements AddressApi {

    private final AddressService service;
    private final AddressRepresentationModelAssembler assembler;    
    private final AppResponseMetadata apiMetadata;

    public AddressController(AddressService addressService, AddressRepresentationModelAssembler assembler,
            ConfigurationFactory config) {
        this.service = addressService;
        this.assembler = assembler;
    
        this.apiMetadata = config.createResponseMetadata();
    }

    /**
     *
     * @param addAddressReq
     * @return
     */
    @Override
    public ResponseEntity<AppResponse> createAddress(AddAddressReq addAddressReq) {
        Optional<Address> addressOpt = service.createAddress(addAddressReq).map(assembler::toModel);

        if (addressOpt.isPresent()) {
            Address address = addressOpt.get();
            return status(HttpStatus.CREATED).body(AppResponse.success(address));
        }
        return accepted().body(AppResponse.success("operation failed"));
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<AppResponse> deleteAddressesById(String id) {
        service.deleteAddressesById(id);

        return ok(AppResponse.success("Delete was successful"));
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<AppResponse> getAddressesById(String id) {
        Optional<Address> optAddress = service.getAddressesById(id).map(assembler::toModel);

        if (optAddress.isPresent()) {
            return ok(AppResponse.success(optAddress.get()));
        } else {
            return notFound().build();
        }
    }

    /**
     *
     * @return
     */
    @Override
    public ResponseEntity<AppResponse> getAllAddresses() {
        List<Address> addresses = assembler.toListModel(service.getAllAddresses());
        return ok(AppResponse.success(addresses));
    }
}
