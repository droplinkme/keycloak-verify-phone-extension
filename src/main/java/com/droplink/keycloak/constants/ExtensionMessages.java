/*
 * Extension validate phone number.
 * @author Allan Vieira (allancnfx.vieira@gmail.com)
 * @version 1.0
 */
package com.droplink.keycloak.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExtensionMessages {
  public static final String PHONE_NOT_CONFIGURED = "Phone number not configured.";
  public static final String EXPIRED_OTP = "Expired OTP. Please try again.";
  public static final String INVALID_OTP = "Invalid OTP. Please try again.";
  public static final String SMS_SEND_FAILED = "Failed to send SMS. Please try again.";
}
