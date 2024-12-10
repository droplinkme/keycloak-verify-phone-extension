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
import com.droplink.keycloak.services.process.RequiredProcessActionService;
import com.droplink.keycloak.services.validations.RequiredCheckPhoneVerifiedService;

public class VerifyPhoneRequiredActionProvider implements RequiredActionProvider {
    private static final RequiredCheckPhoneVerifiedService CHECK_PHONE_VERIFIED_SERVICE_SINGLETON = new RequiredCheckPhoneVerifiedService();
    private static final RequiredActionChallengeService REQUIRED_ACTION_CHALLENGE_SERVICE_SINGLETON = new RequiredActionChallengeService(new TwilioSmsProvider());
    private static final RequiredProcessActionService REQUIRED_PROCESS_ACTION_SERVICE_SINGLETON = new RequiredProcessActionService();

    public VerifyPhoneRequiredActionProvider(KeycloakSession session){
    }
    
    @Override
    public void evaluateTriggers(RequiredActionContext context) {
        CHECK_PHONE_VERIFIED_SERVICE_SINGLETON.exec(context);
    }

    @Override
    public void requiredActionChallenge(RequiredActionContext context) {
      REQUIRED_ACTION_CHALLENGE_SERVICE_SINGLETON.exec(context);
    }

    @Override
    public void processAction(RequiredActionContext context) {
      REQUIRED_PROCESS_ACTION_SERVICE_SINGLETON.exec(context);
    }

    @Override
    public void close() {
    }
    

}
