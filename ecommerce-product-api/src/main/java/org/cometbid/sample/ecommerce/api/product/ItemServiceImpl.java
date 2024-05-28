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
package org.cometbid.sample.ecommerce.user.api.service;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.cometbid.sample.ecommerce.api.product.ItemEntity;
import org.cometbid.sample.ecommerce.api.entity.ProductEntity;
import org.cometbid.swaggerCodeGen.model.Item;
import org.springframework.stereotype.Service;

/**
 *
 * @author samueladebowale
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Override
    public ItemEntity toEntity(Item m) {
        ItemEntity e = new ItemEntity();
        e.setProduct(new ProductEntity().setId(UUID.fromString(m.getId()))).setPrice(m.getUnitPrice())
                .setQuantity(m.getQuantity());
        return e;
    }

    @Override
    public List<ItemEntity> toEntityList(List<Item> items) {
        if (Objects.isNull(items)) {
            return List.of();
        }
        return items.stream().map(this::toEntity).collect(toList());
    }

    @Override
    public Item toModel(ItemEntity e) {
        Item m = new Item();
        m.id(e.getProduct().getId().toString()).unitPrice(e.getPrice()).quantity(e.getQuantity());
        return m;
    }

    @Override
    public List<Item> toModelList(List<ItemEntity> items) {
        if (Objects.isNull(items)) {
            return List.of();
        }
        return items.stream().map(this::toModel).collect(toList());
    }
}
