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

import java.util.Optional;
import static org.springframework.http.ResponseEntity.accepted;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;
import org.cometbid.sample.ecommerce.api.config.ConfigurationFactory;
import org.cometbid.sample.ecommerce.api.order.CardRepresentationModelAssembler;
import org.cometbid.sample.ecommerce.api.order.CardService;
import org.cometbid.swaggerCodeGen.api.CardApi;
import org.cometbid.swaggerCodeGen.model.AddCardReq;
import org.cometbid.swaggerCodeGen.model.Card;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.log4j.Log4j2;
import java.util.List;
import org.cometbid.component.api.response.ApiMetadata;
import org.cometbid.component.api.response.AppResponse;
import org.springframework.http.HttpStatus;

/**
 *
 * @author samueladebowale
 */
@Log4j2
@RestController
public class CardController implements CardApi {

    private final CardService service;
    private final CardRepresentationModelAssembler assembler;
    private final ConfigurationFactory config;

    private String traceId;
    private ApiMetadata apiMetadata;

    public CardController(CardService service, CardRepresentationModelAssembler assembler,
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
    public ResponseEntity<Void> deleteCardById(String id) {
        service.deleteCardById(id);
        return accepted().build();
    }

    /**
     *
     * @return
     */
    @Override
    public ResponseEntity<AppResponse> getAllCards() {
        List<Card> cards = assembler.toListModel(service.getAllCards());
        return ok(AppResponse.success(cards, this.traceId, apiMetadata));
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<AppResponse> getCardById(String id) {
        Optional<Card> cardOpt = service.getCardById(id).map(assembler::toModel);
        if (cardOpt.isPresent()) {
            Card card = cardOpt.get();
            return ok(AppResponse.success(card, this.traceId, apiMetadata));
        }
        return notFound().build();
    }

    /**
     *
     * @param addCardReq
     * @return
     */
    @Override
    public ResponseEntity<AppResponse> registerCard(AddCardReq addCardReq) {
        Optional<Card> cardOpt = service.registerCard(addCardReq).map(assembler::toModel);
        if (cardOpt.isPresent()) {
            Card card = cardOpt.get();
            return status(HttpStatus.CREATED).body(AppResponse.success(card, this.traceId, apiMetadata));
        }

        return accepted().body(AppResponse.success("operation failed", this.traceId, apiMetadata));
    }
}
