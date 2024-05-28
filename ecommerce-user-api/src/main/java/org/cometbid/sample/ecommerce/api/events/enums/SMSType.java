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
package org.cometbid.sample.ecommerce.api.events.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.cometbid.sample.ecommerce.api.events.MessageEventType;

/**
 *
 * @author samueladebowale
 */
public enum SMSType implements MessageEventType {

    ACCT_BALANCE("ACCT_BALANCE"), 
    ACCT_STATUS("ACCT_STATUS"), 
    PASSWORD_RESET_NOTIFICATION("PASSWORD_RESET_REQUEST"),
    PASSWORD_CHANGED("PASSWORD_CHANGED");

    private final String name;

    SMSType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    // Implementing a fromString method on an enum type
    private static final Map<String, SMSType> stringToEnum = new HashMap<String, SMSType>();

    static { // Initialize map from constant name to enum constant
        for (SMSType op : values()) {
            stringToEnum.put(op.toString(), op);
        }
    }

    // Returns Operation for string, or null if string is invalid
    public static SMSType fromString(String typeName) {
        return stringToEnum.get(typeName);
    }

    public static Set<String> getAllTypes() {
        return stringToEnum.keySet();
    }
}
