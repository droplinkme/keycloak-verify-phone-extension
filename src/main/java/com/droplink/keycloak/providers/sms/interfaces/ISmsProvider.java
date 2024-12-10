/*
 * Extension validate phone number.
 * @author Allan Vieira (allancnfx.vieira@gmail.com)
 * @version 1.0
 */
package com.droplink.keycloak.providers.sms.interfaces;

public interface ISmsProvider {
  public void sendOtp(String template, String phoneNumber,boolean isSimulationMode);
}
