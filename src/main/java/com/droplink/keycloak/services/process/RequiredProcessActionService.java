/*
 * Extension validate phone number.
 * @author Allan Vieira (allancnfx.vieira@gmail.com)
 * @version 1.0
 */
package com.droplink.keycloak.services.process;

import org.keycloak.authentication.RequiredActionContext;

import com.droplink.keycloak.constants.ExtensionConstants;
import com.droplink.keycloak.constants.ExtensionMessages;
import com.droplink.keycloak.contexts.RequiredPhoneContext;

public class RequiredProcessActionService extends RequiredPhoneContext {
  public void exec(RequiredActionContext context) {
    String enteredOtp = getFormValue(context, ExtensionConstants.OTP_FORM_ATTRIBUTE);
    String sessionOtp = getSessionAuthNoteValue(context, ExtensionConstants.OTP_SESSION_AUTH_NOTE_ATTRIBUTE);


    if (sessionOtp != null && sessionOtp.equals(enteredOtp)) {
      long ttl = Long.parseLong(context.getConfig().getConfigValue(ExtensionConstants.CODE_TTL, "3600"));

      if (isOtpSessionExpired(context, ttl)) {
        createPageChallenge(context, ExtensionMessages.EXPIRED_OTP, true);
        return;
      }

      context.getUser().setSingleAttribute(ExtensionConstants.PHONE_VERIFIED_USER_ATTRIBUTE, "true");
      context.success();
    } else {
      createPageChallenge(context, ExtensionMessages.INVALID_OTP, true);
    }
  }

}
