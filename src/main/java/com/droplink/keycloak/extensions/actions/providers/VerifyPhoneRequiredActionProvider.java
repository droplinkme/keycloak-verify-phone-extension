/*
 * Extension validate phone number.
 * @author Allan Vieira (allancnfx.vieira@gmail.com)
 * @version 1.0
 */
package com.droplink.keycloak.extensions.actions.providers;

import org.keycloak.authentication.RequiredActionContext;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.models.KeycloakSession;

import com.droplink.keycloak.providers.sms.implementations.twilio.TwilioSmsProvider;
import com.droplink.keycloak.services.actions.RequiredActionChallengeService;
import com.droplink.keycloak.services.validations.RequiredCheckPhoneVerifiedService;

public class VerifyPhoneRequiredActionProvider implements RequiredActionProvider {
    private static final RequiredCheckPhoneVerifiedService SINGLETON_CHECK_PHONE_VERIFIED_SERVICE = new RequiredCheckPhoneVerifiedService();
    private static final RequiredActionChallengeService SINGLETON_REQUIRED_ACTION_CHALLENGE_SERVICE = new RequiredActionChallengeService(new TwilioSmsProvider());

    public VerifyPhoneRequiredActionProvider(KeycloakSession session){
    }
    
    @Override
    public void evaluateTriggers(RequiredActionContext context) {
        SINGLETON_CHECK_PHONE_VERIFIED_SERVICE.exec(context);
    }

    @Override
    public void requiredActionChallenge(RequiredActionContext context) {
      SINGLETON_REQUIRED_ACTION_CHALLENGE_SERVICE.exec(context);
    }

    @Override
    public void processAction(RequiredActionContext context) {
      // SINGLETON_REQUIRED_PROCESS_ACTION_SERVICE.exec(context);
    }

    @Override
    public void close() {
    }
    

}
