/*
 * Extension validate phone number.
 * @author Allan Vieira (allancnfx.vieira@gmail.com)
 * @version 1.0
 */
package com.droplink.keycloak.helpers;

import java.security.SecureRandom;

import lombok.experimental.UtilityClass;

@UtilityClass
public class OTPHelper {
  private static final SecureRandom random = new SecureRandom();
  public static final String generateOTP(int codeLength) {
        if (codeLength <= 0) {
            throw new IllegalArgumentException("Code length must be greater than 0");
        }

        StringBuilder otp = new StringBuilder(codeLength);
        for (int i = 0; i < codeLength; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
  }
    
  public static final long getTimestamp() {
    return System.currentTimeMillis();
  }

  public static boolean isOTPExprired(long otpTimestamp, long ttl) {
    long currentTime = System.currentTimeMillis();
    return (currentTime - otpTimestamp) > (ttl * 1000);
  }

  public static boolean isOTPExprired(String otpTimestamp, long ttl) {
    if (otpTimestamp == null) {
      return false;
    }

    return isOTPExprired(Long.parseLong(otpTimestamp), ttl);
  }
}
