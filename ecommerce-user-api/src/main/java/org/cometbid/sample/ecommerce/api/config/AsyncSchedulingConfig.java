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

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.cometbid.sample.ecommerce.api.common.util.DateUtils;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import lombok.extern.log4j.Log4j2;
import org.cometbid.sample.ecommerce.api.common.util.CustomThreadFactoryBuilder;
import org.cometbid.sample.ecommerce.api.event.handler.CustomAsyncExceptionHandler;
import org.springframework.scheduling.support.TaskUtils;

/**
 *
 * @author samueladebowale
 */
@Log4j2
@Configuration
@EnableAsync
@EnableScheduling
@RequiredArgsConstructor
public class AsyncSchedulingConfig implements AsyncConfigurer, SchedulingConfigurer {

    private final SchedulerConfig appConfig;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        try {
            taskRegistrar.setScheduler(taskExecutor());

        } catch (InterruptedException e) {

            log.error(String.format("Configuring Async Event Scheduler failed - %s", e.getMessage()), e);
        }
    }

    @Bean(name = "gatewaySchedulerThread", destroyMethod = "shutdown")
    public Executor taskExecutor() throws InterruptedException {
        
        ThreadFactory customThreadfactory = new CustomThreadFactoryBuilder()
                .setNamePrefix("Gateway-WorkerthreadPool")
                .setDaemon(false)
                .setPriority(Thread.MAX_PRIORITY)
                .setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {

                        log.error(String.format("Thread %s threw exception - %s", t.getName(), e.getMessage()), e);
                    }
                }).build();

        int awaitTerminationInMillis = appConfig.getAsyncThreadAwaitTime();
        int threadSize = appConfig.getAsyncThreadPoolSize();

        ExecutorService executorService = Executors.newFixedThreadPool(threadSize, customThreadfactory);
        executorService.awaitTermination(awaitTerminationInMillis, TimeUnit.MILLISECONDS);

        return executorService;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new CustomAsyncExceptionHandler();
    }

    @Override
    @Bean(name = "AsyncEventTaskExecutor")
    public Executor getAsyncExecutor() {
        final int MAX_SLEEP_TIME = appConfig.getMaxSleepTime();

        ThreadPoolTaskExecutor threadPoolExecutor = new ThreadPoolTaskExecutor();

        threadPoolExecutor.setAllowCoreThreadTimeOut(true);
        threadPoolExecutor.setAwaitTerminationSeconds(appConfig.getAsyncThreadAwaitTime());
        threadPoolExecutor.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolExecutor.setCorePoolSize(appConfig.getAsyncThreadPoolSize());
        threadPoolExecutor.setQueueCapacity(appConfig.getAsyncThreadCapacity());
        threadPoolExecutor.setMaxPoolSize(appConfig.getAsyncMaxThreadPoolSize());
        threadPoolExecutor.setThreadNamePrefix("AsyncEventTaskExecutor-");
        threadPoolExecutor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                log.info("Async #threadPoolTaskExecutor Task Rejected : {} time: {}", Thread.currentThread().getName(),
                        DateUtils.now());

                log.info("Waiting for a second !!");
                try {

                    Thread.sleep(MAX_SLEEP_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                log.info("Lets add another time : " + r);
                executor.execute(r);
            }
        });
        
        threadPoolExecutor.initialize();

        return threadPoolExecutor;
    }

    @Bean(name = "applicationEventMulticaster")
    public ApplicationEventMulticaster applicationEventMulticaster(@Qualifier("AsyncEventTaskExecutor") Executor taskExecutor) {
        var eventMulticaster = new SimpleApplicationEventMulticaster();

        eventMulticaster.setTaskExecutor(taskExecutor);
        eventMulticaster.setErrorHandler(TaskUtils.LOG_AND_PROPAGATE_ERROR_HANDLER);
        return eventMulticaster;
    }

}
