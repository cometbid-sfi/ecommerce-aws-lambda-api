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
package org.cometbid.sample.ecommerce.api.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.cometbid.component.api.response.model.AppResponse;
import org.cometbid.sample.ecommerce.api.user.UserCreationRequestDto;
import org.cometbid.sample.ecommerce.api.user.UserEntity;
import org.cometbid.sample.ecommerce.api.user.UserService;
import org.cometbid.swaggerCodeGen.api.UserApi;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author samueladebowale
 */
@Log4j2
@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    /**
     *
     * @param userCreationRequest
     * @return
     */
    @Override
    public ResponseEntity<AppResponse> createUserEntity(@Valid @RequestBody UserCreationRequestDto userCreationRequest) {
        userService.create(userCreationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     *
     * @param user
     * @return
     */
    @Override
    public ResponseEntity<AppResponse> createUserEntitysWithList(List<@Valid UserEntity> user) {
        return null;
    }

    /**
     *
     * @param username
     * @return
     */
    @Override
    public ResponseEntity<Void> deleteUserEntity(String username) {
        return null;
    }

    /**
     *
     * @param username
     * @return
     */
    @Override
    public ResponseEntity<AppResponse> getUserEntityByName(String username) {
        return null;
    }

    /**
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public ResponseEntity<AppResponse> loginUserEntity(String username, String password) {
        return null;

    }

    /**
     *
     * @return
     */
    @Override
    public ResponseEntity<AppResponse> logoutUserEntity() {
        return null;

    }

    /**
     *
     * @param username
     * @param user
     * @return
     */
    @Override
    public ResponseEntity<AppResponse> updateUserEntity(String username, UserEntity user) {
        return null;

    }
}
