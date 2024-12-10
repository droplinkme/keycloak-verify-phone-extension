package com.droplink.keycloak.extensions.forms.implementations;

import org.keycloak.authentication.FormAction;
import org.keycloak.authentication.FormContext;
import org.keycloak.authentication.ValidationContext;
import org.keycloak.forms.login.LoginFormsProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.services.validation.Validation;

import com.droplink.keycloak.constants.ExtensionConstants;

import jakarta.ws.rs.core.MultivaluedMap;

public class PhoneNumberValidationAction implements FormAction {

    @Override
    public void validate(ValidationContext context) {
        MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
        String phoneNumber = formData.getFirst(ExtensionConstants.PHONE_USER_ATTRIBUTE);

        System.out.println(" ");
        System.out.println("phoneNumber: " + phoneNumber);
        System.out.println(" ");

        if (!Validation.isBlank(phoneNumber)) {
            boolean phoneTaken = isPhoneNumberTaken(context, phoneNumber);
            System.out.println(" ");
            System.out.println("phoneTaken: " + phoneTaken);
            System.out.println(" ");
            if (phoneTaken) {
                context.error("phoneNumberExists");
                context.validationError(formData, null);
                return;
            }
        }

        context.success();
    }

    private boolean isPhoneNumberTaken(ValidationContext context, String phoneNumber) {
        return context.getSession().users()
                .searchForUserByUserAttributeStream(context.getRealm(), ExtensionConstants.PHONE_USER_ATTRIBUTE, phoneNumber)
                .findAny()
                .isPresent();
    }

    @Override
    public void success(FormContext context) {
        MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
        String phoneNumber = formData.getFirst(ExtensionConstants.PHONE_USER_ATTRIBUTE);

        if (!Validation.isBlank(phoneNumber)) {
            UserModel user = context.getUser();
            user.setSingleAttribute(ExtensionConstants.PHONE_USER_ATTRIBUTE, phoneNumber);
        }
    }

    @Override
    public boolean requiresUser() {
        return false;
    }

    @Override
    public void close() {
        // Cleanup, se necessário.
    }

    @Override
    public void buildPage(FormContext context, LoginFormsProvider form) {
      // TODO Auto-generated method stub
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
      // TODO Auto-generated method stub
      return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
      // TODO Auto-generated method stub
    }
}