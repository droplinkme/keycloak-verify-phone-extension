package com.droplink.keycloak.helpers;

import org.keycloak.authentication.RequiredActionContext;

public final class SessionHelper {
  private SessionHelper() {}
  public static String getValue(RequiredActionContext context, String key) {
    return context.getAuthenticationSession().getAuthNote(key);
  }

  public static void setValue(RequiredActionContext context, String key, String value) {
    context.getAuthenticationSession().setAuthNote(key, value);
  }
}
