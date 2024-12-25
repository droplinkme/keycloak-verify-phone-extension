package com.droplink.keycloak.helpers;

import org.keycloak.authentication.RequiredActionContext;

import com.droplink.keycloak.constants.Constants;

public final class ConfigHelper {

  private ConfigHelper() {}
  public static int getOtpCodeLength(RequiredActionContext context) {
    return getValue(context, Constants.Config.Code.LENGTH, "6");
  }

  public static int getOtpCodeTtl(RequiredActionContext context) {
    return getValue(context, Constants.Config.Code.TTL, "3600");
  }

  public static String getOtpTemplateSms(RequiredActionContext context) {
    return getValue(context, Constants.Config.Code.TEMPLATE_SMS, "Your verification code is: {code}");
  }

  public static Boolean getOtpSimuliationMode(RequiredActionContext context) {
    return getValue(context, Constants.Config.Twilio.SIMULATION_MODE, "true");
  }
    
  @SuppressWarnings("unchecked")
  public static <TOutput> TOutput getValue(RequiredActionContext context, String key, String defaultValue) {
    String value = context.getConfig().getConfigValue(key, defaultValue);
      
    return (TOutput) value;
  }
}
