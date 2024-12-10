/*
 * Extension validate phone number.
 * @author Allan Vieira (allancnfx.vieira@gmail.com)
 * @version 1.0
 */
package com.droplink.keycloak.extensions.actions.factories;

import java.util.List;

import org.keycloak.Config;
import org.keycloak.authentication.RequiredActionFactory;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import com.droplink.keycloak.constants.ExtensionConstants;
import com.droplink.keycloak.extensions.actions.providers.VerifyPhoneRequiredActionProvider;

public class VerifyPhoneRequiredActionFactory implements RequiredActionFactory {
    public static String ACTION_ID = "VERIFY_PHONE";

    @Override
    public String getDisplayText() {
      return "Verify Phone";
    }

    @Override
    public RequiredActionProvider create(KeycloakSession session) {
        return new VerifyPhoneRequiredActionProvider(session);
    }

    @Override
    public void init(Config.Scope config) {}

    @Override
    public void postInit(KeycloakSessionFactory factory) {}

    @Override
    public void close() {}

    @Override
    public String getId() {
      return ACTION_ID;
    }
    
    @Override
    public List<ProviderConfigProperty> getConfigMetadata() {
      return List.of(
        new ProviderConfigProperty(ExtensionConstants.CODE_LENGTH, "Code length", "The number of digits of the generated code.", ProviderConfigProperty.STRING_TYPE, 6),
        new ProviderConfigProperty(ExtensionConstants.CODE_TTL, "Time-to-live", "The time to live in seconds for the code to be valid.", ProviderConfigProperty.STRING_TYPE, "3600"),
        new ProviderConfigProperty(ExtensionConstants.CODE_TEMPLATE_SMS, "Template Sms", "The template used to send the SMS.", ProviderConfigProperty.STRING_TYPE, "Your verification code is: {code}"),
        new ProviderConfigProperty(ExtensionConstants.CODE_SIMULATION_MODE, "Simulation mode", "In simulation mode, the SMS won't be sent, but printed to the server logs", ProviderConfigProperty.BOOLEAN_TYPE, true)
      );
    }
}
