/*
 * Extension validate phone number.
 * @author Allan Vieira (allancnfx.vieira@gmail.com)
 * @version 1.0
 */
package com.droplink.keycloak.extensions.provider.models;

import org.keycloak.Config;
import org.keycloak.models.RealmModel;

public class PhoneRealmSettings {
      private static final String PHONE_VERIFICATION_ENABLED = "phoneVerificationEnabled";
    private static final String DUPLICATE_PHONE_NUMBERS = "duplicatePhoneNumbers";

    private final RealmModel realm;

    public PhoneRealmSettings(RealmModel realm) {
        this.realm = realm;
    }

    /**
     * Obtém se a verificação de telefone está ativada.
     * 
     * @return true se ativada, false caso contrário.
     */
    public boolean isPhoneVerificationEnabled() {
        String value = realm.getAttribute(PHONE_VERIFICATION_ENABLED);
        return Boolean.parseBoolean(value != null ? value : "false");
    }

    /**
     * Define se a verificação de telefone deve ser ativada.
     * 
     * @param enabled true para ativar, false para desativar.
     */
    public void setPhoneVerificationEnabled(boolean enabled) {
        realm.setAttribute(PHONE_VERIFICATION_ENABLED, Boolean.toString(enabled));
    }

    /**
     * Obtém se números de telefone duplicados são permitidos.
     * 
     * @return true se permitido, false caso contrário.
     */
    public boolean isDuplicatePhoneNumbersAllowed() {
        String value = realm.getAttribute(DUPLICATE_PHONE_NUMBERS);
        return Boolean.parseBoolean(value != null ? value : "false");
    }

    /**
     * Define se números de telefone duplicados devem ser permitidos.
     * 
     * @param allowed true para permitir, false para não permitir.
     */
    public void setDuplicatePhoneNumbersAllowed(boolean allowed) {
        realm.setAttribute(DUPLICATE_PHONE_NUMBERS, Boolean.toString(allowed));
    }

    /**
     * Inicializa configurações padrão para o Realm.
     * 
     * @param config Configurações padrão do Keycloak.
     */
    public void initializeDefaultSettings(Config.Scope config) {
        if (realm.getAttribute(PHONE_VERIFICATION_ENABLED) == null) {
            setPhoneVerificationEnabled(false); // Configuração padrão
        }
        if (realm.getAttribute(DUPLICATE_PHONE_NUMBERS) == null) {
            setDuplicatePhoneNumbersAllowed(false); // Configuração padrão
        }
    }
}
