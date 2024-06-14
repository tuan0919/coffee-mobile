package nlu.hcmuaf.android_coffee_app.service.templates;

import nlu.hcmuaf.android_coffee_app.dto.request.LoginRequestDTO;
import nlu.hcmuaf.android_coffee_app.dto.request.RegisterRequestDTO;
import nlu.hcmuaf.android_coffee_app.dto.request.VerifyRequestDTO;
import nlu.hcmuaf.android_coffee_app.dto.response.MessageResponseDTO;
import nlu.hcmuaf.android_coffee_app.dto.response.TokenResponseDTO;

public interface IUserService extends IInitializerData {

  TokenResponseDTO login(LoginRequestDTO requestDTO);

  MessageResponseDTO register(RegisterRequestDTO requestDTO);

  MessageResponseDTO verifyAccount(VerifyRequestDTO requestDTO);

}
