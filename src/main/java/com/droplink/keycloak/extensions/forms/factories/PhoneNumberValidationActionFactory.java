package com.droplink.keycloak.extensions.forms.factories;

import java.util.List;

import org.keycloak.Config;
import org.keycloak.authentication.FormActionFactory;
import org.keycloak.models.AuthenticationExecutionModel.Requirement;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import com.droplink.keycloak.extensions.forms.implementations.PhoneNumberValidationAction;


public class PhoneNumberValidationActionFactory implements FormActionFactory {

    public static final String PROVIDER_ID = "phone-number-validation-action";

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public PhoneNumberValidationAction create(KeycloakSession session) {
        return new PhoneNumberValidationAction();
    }

    @Override
    public void init(Config.Scope config) {
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
    }

    @Override
    public void close() {
    }

    @Override
    public String getDisplayType() {
        return "Phone Number Validation";
    }

    @Override
    public boolean isConfigurable() {
        return false;
    }

    @Override
    public boolean isUserSetupAllowed() {
        return false;
    }

    @Override
    public String getReferenceCategory() {
      return "phone";
    }

    @Override
    public Requirement[] getRequirementChoices() {
      return null;
    }

    @Override
    public String getHelpText() {
      return "This authenticator verifies if phone already taken.";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return List.of();
    }
}