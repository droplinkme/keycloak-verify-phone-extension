/*
 * Extension validate phone number.
 * @author Allan Vieira (allancnfx.vieira@gmail.com)
 * @version 1.0
 */
package com.droplink.keycloak.services.actions;

import java.util.HashMap;

import org.keycloak.authentication.RequiredActionContext;

import com.droplink.keycloak.helpers.FormHelper;
import com.droplink.keycloak.helpers.PageChallengeHelper;
import com.droplink.keycloak.interfaces.IExecutable;
import com.droplink.keycloak.providers.sms.interfaces.ISmsProvider;
import com.droplink.keycloak.services.ReceiveOtpService;
import com.droplink.keycloak.services.SendOtpService;

public class RequiredActionChallengeService {
  
  private final HashMap<String, Object> classes = new HashMap<>();

  public RequiredActionChallengeService(ISmsProvider smsProvider) {
    classes.put("sendOTP", new SendOtpService(smsProvider));
    classes.put("recevieOTP", new ReceiveOtpService());
  }
  
  public void exec(RequiredActionContext context) {
    String origin = FormHelper.getValue(context, "origin").toLowerCase();
    
    Object obj = classes.get(origin);
    
    if (obj == null) {
      PageChallengeHelper.createPageChallengeSendOTP(context, null);
    }

    ((IExecutable) classes.get(origin)).exec(context);
  }

}
