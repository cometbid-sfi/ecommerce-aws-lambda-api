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
package org.cometbid.sample.ecommerce.notifier.email;

import lombok.extern.log4j.Log4j2;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.annotation.SnsNotificationMessage;
import org.cometbid.sample.ecommerce.notifier.config.AwsCloudProperties;
import org.cometbid.sample.ecommerce.notifier.models.UserCreatedEventDto;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 *
 * @author samueladebowale
 */
@Log4j2
@Component
@EnableConfigurationProperties(AwsCloudProperties.class)
public class EmailNotificationListener {

    private final EmailNotificationService emailService;

    public EmailNotificationListener(EmailNotificationService emailService) {
        this.emailService = emailService;
    }

    @SqsListener("${org.cometbid.aws.sqs.queue-url}")
    public void listen(@SnsNotificationMessage final UserCreatedEventDto userCreatedEvent) {
        log.info("Dispatching account creation email to {} on {}",
                userCreatedEvent.getName(),
                userCreatedEvent.getEmailId());
        // business logic to send email
    }

}
