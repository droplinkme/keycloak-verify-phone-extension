/*
 * Extension validate phone number.
 * @author Allan Vieira (allancnfx.vieira@gmail.com)
 * @version 1.0
 */
package com.droplink.keycloak.exceptions;

public class SmsSendingException extends RuntimeException {
     public SmsSendingException(String message, Throwable cause) {
        super(message, cause);
    }
}
