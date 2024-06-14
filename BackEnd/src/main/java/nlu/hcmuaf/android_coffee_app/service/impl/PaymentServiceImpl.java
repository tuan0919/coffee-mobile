package nlu.hcmuaf.android_coffee_app.service.impl;

import nlu.hcmuaf.android_coffee_app.entities.Payments;
import nlu.hcmuaf.android_coffee_app.enums.EPaymentMethod;
import nlu.hcmuaf.android_coffee_app.repositories.PaymentRepository;
import nlu.hcmuaf.android_coffee_app.service.templates.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl extends AService implements IPaymentService {

  @Autowired
  private PaymentRepository paymentRepository;

  @Override
  public void initData() {
    if (paymentRepository.getAllBy().isEmpty()) {
      for (EPaymentMethod method : EPaymentMethod.values()) {
        Payments payments = new Payments(method);
        paymentRepository.save(payments);
      }
    }
  }
}
