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
package org.cometbid.sample.ecommerce.api.product;

import java.util.List;
import java.util.Optional;

import org.cometbid.sample.ecommerce.api.config.ConfigurationFactory;
import org.cometbid.sample.ecommerce.user.api.service.ProductService;
import org.cometbid.swaggerCodeGen.api.ProductApi;
import org.cometbid.swaggerCodeGen.model.Image;
import org.cometbid.swaggerCodeGen.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.cometbid.component.api.response.ApiMetadata;
import org.cometbid.component.api.response.AppResponse;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

/**
 *
 * @author samueladebowale
 */
@Log4j2
@RestController
public class ProductController implements ProductApi {

    private final ProductService service;
    private final ProductRepresentationModelAssembler assembler;
    private final ConfigurationFactory config;

    private String traceId;
    private ApiMetadata apiMetadata;

    public ProductController(ProductService service,
            ProductRepresentationModelAssembler assembler,
            ConfigurationFactory config) {
        this.service = service;
        this.assembler = assembler;
        this.config = config;
        
        this.traceId = config.getTraceId();
        this.apiMetadata = config.createResponseMetadata();
    }

    /**
     *
     * @param product
     * @return
     */
    @Override
    public ResponseEntity<AppResponse> addProduct(Product product) {
        return null;
    }

    /**
     *
     * @param id
     * @param apiKey
     * @return
     */
    @Override
    public ResponseEntity<Void> deleteProduct(String id, String apiKey) {
        return null;
    }

    /**
     *
     * @param tags
     * @return
     */
    @Override
    public ResponseEntity<AppResponse> findProductsByTags(List<String> tags) {
        return null;
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<AppResponse> getProductById(String id) {
        Optional<Product> productOpt = service.getProduct(id).map(assembler::toModel);

        if (productOpt.isPresent()) {
            return ok(AppResponse.success(productOpt.get(), this.traceId, apiMetadata));
        } else {
            return notFound().build();
        }
    }

    /**
     *
     * @param status
     * @return
     */
    @Override
    public ResponseEntity<AppResponse> getProductsByStatus(String status) {
        return null;
    }

    /**
     *
     * @param tag
     * @param name
     * @param page
     * @param size
     * @return
     */
    @Override
    public ResponseEntity<AppResponse> queryProducts(String tag, String name, Integer page, Integer size) {
        List<Product> products = assembler.toListModel(service.getAllProducts());
        return ok(AppResponse.success(products, this.traceId, apiMetadata));
    }

    /**
     *
     * @param product
     * @return
     */
    @Override
    public ResponseEntity<AppResponse> updateProduct(Product product) {
        return null;
    }

    /**
     *
     * @param id
     * @param status
     * @param name
     * @return
     */
    @Override
    public ResponseEntity<AppResponse> updateProductWithForm(String id, String status, String name) {
        return null;
    }

    /**
     *
     * @param id
     * @param length
     * @param schema
     * @return
     */
    @Override
    public ResponseEntity<AppResponse> uploadFile(String id, Integer length, List<@Valid Image> schema) {
        return null;
    }
}
