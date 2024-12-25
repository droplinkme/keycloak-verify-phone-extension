<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="${LOCALE!'en'}">
<head>
    <meta charset="UTF-8">
    <title>${msg("phone.TITLE", "Verify Your Phone Number")}</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 400px;
            margin: 0 auto;
            padding: 20px;
            background-color: #f9f9f9;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        h1 {
            color: #333;
            text-align: center;
        }

        p {
            color: #555;
            margin-bottom: 20px;
        }

        .kc-form-group {
            margin-bottom: 15px;
        }

        .kc-form-group label {
            display: block;
            margin-bottom: 5px;
            color: #333;
        }

        .kc-form-group input {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 14px;
        }

        .btn-primary {
            display: inline-block;
            width: 100%;
            padding: 10px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
        }

        .btn-primary:hover {
            background-color: #0056b3;
        }

        .error {
            margin-top: 15px;
            text-align: center;
            color: red;
            background-color: #ffe6e6;
            padding: 10px;
            border: 1px solid red;
            border-radius: 5px;
        }

        .success {
            margin-top: 15px;
            text-align: center;
            color: green;
            background-color: #e6ffe6;
            padding: 10px;
            border: 1px solid green;
            border-radius: 5px;
        }


        .btn-secondary {
                      display: inline-block;
            width: 100%;
            padding: 10px;
            background-color: #6c757d;
            color: #fff;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
        }

        .btn-secondary:hover {
            color: #fff;
            background-color: #5a6268;
            border-color: #545b62;
        }

        .btn-secondary:focus, 
        .btn-secondary.focus {
            color: #fff;
            background-color: #5a6268;
            border-color: #545b62;
            box-shadow: 0 0 0 0.2rem rgba(108, 117, 125, 0.5);
        }

        .btn-secondary:disabled, 
        .btn-secondary.disabled {
            color: #fff;
            background-color: #6c757d;
            border-color: #6c757d;
            cursor: not-allowed;
            opacity: 0.65;
        }

    </style>
    
    <script>
        let countdown = 30;
        let resendButton;

        function startCountdown() {
            resendButton = document.getElementById('resend-button');
            const timerElement = document.getElementById('timer');

            if(timerElement && resendButton){
              resendButton.disabled = true;

              const interval = setInterval(() => {
                  if (countdown > 0) {
                      countdown--;
                      timerElement.innerText = countdown;
                  } else {
                      clearInterval(interval);
                      resendButton.disabled = false;
                      timerElement.innerText = '';
                  }
              }, 1000);
              }

        }

        function resetCont(){
          countdown = 10; 
          startCountdown(); 
        }

        function submitResendForm() {
          document.getElementById('resend-code-form').submit();
          resetCont(); 
        }

        function submitSendForm() {
          console.log('Sending Form!');
          document.getElementById('send-code-form').submit();
          resetCont(); 
        }

        document.addEventListener("DOMContentLoaded", () => {
            startCountdown();
        });

        document.getElementById('resend-code-form')?.addEventListener('submit', () => {
            console.log('Success Resed Form!');
        });

        document.getElementById('send-code-form')?.addEventListener('submit', () => {
            console.log('Success Send Form!');
        });
    </script>
</head>
<body>
    <h1>${msg("phone.TITLE", "Verify Your Phone Number")}</h1>
  
    
    <#if IS_OTP_VERIFICATION_FORM?? && !IS_OTP_VERIFICATION_FORM>
      <p>${msg("phone.TITLE_FORM_SEND_CODE", "We will sent an code to your phone number:")} ${phoneNumber}</p>
      <div class="send-code-section">
          <form id="send-code-form" action="${SEND_OTP_URL}" method="post">
              <input type="hidden" name="PHONE_OTP_SEND" value="true">
              <button id="resend-button" type="button" onclick="submitSendForm()" class="btn btn-primary">
                  ${msg("phone.BTN_SEND_CODE", "Send Code")}
              </button>
          </form>
      </div>
    </#if>


    <#if IS_OTP_VERIFICATION_FORM>
      <p>${msg("phone.ENTER_CODE_MESSAGE", "Enter the code sent to your phone number:")} ${phoneNumber}</p>
      <form id="kc-verify-phone-form" action="${ACTION_URL}" method="post">
          <div class="kc-form-group">
              <label for="otp">${msg("phone.ENTER_OTP", "Enter the OTP sent to your phone")}</label>
              <input 
                type="text" 
                id="otp" 
                name="OTP" 
                required 
                placeholder="${msg("phone.OTP_PLACEHOLDER", CODE_LENGTH ,CODE_LENGTH + "-digit code")}"
                maxlength="${CODE_LENGTH}"
              >
          </div>
          <div class="kc-form-group">
              <button type="submit" class="btn btn-primary">${msg("phone.BTN_VERIFY_CODE", "Verify")}</button>
          </div>
      </form>

      <div class="resend-code-section">
          <form id="resend-code-form" action="${RESEND_OTP_URL}" method="post">
              <input type="hidden" name="PHONE_OTP_RESEND" value="true">
              <input type="hidden" name="PHONE_OTP_SEND" value="true">
              <button id="resend-button" type="button" onclick="submitResendForm()" class="btn btn-secondary">
                  ${msg("phone.BTN_RESEND_CODE", "Resend Code")}
                  <span id="timer"></span>
              </button>
          </form>
          
      </div>
    </#if>

    <#if message??>
        <div class="${message.type}">${message.summary}</div>
    </#if>
</body>
</html>
