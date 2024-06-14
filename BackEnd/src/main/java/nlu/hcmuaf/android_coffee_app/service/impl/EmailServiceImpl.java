package nlu.hcmuaf.android_coffee_app.service.impl;

import nlu.hcmuaf.android_coffee_app.service.templates.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements IEmailService {

  @Autowired
  private JavaMailSender mailSender;

  @Override
  public void sendVerificationCode(String email, String verificationCode) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("nqat0919@gmail.com");
    message.setTo(email);
    message.setSubject("Xác thực tài khoản của bạn");
    message.setText("Mã xác thực của bạn là: " + verificationCode +
        "\n" + "Bạn có thời hạn là 5 phút.");
    try {
      mailSender.send(message);
    } catch (Exception e) {
      System.err.println("Cant send email");
    }
  }

  @Override
  public void sendThankYou(String email) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("nqat0919@gmail.com");
    message.setTo(email);
    message.setSubject("Đăng ký thành công");
    message.setText(
        "Cảm ơn bạn đã đăng ký tài khoản của ứng dụng \"NLU light novels & manga\" chúng tôi sẽ mang đến cho bạn trải nghiệm mua sắm tuyệt vời nhất trong lúc sử dụng ứng dụng."
            + "\n"
            + "Một lần nữa cảm ơn bạn đã tin tưởng và sử dụng ứng dụng của chúng tôi để đặt light novel, manga chất lượng nhất Việt Nam. \uD83D\uDC27");
    try {
      mailSender.send(message);
    } catch (Exception e) {
      System.err.println("Cant send email");
    }
  }
}
