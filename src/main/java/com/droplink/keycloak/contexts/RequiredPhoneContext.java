/*
 * Extension validate phone number.
 * @author Allan Vieira (allancnfx.vieira@gmail.com)
 * @version 1.0
 */
package com.droplink.keycloak.contexts;

import org.keycloak.authentication.RequiredActionContext;
import org.keycloak.forms.login.LoginFormsProvider;
import org.keycloak.forms.login.MessageType;
import org.keycloak.models.UserModel;
import org.keycloak.sessions.AuthenticationSessionModel;

import com.droplink.keycloak.constants.ExtensionConstants;
import com.droplink.keycloak.constants.ExtensionMessages;
import com.droplink.keycloak.helpers.OTPHelper;

import jakarta.ws.rs.core.Response;

public class RequiredPhoneContext {
  private LoginFormsProvider buildChallenge(RequiredActionContext context, boolean isValidatioForm) {
    return context.form()
        .setActionUri(context.getActionUrl())
        .setAttribute(ExtensionConstants.PAGE_LOCALE_ATTRIBUTE, context.getSession().getContext().resolveLocale(context.getUser()))
        .setAttribute(ExtensionConstants.PAGE_ACTION_URL_ATTRIBUTE, context.getActionUrl().toString())
        .setAttribute(ExtensionConstants.PAGE_RESEND_OTP_URL_ATTRIBUTE, getResendOtpUrl(context))
        .setAttribute(ExtensionConstants.PAGE_SEND_OTP_URL_ATTRIBUTE, getResendOtpUrl(context))
        .setAttribute(ExtensionConstants.PAGE_CODE_LENGTH_ATTRIBUTE, getCodeLength(context))
        .setAttribute(ExtensionConstants.IS_OTP_VERIFICATION_FORM, isValidatioForm);
  }
  protected void createPageChallenge(RequiredActionContext context, String message, boolean isValidatioForm) {
    Response challenge = buildChallenge(context, isValidatioForm)
        .setError(message)
        .setMessage(MessageType.ERROR, message)
        .setAttribute("message", message)
        .setAttribute(ExtensionConstants.PHONE_USER_ATTRIBUTE, getUserPhoneNumberValue(context))
        .createForm(ExtensionConstants.PAGE_VERIFY_PHONE);
    context.challenge(challenge);
  }

  protected void createPageChallenge(RequiredActionContext context, boolean isValidatioForm) {
    Response challenge = buildChallenge(context, isValidatioForm)
        .setAttribute(ExtensionConstants.PHONE_USER_ATTRIBUTE, getUserPhoneNumberValue(context))
        .createForm(ExtensionConstants.PAGE_VERIFY_PHONE);
    context.challenge(challenge);
  }
  
  protected String getResendOtpUrl(RequiredActionContext context) {
    String baseUri = context.getUriInfo().getBaseUri().toString();
    String realmName = context.getRealm().getName();
    String clientId = context.getAuthenticationSession().getClient().getClientId();
    String tabId = context.getAuthenticationSession().getTabId();

    return String.format(
        "%srealms/%s/login-actions/required-action?execution=VERIFY_PHONE&client_id=%s&tab_id=%s",
        baseUri, realmName, clientId, tabId);
  }
    
  protected void createPageChallengeFailure(RequiredActionContext context, String message) {
    Response challenge = context.form()
        .setAttribute(ExtensionConstants.PAGE_LOCALE_ATTRIBUTE, context.getSession().getContext().resolveLocale(context.getUser()))
        .setError(message)
        .createForm(ExtensionConstants.PAGE_VERIFY_PHONE_ERROR);
    context.challenge(challenge);
    context.failure();
  }
  
  protected String getUserPhoneNumberValue(RequiredActionContext context) {
    return getUserPhoneNumberValue(context.getUser());
  }

  protected String getUserPhoneNumberValue(UserModel user) {
    return user.getFirstAttribute(ExtensionConstants.PHONE_USER_ATTRIBUTE);
  }

  protected String getFormValue(RequiredActionContext context, String key) {
    return context.getHttpRequest().getDecodedFormParameters().getFirst(key);
  }

  protected String getSessionAuthNoteValue(RequiredActionContext context, String key) {
    return context.getAuthenticationSession().getAuthNote(key);
  }

  protected boolean validatePhoneConfiguration(RequiredActionContext context) {
    String phoneNumber = getUserPhoneNumberValue(context);
    if (phoneNumber == null || phoneNumber.isEmpty()) {
      createPageChallengeFailure(context, ExtensionMessages.PHONE_NOT_CONFIGURED);
      return false;
    }
    return true;
  }
  
  protected boolean isOtpSessionExpired(RequiredActionContext context, long ttl) {
    String otpTimestamp = getSessionAuthNoteValue(context,
        ExtensionConstants.OTP_SESSION_TIMESTAMP_AUTH_NOTE_ATTRIBUTE);

    return OTPHelper.isOTPExprired(otpTimestamp, ttl);
  }
  
  protected int getCodeLength(RequiredActionContext context) {
    return Integer
        .parseInt(context.getConfig().getConfigValue(ExtensionConstants.CODE_LENGTH, "6"));
  }
  
  protected String generateAndStoreOtp(RequiredActionContext context) {
    String otp = OTPHelper.generateOTP(getCodeLength(context));
    long timestamp = OTPHelper.getTimestamp();

    AuthenticationSessionModel session = context.getAuthenticationSession();
    session.setAuthNote(ExtensionConstants.OTP_SESSION_AUTH_NOTE_ATTRIBUTE, otp);
    session.setAuthNote(ExtensionConstants.OTP_SESSION_TIMESTAMP_AUTH_NOTE_ATTRIBUTE, String.valueOf(timestamp));
    return otp;
  }
}
