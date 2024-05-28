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
package com.cometbid.sample.ecommerce.api.metrics;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cloudwatch.CloudWatchAsyncClient;
import software.amazon.awssdk.services.cloudwatch.model.CloudWatchException;
import software.amazon.awssdk.services.cloudwatch.model.Dimension;
import software.amazon.awssdk.services.cloudwatch.model.MetricDatum;
import software.amazon.awssdk.services.cloudwatch.model.PutMetricDataRequest;
import software.amazon.awssdk.services.cloudwatch.model.StandardUnit;

/**
 *
 * @author samueladebowale
 */
@Log4j2
@Service
public class MetricPublisher {

    private final CloudWatchAsyncClient cloudWatchAsyncClient;

    @Autowired
    public MetricPublisher(CloudWatchAsyncClient cloudWatchAsyncClient) {
        super();
        this.cloudWatchAsyncClient = cloudWatchAsyncClient;
    }

    /**
     * 
     * @param nameSpace
     * @param metricName
     * @param dataPoint
     * @param metricTags 
     */
    public void putMetricData(final String nameSpace,
            final String metricName,
            final Double dataPoint,
            final List<MetricTag> metricTags) {

        try {

            List<Dimension> dimensions = metricTags
                    .stream()
                    .map((metricTag) -> {
                        return Dimension
                                .builder()
                                .name(metricTag.getName())
                                .value(metricTag.getValue())
                                .build();
                    }).collect(Collectors.toList());

            // Set an Instant object
            String time = ZonedDateTime
                    .now(ZoneOffset.UTC)
                    .format(DateTimeFormatter.ISO_INSTANT);
            Instant instant = Instant.parse(time);

            MetricDatum datum = MetricDatum
                    .builder()
                    .metricName(metricName)
                    .unit(StandardUnit.NONE)
                    .value(dataPoint)
                    .timestamp(instant)
                    .dimensions(dimensions)
                    .build();

            PutMetricDataRequest request
                    = PutMetricDataRequest
                            .builder()
                            .namespace(nameSpace)
                            .metricData(datum)
                            .build();

            cloudWatchAsyncClient.putMetricData(request);

        } catch (CloudWatchException e) {
            log.error(e.awsErrorDetails().errorMessage());
        }
    }
}
