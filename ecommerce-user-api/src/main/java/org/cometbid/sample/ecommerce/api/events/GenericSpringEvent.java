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

import org.cometbid.sample.ecommerce.api.events.enums.ContentType;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 *
 * @author samueladebowale
 * @param <T>
 */
@Getter
public class GenericSpringEvent<T> extends ApplicationEvent {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private T what;
    protected Object source;
    protected String message;
    protected boolean success;
    private ContentType contentActedOn;
    private Object payload;

    public GenericSpringEvent(T what, Object source, String message, ContentType content) {
        super(source);
        this.what = what;
        this.source = source;
        this.message = message;
        this.contentActedOn = content;
        this.success = true;
        this.payload = null;
    }

    public GenericSpringEvent(T what, Object source, String message, ContentType content,
            Object payload) {
        super(source);
        this.what = what;
        this.source = source;
        this.message = message;
        this.contentActedOn = content;
        this.success = true;
        this.payload = payload;
    }

    public T getWhat() {
        return this.what;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        return "GenericSpringEvent [what=" + what
                + ", source=" + source
                + ", message=" + message
                + ", success=" + success
                + ", contentActedOn=" + contentActedOn
                + ", payload=" + payload
                + "]";
    }

}
