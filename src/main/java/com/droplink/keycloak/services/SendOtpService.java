package com.droplink.keycloak.services;

import org.keycloak.authentication.RequiredActionContext;

import com.droplink.keycloak.constants.ExtensionMessages;
import com.droplink.keycloak.helpers.ConfigHelper;
import com.droplink.keycloak.helpers.FormHelper;
import com.droplink.keycloak.helpers.OtpHelper;
import com.droplink.keycloak.helpers.PageChallengeHelper;
import com.droplink.keycloak.interfaces.IExecutable;
import com.droplink.keycloak.providers.sms.interfaces.ISmsProvider;
import com.droplink.keycloak.services.validations.ShouldSendOTPService;



public class SendOtpService implements IExecutable {
  private final ISmsProvider smsProvider;

  public SendOtpService(ISmsProvider smsProvider) {
    this.smsProvider = smsProvider;
  }
  @Override
  public void exec(RequiredActionContext context) {
    boolean shouldSendOtp = ShouldSendOTPService.exec(context);

    if (!shouldSendOtp) {
      return;
    }

    generateAndSendOtp(context);

    PageChallengeHelper.createPageChallengeSendOTP(context, null);
  }
    
  private void generateAndSendOtp(RequiredActionContext context) {
    String otp = OtpHelper.generateOTP(context);
    OtpHelper.storeSessionOTP(context, otp);

    String phoneNumber = FormHelper.getPhoneNumber(context);
    String template = ConfigHelper.getOtpTemplateSms(context).replace("{code}", otp);

    boolean isSimulationMode = ConfigHelper.getOtpSimuliationMode(context);
    String message = template.replace("{code}", otp);

    try {
      smsProvider.sendOtp(message, phoneNumber, isSimulationMode);
    } catch (Exception e) {
      context.getEvent().error("sms_send_failure");
      PageChallengeHelper.createPageChallengeSendOTP(context, ExtensionMessages.SMS_SEND_FAILED);
    }
  }
}
