package com.droplink.keycloak.helpers;

import org.keycloak.authentication.RequiredActionContext;

import com.droplink.keycloak.constants.Constants;

public final class FormHelper {
  private FormHelper() {}

  public static String getValue(RequiredActionContext context, String key) {
    return context.getHttpRequest().getDecodedFormParameters().getFirst(key);
  }
  
  public static String getPhoneNumber(RequiredActionContext context) {
    return getValue(context, Constants.Form.PHONE_NUMBER);
  }

  public static String getOTPCode(RequiredActionContext context) {
    return getValue(context, Constants.Form.OTP);
  }
}
