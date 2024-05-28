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
package org.cometbid.sample.ecommerce.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

/**
 *
 * @author samueladebowale
 */
@Log4j2
@Getter
@Configuration
@PropertySource(value = {"classpath:springScheduled.properties"})
public class SchedulerConfig {

    @Value("${reactive.thread.maximum-pool-size:10}")
    private Integer connectionPoolSize;

    @Value(value = "${async.worker.capacity:10}")
    private Integer asyncThreadCapacity;

    @Value(value = "${async.worker.size:1}")
    private Integer asyncThreadPoolSize;

    @Value(value = "${async.worker.max_size:20}")
    private Integer asyncMaxThreadPoolSize;

    @Value(value = "${async.worker.thread.await_time:30}")
    private Integer asyncThreadAwaitTime;

    @Value(value = "${async.worker.aliveTime:1000}")
    private Integer KeepAliveTime;

    @Value(value = "${async.worker.thread.sleepTime.max:200}")
    private Integer maxSleepTime;

    @Value(value = "${event.worker.capacity:10}")
    private Integer eventThreadCapacity;

    @Value(value = "${event.worker.size:1}")
    private Integer eventThreadPoolSize;

    @Value(value = "${event.worker.max_size:20}")
    private Integer eventMaxThreadPoolSize;

}
