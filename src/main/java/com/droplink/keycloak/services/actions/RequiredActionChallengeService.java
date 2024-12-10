/*
 * Extension validate phone number.
 * @author Allan Vieira (allancnfx.vieira@gmail.com)
 * @version 1.0
 */
package com.droplink.keycloak.services.actions;

import org.keycloak.authentication.RequiredActionContext;

import com.droplink.keycloak.constants.ExtensionConstants;
import com.droplink.keycloak.constants.ExtensionMessages;
import com.droplink.keycloak.contexts.RequiredPhoneContext;
import com.droplink.keycloak.providers.sms.interfaces.ISmsProvider;

public class RequiredActionChallengeService extends RequiredPhoneContext {
  
  private final ISmsProvider smsProvider;

  public RequiredActionChallengeService(ISmsProvider smsProvider) {
    this.smsProvider = smsProvider;
  }
  
  public void exec(RequiredActionContext context) {
    if (!validatePhoneConfiguration(context)) {
        return;
    }

    if (shouldSendOtp(context)) {
      generateAndSendOtp(context);
      createPageChallenge(context, true);
      return;
    }

    createPageChallenge(context, false);
  }
  

  private void generateAndSendOtp(RequiredActionContext context) {
    String otp = generateAndStoreOtp(context);
    String phoneNumber = getUserPhoneNumberValue(context);
    String template = context.getConfig().getConfigValue(ExtensionConstants.CODE_TEMPLATE_SMS,
        "Your verification code is: {code}");
    boolean isSimulationMode = Boolean
        .parseBoolean(context.getConfig().getConfigValue(ExtensionConstants.CODE_SIMULATION_MODE, "false"));
    String message = template.replace("{code}", otp);
    try {
      smsProvider.sendOtp(message, phoneNumber, isSimulationMode);
    } catch (Exception e) {
      context.getEvent().error("sms_send_failure");
      createPageChallengeFailure(context, ExtensionMessages.SMS_SEND_FAILED);
    }
  }
  
  private boolean shouldSendOtp(RequiredActionContext context) {
      long ttl = Long.parseLong(context.getConfig().getConfigValue(ExtensionConstants.CODE_TTL, "3600"));
      String existingOtp = getSessionAuthNoteValue(context, ExtensionConstants.OTP_SESSION_AUTH_NOTE_ATTRIBUTE);
      String isResendOtp = getFormValue(context, ExtensionConstants.OTP_RESEND_FORM_ATTRIBUTE);
      String isSendOtp = getFormValue(context, ExtensionConstants.OTP_SEND_FORM_ATTRIBUTE);
      boolean otpExpired = isOtpSessionExpired(context, ttl);

      if("true".equals(isSendOtp)) {
        return true;
      }

      return "true".equals(isSendOtp) && (existingOtp == null || otpExpired || "true".equals(isResendOtp));
  }
}
