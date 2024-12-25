package com.droplink.keycloak.constants;

public final class Constants {

    private Constants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static final class Session {
        public static final String OTP = "OTP";
        public static final String OTP_TIMESTAMP = "OTP_TIMESTAMP";

        private Session() {}
    }

    public static final class Form {
        public static final String PHONE_NUMBER = "PHONE_NUMBER";

        public static final String PHONE_OTP_CODE_SEND = "PHONE_OTP_CODE_SEND";
        public static final String PHONE_OTP_SEND = "PHONE_OTP_SEND";
        public static final String PHONE_OTP_RESEND = "PHONE_OTP_RESEND";
        public static final String OTP = "OTP";
        public static final String IS_OTP_VERIFICATION_FORM = "IS_OTP_VERIFICATION_FORM";

        public static final String PAGE_LOCALE_ATTRIBUTE = "LOCALE";
        public static final String PAGE_ACTION_URL_ATTRIBUTE = "ACTION_URL";
        public static final String PAGE_RESEND_OTP_URL_ATTRIBUTE = "RESEND_OTP_URL";
        public static final String PAGE_SEND_OTP_URL_ATTRIBUTE = "SEND_OTP_URL";
        public static final String PAGE_CODE_LENGTH_ATTRIBUTE = "CODE_LENGTH";

        private Form() {}
    }

    public static final class Attributes {
        public static final String PHONE_USER_ATTRIBUTE = "phoneNumber";
        public static final String PHONE_VERIFIED_USER_ATTRIBUTE = "phoneVerified";

        private Attributes() {}
    }

    public static final class Pages {
        public static final String PAGE_VERIFY_PHONE_ERROR = "verify-phone-error.ftl";
        public static final String PAGE_VERIFY_PHONE_OTP = "verify-phone-otp.ftl";
        public static final String PAGE_VERIFY_PHONE = "verify-phone.ftl";

        private Pages() {}
    }

    public static final class Config {

        public static final class Twilio {
            public static final String ACCOUNT_SID = "TWILIO_ACCOUNT_SID";
            public static final String AUTH_TOKEN = "TWILIO_AUTH_TOKEN";
            public static final String FROM_PHONE = "TWILIO_FROM_PHONE";
            public static final String SIMULATION_MODE = "SIMULATION_MODE";

            private Twilio() {}
        }

        public static final class Code {
            public static final String LENGTH = "CODE_LENGTH";
            public static final String TTL = "CODE_TTL";
            public static final String TEMPLATE_SMS = "SMS_TEMPLATE";

            private Code() {}
        }

        private Config() {}
    }
}
