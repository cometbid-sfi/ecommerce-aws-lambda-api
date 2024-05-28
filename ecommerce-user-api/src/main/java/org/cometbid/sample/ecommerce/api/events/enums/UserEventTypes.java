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

import lombok.Getter;

/**
 *
 * @author samueladebowale
 */
public enum UserEventTypes {

    ON_USER_REGISTRATION_COMPLETE("OnUserRegistrationCompleteEvent"),
    ON_USER_SIGNUP_COMPLETE("OnUserSignUpCompleteEvent"),
    ON_PASSWORD_CHANGED_COMPLETE("OnPasswordChangeCompleteEvent"),
    ON_USERNAME_REQUEST_COMPLETE("OnUsernameRequestCompleteEvent"),
    ON_PASSWORD_RESET_REQUEST("OnPasswordResetRequestEvent"),
    ON_MEMBER_REGISTRATION_REQUEST("OnMemberRegistrationRequestEvent"),
    ON_MEMBER_REGISTRATION_COMPLETE("OnMemberRegistrationCompleteEvent"),
    ON_PASSWORD_RESET_COMPLETE("OnPasswordUpdateCompleteEvent"),
    ON_SUCCESS_LOGIN("OnSuccessfulLoginEvent"),
    ON_FAILED_LOGIN("OnFailedLoginEvent"),
    ON_UNKNOWN_DEVICE_LOGIN("OnUnknownDeviceLoginEvent"),
    ON_NEW_LOGIN_LOCATION("OnNewLoginLocationEvent"),
    ON_DIFFERENT_LOGIN_LOCATION("OnDifferentLocationLoginEvent"),
    ON_ACTIVATION_TOKEN_RENEWAL_REQUEST("OnEmailActivationRenewalRequestEvent"),
    ON_SOCIAL_USER_SIGNUP_COMPLETE("OnSocialUserSignUpCompleteEvent"),
    ON_OTPCODE_REQUEST("OnOtpRequestEvent");

    @Getter
    private final String name;

    UserEventTypes(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    // Implementing a fromString method on an enum type
    private static final Map<String, UserEventTypes> stringToEnum = new HashMap<>();

    static { // Initialize map from constant name to enum constant
        for (UserEventTypes op : values()) {
            stringToEnum.put(op.toString(), op);
        }
    }

    // Returns Operation for string, or null if string is invalid
    public static UserEventTypes fromString(String typeName) {
        return stringToEnum.get(typeName);
    }

    public static Set<String> getAllTypes() {
        return stringToEnum.keySet();
    }
}
