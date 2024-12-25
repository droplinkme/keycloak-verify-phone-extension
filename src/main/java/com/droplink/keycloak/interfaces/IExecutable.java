package com.droplink.keycloak.interfaces;

import org.keycloak.authentication.RequiredActionContext;

public interface IExecutable {
  public void exec(RequiredActionContext context);
}
