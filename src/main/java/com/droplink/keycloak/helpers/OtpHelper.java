package com.droplink.keycloak.helpers;

import org.json.JSONObject;
import org.keycloak.authentication.RequiredActionContext;
import org.keycloak.sessions.AuthenticationSessionModel;

import com.droplink.keycloak.constants.Constants;
import com.droplink.keycloak.utils.OTPUtils;

public final class OtpHelper {
  private OtpHelper() {}
  public static String generateOTP(RequiredActionContext context) {
    return OTPUtils.generateOTP(ConfigHelper.getOtpCodeLength(context));
  }
    
  public static void storeSessionOTP(RequiredActionContext context, String otp) {
    long timestamp = OTPUtils.getTimestamp();
    JSONObject data = (new JSONObject())
        .put("otp", otp)
        .put("timestamp", timestamp);

    SessionHelper.setValue(context, Constants.Session.OTP, data.toString());
  }
  
  public static JSONObject retrieveSessionOTP(RequiredActionContext context) {
    AuthenticationSessionModel session = context.getAuthenticationSession();
    String data = session.getAuthNote(Constants.Session.OTP);

    if (data != null) {
      return new JSONObject(data);
    }

    return null;
  }
  
  public static boolean isOTPSessionExpired(long otpTimestamp, long ttl) {
    if(otpTimestamp == 0) {
      return false;
    }
    return OTPUtils.isOTPExprired(otpTimestamp, ttl);
  }
}
