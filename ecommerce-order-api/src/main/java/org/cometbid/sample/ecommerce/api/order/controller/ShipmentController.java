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

import lombok.extern.log4j.Log4j2;

import java.util.List;
import static org.springframework.http.ResponseEntity.ok;
import org.cometbid.sample.ecommerce.api.config.ConfigurationFactory;
import org.cometbid.swaggerCodeGen.api.ShippingApi;
import org.cometbid.swaggerCodeGen.model.Authorization;
import org.cometbid.swaggerCodeGen.model.Shipment;
import org.cometbid.swaggerCodeGen.model.ShippingReq;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.cometbid.component.api.response.ApiMetadata;
import org.cometbid.component.api.response.AppResponse;

/**
 *
 * @author samueladebowale
 */
@Log4j2
@RestController
public class ShipmentController implements ShippingApi {

    private final ShipmentService service;
    private final ShipmentRepresentationModelAssembler assembler;
    private final ConfigurationFactory config;

    private String traceId;
    private ApiMetadata apiMetadata;

    public ShipmentController(ShipmentService service,
            ShipmentRepresentationModelAssembler assembler,
            ConfigurationFactory config) {
        this.service = service;
        this.assembler = assembler;
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
    public ResponseEntity<AppResponse> getShipmentByOrderId(@NotNull @Valid String id) {
        List<Shipment> shipmentList = assembler.toListModel(service.getShipmentByOrderId(id));
        return ok(AppResponse.success(shipmentList, this.traceId, apiMetadata));
    }

    /**
     *
     * @param shippingReq
     * @return
     */
    @Override
    public ResponseEntity<Authorization> shipOrder(ShippingReq shippingReq) {
        return null;

    }
}
