/*
 * Extension validate phone number.
 * @author Allan Vieira (allancnfx.vieira@gmail.com)
 * @version 1.0
 */
package com.droplink.keycloak.extensions.provider.config;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.provider.Provider;
import org.keycloak.theme.Theme;

import com.droplink.keycloak.extensions.provider.models.PhoneRealmSettings;

public class PhoneRealmConfigurationProvider implements Provider {
    private final KeycloakSession session;

    public PhoneRealmConfigurationProvider(KeycloakSession session) {
        this.session = session;
    }

    public void addRealmSettings(Config.Scope config) {
      PhoneRealmSettings phoneSettings = new PhoneRealmSettings(session.getContext().getRealm());

      phoneSettings.initializeDefaultSettings(config);

      phoneSettings.setPhoneVerificationEnabled(true);

      boolean duplicatesAllowed = phoneSettings.isDuplicatePhoneNumbersAllowed();
      System.out.println("Duplicate phone numbers allowed: " + duplicatesAllowed);
    }

    public URL getPhoneSettingsTemplate() {
      try {
        Theme theme = session.theme().getTheme(Theme.Type.ADMIN);
        return theme.getTemplate("realm-settings/phone-settings.ftl");
      } catch (IOException e) {
        throw new RuntimeException("Failed to load phone-settings.ftl template", e);
      }
    }
    

  public Map<String, Object> getRealmSettings() {
    Map<String, Object> settings = new HashMap<>();
    settings.put("phoneVerificationEnabled", true);
    settings.put("allowDuplicateNumbers", false); 
    return settings;
  }


    @Override
    public void close() {}
    
}
