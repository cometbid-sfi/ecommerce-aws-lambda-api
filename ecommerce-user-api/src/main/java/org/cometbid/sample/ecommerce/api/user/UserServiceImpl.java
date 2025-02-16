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
package org.cometbid.sample.ecommerce.api.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import lombok.NonNull;
import org.cometbid.component.api.util.TimeZoneUtils;
import org.cometbid.sample.ecommerce.api.config.AwsCloudProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.PayloadApplicationEvent;

/**
 *
 * @author samueladebowale
 */
@Log4j2
@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(AwsCloudProperties.class)
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void create(@NonNull final UserCreationRequestDto userCreationRequest) {
        // save user record in database

        sendNotificationEvent(userCreationRequest);
    }

    private void sendNotificationEvent(@NonNull final UserCreationRequestDto userCreationRequest) {

        final var userCreatedEvent = new UserCreatedEventDto(userCreationRequest.getEmailId(),
                userCreationRequest.getName(), TimeZoneUtils.getOffsetDateTimeInUTC());

        PayloadApplicationEvent<UserCreatedEventDto> payload = new PayloadApplicationEvent<>(
                this, userCreatedEvent);

        eventPublisher.publishEvent(payload);
    }
}
