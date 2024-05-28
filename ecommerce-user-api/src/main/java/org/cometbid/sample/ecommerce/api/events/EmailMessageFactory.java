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
package org.cometbid.sample.ecommerce.api.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.cometbid.sample.ecommerce.api.events.enums.EmailType;
import org.springframework.stereotype.Component;

/**
 *
 * @author samueladebowale
 */
@Component
@RequiredArgsConstructor
public class EmailMessageFactory {

    private final EventPayloadMappers payloadMapper;

    /**
     *
     * @param payloadDto
     * @return
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     */
    public String buildUserCreatedEmailMessage(EventPayloadType payloadDto) throws JsonProcessingException {
        // Process the Event source by sending a message to Messaging Broker

        String eventId = UUID.randomUUID().toString();
        final EmailEvent finalPayload = new EmailEvent(EmailType.USER_PROFILE_CREATION, eventId, payloadDto);

        return payloadMapper.withJackson(finalPayload);
    }
}
