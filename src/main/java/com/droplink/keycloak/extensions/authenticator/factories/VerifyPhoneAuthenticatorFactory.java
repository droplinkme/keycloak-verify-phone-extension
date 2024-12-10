  /*
  * Extension validate phone number.
  * @author Allan Vieira (allancnfx.vieira@gmail.com)
  * @version 1.0
  */
  package com.droplink.keycloak.extensions.authenticator.factories;

  import java.util.List;

  import org.keycloak.Config;
  import org.keycloak.authentication.Authenticator;
  import org.keycloak.authentication.AuthenticatorFactory;
  import org.keycloak.models.AuthenticationExecutionModel;
  import org.keycloak.models.KeycloakSession;
  import org.keycloak.models.KeycloakSessionFactory;
  import org.keycloak.provider.ProviderConfigProperty;

import com.droplink.keycloak.extensions.authenticator.implementations.VerifyPhoneAuthenticator;


  public class VerifyPhoneAuthenticatorFactory implements AuthenticatorFactory {

    public static final String ID = "verify-phone-authenticator";

    private static final VerifyPhoneAuthenticator SINGLETON = new VerifyPhoneAuthenticator();
      
    private static final AuthenticationExecutionModel.Requirement[] REQUIREMENT_CHOICES = {
      AuthenticationExecutionModel.Requirement.REQUIRED,
      AuthenticationExecutionModel.Requirement.DISABLED
    };

      @Override
      public Authenticator create(KeycloakSession session) {
          return SINGLETON;
      }

      @Override
      public String getId() {
          return ID;
      }

      @Override
      public String getDisplayType() {
          return "Verify Phone";
      }

      @Override
      public boolean isConfigurable() {
          return true;
      }

      @Override
      public boolean isUserSetupAllowed() {
          return true;
      }

      @Override
      public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        System.out.println("getRequirementChoices() called");
        System.out.println("getRequirementChoices() called");
        System.out.println("getRequirementChoices() called");
        System.out.println("getRequirementChoices() called");
        System.out.println("getRequirementChoices() called");
        System.out.println("getRequirementChoices() called");
        System.out.println("getRequirementChoices() called");
        System.out.println("getRequirementChoices() called");
        System.out.println("getRequirementChoices() called");
        System.out.println("getRequirementChoices() called");
        System.out.println("getRequirementChoices() called");
        System.out.println("getRequirementChoices() called");
          return REQUIREMENT_CHOICES;
      }

      @Override
      public void init(Config.Scope config) {}

      @Override
      public void postInit(KeycloakSessionFactory factory) {}

      @Override
      public void close() {}

      @Override
      public String getReferenceCategory() {
        return "phone";
      }

      @Override
      public String getHelpText() {
          return "This authenticator verifies the user's phone number is already taken.";
      }

      @Override
      public List<ProviderConfigProperty> getConfigProperties() {
          return List.of();
      }
  }
