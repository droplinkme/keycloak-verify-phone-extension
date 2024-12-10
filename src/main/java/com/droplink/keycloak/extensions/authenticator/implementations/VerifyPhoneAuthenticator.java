/*
 * Extension validate phone number.
 * @author Allan Vieira (allancnfx.vieira@gmail.com)
 * @version 1.0
 */
package com.droplink.keycloak.extensions.authenticator.implementations;


import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.forms.login.freemarker.model.RegisterBean;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import com.droplink.keycloak.constants.ExtensionConstants;

import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;

public class VerifyPhoneAuthenticator implements Authenticator {
    private boolean isPhoneNumberTaken(AuthenticationFlowContext context, String phoneNumber) {
        return context.getSession().users()
                .searchForUserByUserAttributeStream(context.getRealm(), ExtensionConstants.PHONE_USER_ATTRIBUTE, phoneNumber)
                .findAny()
                .isPresent();
    }
    @Override
    public void authenticate(AuthenticationFlowContext context) {
        MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
        String phoneNumber = formData.getFirst(ExtensionConstants.PHONE_USER_ATTRIBUTE);

        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            RegisterBean rb = new RegisterBean(formData, context.getSession());
            Response challenge = context.form()
                .setError("Phone number is required.")
                .setAttribute("register", rb)
                .setAttribute("profile", rb)
                .createForm("register.ftl");
            context.failureChallenge(AuthenticationFlowError.INVALID_USER, challenge); // Finaliza o fluxo como falha
            return;
        }

        boolean phoneTaken = isPhoneNumberTaken(context, phoneNumber);
        if (phoneTaken) {
            RegisterBean rb = new RegisterBean(formData, context.getSession());
            Response challenge = context.form()
                .setError("Phone number is already taken.")
                .setAttribute("register", rb)
                .setAttribute("profile", rb)
                .createForm("register.ftl");
            context.failureChallenge(AuthenticationFlowError.INVALID_USER, challenge); // Finaliza o fluxo como falha
            return;
        }

        context.success();
    }



    @Override
    public void action(AuthenticationFlowContext context) {
    }

    @Override
    public boolean requiresUser() {
        return true;
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        String phoneNumber = user.getFirstAttribute(ExtensionConstants.PHONE_USER_ATTRIBUTE);
        return phoneNumber != null && !phoneNumber.isEmpty();
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {}

    @Override
    public void close() {
    }
    
}
