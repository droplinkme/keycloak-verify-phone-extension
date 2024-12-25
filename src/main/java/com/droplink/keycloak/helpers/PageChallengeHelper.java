package com.droplink.keycloak.helpers;

import org.keycloak.authentication.RequiredActionContext;
import org.keycloak.forms.login.LoginFormsProvider;

import com.droplink.keycloak.constants.Constants;

import jakarta.ws.rs.core.Response;

public final class PageChallengeHelper {
  private PageChallengeHelper() {}
  private static LoginFormsProvider buildBaseFormAttributes(RequiredActionContext context) {
    return context.form()
        .setActionUri(context.getActionUrl())
        .setAttribute(Constants.Form.PAGE_ACTION_URL_ATTRIBUTE, context.getActionUrl().toString())
        .setAttribute(Constants.Form.PAGE_LOCALE_ATTRIBUTE, context.getSession().getContext().resolveLocale(context.getUser()));
  }
  
  public static final void createPageChallengeSendOTP(RequiredActionContext context, String error) {
    Response challenge = buildBaseFormAttributes(context)
        .setError(error)
        .setAttribute(Constants.Form.PAGE_ACTION_URL_ATTRIBUTE, context.getActionUrl().toString())
        .createForm(Constants.Pages.PAGE_VERIFY_PHONE);
    context.challenge(challenge);
  }

  public static final void createPageChallengeReceiveOTP(RequiredActionContext context, String phoneNumber, String error) {
    Response challenge = buildBaseFormAttributes(context)
        .setError(error)
        .setAttribute(Constants.Form.PAGE_RESEND_OTP_URL_ATTRIBUTE, getResendOtpUrl(context))
        .setAttribute(Constants.Attributes.PHONE_USER_ATTRIBUTE, phoneNumber)
        // .setAttribute(Constants.Form.PAGE_SEND_OTP_URL_ATTRIBUTE, getResendOtpUrl(context))
        .setAttribute(Constants.Form.PAGE_CODE_LENGTH_ATTRIBUTE, ConfigHelper.getOtpCodeLength(context))
        .createForm(Constants.Pages.PAGE_VERIFY_PHONE_OTP);
    context.challenge(challenge);
  }
  
  public static final void createPageChallengeFailure(RequiredActionContext context, String message) {
    Response challenge = context.form()
        .setAttribute(Constants.Form.PAGE_LOCALE_ATTRIBUTE,
            context.getSession().getContext().resolveLocale(context.getUser()))
        .setError(message)
        .createForm(Constants.Pages.PAGE_VERIFY_PHONE_ERROR);
    context.challenge(challenge);
    context.failure();
  }
  
  private static String getResendOtpUrl(RequiredActionContext context) {
    String baseUri = context.getUriInfo().getBaseUri().toString();
    String realmName = context.getRealm().getName();
    String clientId = context.getAuthenticationSession().getClient().getClientId();
    String tabId = context.getAuthenticationSession().getTabId();

    return String.format(
        "%srealms/%s/login-actions/required-action?execution=VERIFY_PHONE&client_id=%s&tab_id=%s",
        baseUri, realmName, clientId, tabId);
  }
}
