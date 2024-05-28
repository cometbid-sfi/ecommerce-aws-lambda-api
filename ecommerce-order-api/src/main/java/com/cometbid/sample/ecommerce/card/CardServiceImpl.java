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
package org.cometbid.sample.ecommerce.card;

import jakarta.validation.Valid;
import java.util.Optional;
import java.util.UUID;
import org.cometbid.sample.ecommerce.api.customer.CustomerEntity;
import org.cometbid.sample.ecommerce.api.customer.CustomerRepository;
import org.cometbid.swaggerCodeGen.model.AddCardReq;
import org.springframework.stereotype.Service;

/**
 *
 * @author samueladebowale
 */
@Service
public class CardServiceImpl implements CardService {

    private final CardRepository repository;
    private final CustomerRepository customerRepo;

    public CardServiceImpl(CardRepository repository, CustomerRepository userRepo) {
        this.repository = repository;
        this.customerRepo = userRepo;
    }

    @Override
    public void deleteCardById(String id) {
        repository.deleteById(UUID.fromString(id));
    }

    @Override
    public Iterable<CardEntity> getAllCards() {
        return repository.findAll();
    }

    @Override
    public Optional<CardEntity> getCardById(String id) {
        return repository.findById(UUID.fromString(id));
    }

    @Override
    public Optional<CardEntity> registerCard(@Valid AddCardReq addCardReq) {
        // add validation to make sure that only single card exists from one user
        // else it throws DataIntegrityViolationException for user_id (unique)
        return Optional.of(repository.save(toEntity(addCardReq)));
    }

    private CardEntity toEntity(AddCardReq m) {
        CardEntity e = new CardEntity();
        Optional<CustomerEntity> user = customerRepo.findById(UUID.fromString(m.getUserId()));
        user.ifPresent(e::setUser);
        return e.setNumber(m.getCardNumber()).setCvv(m.getCvv()).setExpires(m.getExpires());
    }
}
