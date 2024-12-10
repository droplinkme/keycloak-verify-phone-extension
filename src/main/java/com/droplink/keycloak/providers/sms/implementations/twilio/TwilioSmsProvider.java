/*
 * Extension validate phone number.
 * @author Allan Vieira (allancnfx.vieira@gmail.com)
 * @version 1.0
 */
package com.droplink.keycloak.providers.sms.implementations.twilio;

import com.droplink.keycloak.providers.sms.interfaces.ISmsProvider;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioSmsProvider implements ISmsProvider {
  public static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
  public static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
  public static final String FROM_PHONE = System.getenv("TWILIO_FROM_PHONE"); 

  public TwilioSmsProvider() {
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
  }

  @Override
  public void sendOtp(String template, String phoneNumber, boolean isSimulationMode) {
    
    if (isSimulationMode) {
      System.out.println(template);
      return;
    }

    if(ACCOUNT_SID == null || AUTH_TOKEN == null || FROM_PHONE == null) {
      System.out.println("Twilio credentials not found");
      throw new Error("Twilio credentials not found");
    }
    
    Message message = Message
      .creator(
        new PhoneNumber(phoneNumber),
        new PhoneNumber(FROM_PHONE),
        template
      )
        .create();
      
    System.out.println("SMS sent! Message SID: " + message.getSid());

  } 
}
