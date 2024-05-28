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
package org.cometbid.sample.ecommerce.api.event.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.awspring.cloud.sns.core.SnsTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.cometbid.sample.ecommerce.api.config.AwsCloudProperties;
import org.cometbid.sample.ecommerce.api.events.EmailMessageFactory;
import org.cometbid.sample.ecommerce.api.user.UserCreatedEventDto;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 *
 * @author samueladebowale
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class ApplicationEventNotifierListener {

    private final SnsTemplate snsTemplate;
    private final AwsCloudProperties awsSnsTopicProperties;
    private final EmailMessageFactory emailMessageBuilder;

    /*
    @Async
    @EventListener
    public void handleUserRegisteredEvent(final PayloadApplicationEvent event) {
        UserEventTypes authEventTypes = event.getEventTypes();

        switch (authEventTypes) {
            case ON_USER_SIGNUP_COMPLETE ->
                this.sendEmailVerificationNotification(event);
            case ON_SOCIAL_USER_SIGNUP_COMPLETE ->
                this.sendTemporaryPasswordEmail(event);
            case ON_PASSWORD_RESET_REQUEST ->
                this.confirmPasswordResetRequest(event);
            case ON_USERNAME_REQUEST_COMPLETE ->
                this.sendUsernameRequestConfirmation(event);
            case ON_USER_REGISTRATION_COMPLETE ->
                this.confirmUserRegistration(event);
            case ON_PASSWORD_RESET_COMPLETE ->
                this.sendUserPasswordResetCompletedNotification(event);
            case ON_DIFFERENT_LOGIN_LOCATION ->
                this.handleDifferentLocationLoginEvent(event);
            default ->
                log.error("Invalid User Auth event type specified....");
        }
    }
     */
    @Async("AsyncEventTaskExecutor")
    @EventListener
    public void sendEmailVerificationNotification(PayloadApplicationEvent<UserCreatedEventDto> event) {
        final var topicArn = awsSnsTopicProperties.getTopicArn();

        try {
            final String finalPayload = emailMessageBuilder.buildUserCreatedEmailMessage(event.getPayload());

            snsTemplate.convertAndSend(topicArn, finalPayload);
            log.info("Successfully published message to topic ARN: {}", topicArn);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
    }

    private void sendTemporaryPasswordEmail(PayloadApplicationEvent event) {

    }

    private void confirmPasswordResetRequest(PayloadApplicationEvent event) {

    }

    private void sendUsernameRequestConfirmation(PayloadApplicationEvent event) {

    }

    private void confirmUserRegistration(PayloadApplicationEvent event) {

    }

    private void sendUserPasswordResetCompletedNotification(PayloadApplicationEvent event) {

    }

    private void handleDifferentLocationLoginEvent(PayloadApplicationEvent event) {

    }
}
