/*
 * Extension validate phone number.
 * @author Allan Vieira (allancnfx.vieira@gmail.com)
 * @version 1.0
 */
package com.droplink.keycloak.services;

import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.models.RealmModel;

public class CreatePhoneNumberAttribute {
  public void exec(KeycloakSessionFactory factory) {
    try (KeycloakSession session = factory.create()) {
        session.getTransactionManager().begin();

        session.realms().getRealmsStream().forEach(realm -> {
          createPhoneNumberAttribute(realm);
        });

        session.getTransactionManager().commit();
    } catch (Exception e) {
        System.err.println("Failed to add phoneNumber attribute to realms: " + e.getMessage());
        e.printStackTrace();
    }
  }
  
  private void createPhoneNumberAttribute(RealmModel realm) {
    if (realm.getAttributes().containsKey("phoneNumber")) {
        System.out.println("phoneNumber attribute already exists for realm: " + realm.getName());
        return;
    }
    
    realm.setAttribute("phoneNumber", "Custom attribute for storing user phone numbers.");
    System.out.println("Added phoneNumber attribute to realm: " + realm.getName());
}
}
