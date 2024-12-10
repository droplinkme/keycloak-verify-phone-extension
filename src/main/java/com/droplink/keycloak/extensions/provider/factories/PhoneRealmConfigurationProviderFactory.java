/*
 * Extension validate phone number.
 * @author Allan Vieira (allancnfx.vieira@gmail.com)
 * @version 1.0
 */
package com.droplink.keycloak.extensions.provider.factories;

import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderFactory;

import com.droplink.keycloak.extensions.provider.config.PhoneRealmConfigurationProvider;
import com.droplink.keycloak.services.CreatePhoneNumberAttribute;

public class PhoneRealmConfigurationProviderFactory implements ProviderFactory<PhoneRealmConfigurationProvider> {
  public static final String ID = "phone-realm-configuration";
    private static final CreatePhoneNumberAttribute CREATE_PHONE_NUMBER_ATTRIBUTE_SINGLETON = new CreatePhoneNumberAttribute();

    @Override
    public PhoneRealmConfigurationProvider create(KeycloakSession session) {
        return new PhoneRealmConfigurationProvider(session);
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void init(Config.Scope config) {
      System.out.println("Initializing PhoneRealmConfigurationProvider");
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
      CREATE_PHONE_NUMBER_ATTRIBUTE_SINGLETON.exec(factory);
    }

    @Override
    public void close() {
    }
}
