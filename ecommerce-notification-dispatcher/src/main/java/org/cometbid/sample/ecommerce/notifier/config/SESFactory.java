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
package org.cometbid.sample.ecommerce.notifier.config;

import java.util.Properties;
import org.springframework.util.ResourceUtils;

import lombok.extern.log4j.Log4j2;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;

/**
 *
 * @author samueladebowale
 */
@Log4j2
public class SESFactory {

    private static SESFactory me;

    private Properties properties;

    private SESFactory() {

    }

    public static SESFactory getInstance() {
        if (me == null) {
            me = new SESFactory();
        }

        me.properties = fetchProperties();
        return me;
    }

    public static Properties fetchProperties() {
        Properties properties = new Properties();
        try {
            File file = ResourceUtils.getFile("classpath:SESConfiguration.properties");
            InputStream in = new FileInputStream(file);
            properties.load(in);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return properties;
    }

    public String getSupportEmail() {

        return (String) properties.get("support.team.email");
    }

    public String getNoReplyEmail() {

        return (String) properties.get("noreply.email");
    }

    public String getSupportName() {

        return (String) properties.get("support.team.name");
    }

    public String getNoReplyName() {

        return (String) properties.get("noreply.name");
    }

}
