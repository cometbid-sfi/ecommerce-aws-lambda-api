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
package org.cometbid.sample.ecommerce.api.cart;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;
import org.cometbid.sample.ecommerce.user.api.service.ItemService;
import org.cometbid.swaggerCodeGen.model.Cart;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 *
 * @author samueladebowale
 */
@Component
public class CartRepresentationModelAssembler extends
        RepresentationModelAssemblerSupport<CartEntity, Cart> {

    private final ItemService itemService;

    /**
     * Creates a new {@link RepresentationModelAssemblerSupport} using the given
     * controller class and resource type.
     * @param itemService
     */
    public CartRepresentationModelAssembler(ItemService itemService) {
        super(CartController.class, Cart.class);
        this.itemService = itemService;
    }

    /**
     * Coverts the Card entity to resource
     *
     * @param entity
     */
    @Override
    public Cart toModel(CartEntity entity) {
        String uid = Objects.nonNull(entity.getUser()) ? entity.getUser().getId().toString() : null;
        String cid = Objects.nonNull(entity.getId()) ? entity.getId().toString() : null;
        Cart resource = new Cart();
        BeanUtils.copyProperties(entity, resource);

        resource.id(cid).customerId(uid).items(itemService.toModelList(entity.getItems()));
        resource.add(linkTo(methodOn(CartController.class).getCartByCustomerId(uid)).withSelfRel());
        resource.add(linkTo(methodOn(CartController.class).getCartItemsByCustomerId(uid))
                .withRel("cart-items"));
        return resource;
    }

    /**
     * Coverts the collection of Product entities to list of resources.
     *
     * @param entities
     * @return 
     */
    public List<Cart> toListModel(Iterable<CartEntity> entities) {
        if (Objects.isNull(entities)) {
            return List.of();
        }
        return StreamSupport.stream(entities.spliterator(), false).map(this::toModel)
                .collect(toList());
    }

}
