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
package org.cometbid.sample.ecommerce.api.payment;

import org.cometbid.swaggerCodeGen.api.PaymentApi;
import org.cometbid.swaggerCodeGen.model.Authorization;
import org.cometbid.swaggerCodeGen.model.PaymentReq;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.log4j.Log4j2;

/**
 *
 * @author samueladebowale
 */
@Log4j2
@RestController
public class PaymentController implements PaymentApi {

    /**
     *
     * @param paymentReq
     * @return
     */
    @Override
    public ResponseEntity<Authorization> authorize(PaymentReq paymentReq) {
        return null;
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<Authorization> getOrdersPaymentAuthorization(@NotNull @Valid String id) {
        return null;

    }
}
