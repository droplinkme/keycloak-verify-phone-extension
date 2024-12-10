/*
 * Extension validate phone number.
 * @author Allan Vieira (allancnfx.vieira@gmail.com)
 * @version 1.0
 */
package com.droplink.keycloak.extensions.provider.spi;

import org.keycloak.provider.Provider;
import org.keycloak.provider.ProviderFactory;
import org.keycloak.provider.Spi;

import com.droplink.keycloak.extensions.provider.config.PhoneRealmConfigurationProvider;
import com.droplink.keycloak.extensions.provider.factories.PhoneRealmConfigurationProviderFactory;



public class ConfigServiceSpi implements Spi {
    @Override
    public boolean isInternal() {
        return false;
    }

    @Override
    public String getName() {
        return "phone-settings";
    }

    @Override
    public Class<? extends Provider> getProviderClass() {
        return PhoneRealmConfigurationProvider.class;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Class<? extends ProviderFactory> getProviderFactoryClass() {
        return PhoneRealmConfigurationProviderFactory.class;
    }
}
