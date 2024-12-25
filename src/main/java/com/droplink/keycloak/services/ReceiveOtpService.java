package com.droplink.keycloak.services;

import org.jboss.logging.Logger;
import org.json.JSONObject;
import org.keycloak.authentication.RequiredActionContext;

import com.droplink.keycloak.constants.Constants;
import com.droplink.keycloak.constants.ExtensionMessages;
import com.droplink.keycloak.helpers.ConfigHelper;
import com.droplink.keycloak.helpers.FormHelper;
import com.droplink.keycloak.helpers.OtpHelper;
import com.droplink.keycloak.helpers.PageChallengeHelper;
import com.droplink.keycloak.helpers.SessionHelper;
import com.droplink.keycloak.interfaces.IExecutable;
import com.droplink.keycloak.interfaces.IStringFunction;

public class ReceiveOtpService implements IExecutable {

    private static final Logger LOGGER = Logger.getLogger(ReceiveOtpService.class);

    @Override
    public void exec(RequiredActionContext context) {
        LOGGER.info("Starting ReceiveOtpService execution");
        String otp = FormHelper.getValue(context, Constants.Form.OTP);
        String phone = SessionHelper.getValue(context, Constants.Attributes.PHONE_USER_ATTRIBUTE);
        JSONObject data = OtpHelper.retrieveSessionOTP(context);
        
        if (isInvalid(otp, phone, data, context)) {
            return;
        }

       markPhoneAsVerified(context);
    }

    private boolean isInvalid(String otp, String phone, JSONObject data, RequiredActionContext context) {
        IStringFunction[] conditions = new IStringFunction[]{
            () -> (otp == null || phone == null) ? ExtensionMessages.MISSING_REQUIRED_FIELDS : null,
            () -> (data == null) ? ExtensionMessages.MISSING_OTP_SESSION : null,
            () -> isOtpExpired(context, data) ? ExtensionMessages.EXPIRED_OTP : null,
            () -> !isOtpValid(otp, data) ? ExtensionMessages.INVALID_OTP : null,
        };

        for (IStringFunction condition : conditions) {
            String message = condition.apply();
            if (message != null) {
                failure(context, phone, message);
                return true;
            }
        }
        return false;
    }

    private boolean isOtpExpired(RequiredActionContext context, JSONObject data) {
        long ttl = ConfigHelper.getOtpCodeTtl(context);
        long otpTimestamp = data.getLong("timestamp");
        return OtpHelper.isOTPSessionExpired(otpTimestamp, ttl);
    }

    private boolean isOtpValid(String otp, JSONObject data) {
      return otp.equals(data.getString("otp"));
    }
    
    private void failure(RequiredActionContext context, String phone, String message) {
      LOGGER.warn(message);
      PageChallengeHelper.createPageChallengeReceiveOTP(context, phone, message);
    }
    
    private void markPhoneAsVerified(RequiredActionContext context) {
        context.getUser().setSingleAttribute(Constants.Attributes.PHONE_VERIFIED_USER_ATTRIBUTE, "true");
        LOGGER.info("OTP validation successful, phone verified.");
        context.success();
    }
}
