package nlu.hcmuaf.android_coffee_app.service.templates;

public interface IEmailService {

  void sendVerificationCode(String email, String verificationCode);

  void sendThankYou(String email);
}
