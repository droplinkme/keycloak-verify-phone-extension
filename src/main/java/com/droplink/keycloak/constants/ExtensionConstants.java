/*
 * Extension validate phone number.
 * @author Allan Vieira (allancnfx.vieira@gmail.com)
 * @version 1.0
 */
package com.droplink.keycloak.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExtensionConstants {
  public String PHONE_USER_ATTRIBUTE = "phoneNumber";
  public String PHONE_VERIFIED_USER_ATTRIBUTE = "phoneVerified";

  
  public String OTP_SESSION_AUTH_NOTE_ATTRIBUTE = "PHONE_OTP";
  public String OTP_SESSION_TIMESTAMP_AUTH_NOTE_ATTRIBUTE = "PHONE_OTP_TIMESTAMP";
  
  
  public String OTP_CODE_SEND_FORM_ATTRIBUTE = "PHONE_OTP_CODE_SEND";
  public String OTP_SEND_FORM_ATTRIBUTE = "PHONE_OTP_SEND";
  public String OTP_RESEND_FORM_ATTRIBUTE = "PHONE_OTP_RESEND";
  public String OTP_FORM_ATTRIBUTE = "OTP";
  public String IS_OTP_VERIFICATION_FORM = "IS_OTP_VERIFICATION_FORM";

  
  public String PAGE_VERIFY_PHONE_ERROR = "verify-phone-error.ftl";
  public String PAGE_VERIFY_PHONE = "verify-phone.ftl";


  public String PAGE_LOCALE_ATTRIBUTE = "LOCALE";
  public String PAGE_ACTION_URL_ATTRIBUTE = "ACTION_URL";
  public String PAGE_RESEND_OTP_URL_ATTRIBUTE = "RESEND_OTP_URL";
  public String PAGE_SEND_OTP_URL_ATTRIBUTE = "SEND_OTP_URL";
  public String PAGE_CODE_LENGTH_ATTRIBUTE = "CODE_LENGTH";



  public String CODE_LENGTH = "CODE_LENGTH";
  public String CODE_TTL = "CODE_TTL";
  public String CODE_TEMPLATE_SMS = "SMS_TEMPLATE";
  public String CODE_SIMULATION_MODE = "SIMULATION_MODE";

}
