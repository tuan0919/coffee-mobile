package nlu.hcmuaf.android_coffee_app.utils;

import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class MyUtils {

  public String generateOtp() {
    Random random = new Random();
    return String.format("%06d", random.nextInt(999999));
  }
}
