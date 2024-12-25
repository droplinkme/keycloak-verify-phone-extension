package com.droplink.keycloak.services.validations;

import org.json.JSONObject;
import org.keycloak.authentication.RequiredActionContext;

import com.droplink.keycloak.constants.Constants;
import com.droplink.keycloak.helpers.FormHelper;
import com.droplink.keycloak.helpers.OtpHelper;

public class ShouldSendOTPService {
  public static boolean exec(RequiredActionContext context) {
      JSONObject data = OtpHelper.retrieveSessionOTP(context);
      Long existingOtpTimestamp = data != null ? data.getLong("timestamp") : 0;
      long ttl = Long.parseLong(context.getConfig().getConfigValue(Constants.Config.Code.TTL, "3600"));
      String isResendOtp = FormHelper.getValue(context, Constants.Form.PHONE_OTP_RESEND);
      String isSendOtp = FormHelper.getValue(context, Constants.Form.PHONE_OTP_CODE_SEND);
      boolean otpExpired = OtpHelper.isOTPSessionExpired(existingOtpTimestamp, ttl);

      if("true".equals(isSendOtp)) {
        return true;
      }

      return (data == null || otpExpired || "true".equals(isResendOtp));
  }
}
