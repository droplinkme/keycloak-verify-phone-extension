package com.droplink.keycloak.services.validations;

import org.keycloak.authentication.RequiredActionContext;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RequiredActionProviderModel;
import org.keycloak.models.UserModel;

import com.droplink.keycloak.extensions.actions.factories.VerifyPhoneRequiredActionFactory;

public class RequiredCheckPhoneVerifiedService {
  public void exec(RequiredActionContext context) {
    UserModel user = context.getUser();
    RealmModel realm = context.getRealm();

    RequiredActionProviderModel requiredActionProvider = realm.getRequiredActionProviderByAlias(VerifyPhoneRequiredActionFactory.ACTION_ID);
    boolean enabled = requiredActionProvider != null && requiredActionProvider.isEnabled();
    boolean defaultAction = requiredActionProvider != null && requiredActionProvider.isDefaultAction();
    String isResendOtp = context.getHttpRequest().getDecodedFormParameters().getFirst("resendOtp");

    if (enabled && (defaultAction || "true".equals(isResendOtp))) {
      if ("true".equals(user.getFirstAttribute("phoneVerified"))) {
        return;
      }

      context.getUser().addRequiredAction(VerifyPhoneRequiredActionFactory.ACTION_ID);
      return;
    }
    
    System.out.println("Phone verification not enabled for realm: " + realm.getName());
  }
}
