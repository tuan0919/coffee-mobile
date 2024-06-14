package nlu.hcmuaf.android_coffee_app.controller;

import nlu.hcmuaf.android_coffee_app.config.JwtService;
import nlu.hcmuaf.android_coffee_app.dto.request.LoginRequestDTO;
import nlu.hcmuaf.android_coffee_app.dto.request.RegisterRequestDTO;
import nlu.hcmuaf.android_coffee_app.dto.request.VerifyRequestDTO;
import nlu.hcmuaf.android_coffee_app.dto.response.MessageResponseDTO;
import nlu.hcmuaf.android_coffee_app.dto.response.TokenResponseDTO;
import nlu.hcmuaf.android_coffee_app.service.templates.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class LoginController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtService jwtService;

  @Autowired
  private IUserService userService;

  @PostMapping("login")
  public ResponseEntity<TokenResponseDTO> login(@RequestBody LoginRequestDTO requestDTO) {
    return new ResponseEntity<>(userService.login(requestDTO), HttpStatus.OK);
  }

  @PostMapping("register")
  public ResponseEntity<MessageResponseDTO> register(@RequestBody RegisterRequestDTO requestDTO) {
    return new ResponseEntity<>(userService.register(requestDTO), HttpStatus.OK);
  }

  @PostMapping("verify-account")
  public ResponseEntity<MessageResponseDTO> verifyAccount(
      @RequestBody VerifyRequestDTO requestDTO) {
    return new ResponseEntity<>(userService.verifyAccount(requestDTO), HttpStatus.OK);
  }

  @GetMapping("test")
  public ResponseEntity<MessageResponseDTO> test(
      @RequestBody MessageResponseDTO messageResponseDTO) {
    return new ResponseEntity<>(
        MessageResponseDTO.builder().message("DJT me langw coc day nhu dau buoi").build(),
        HttpStatus.OK);
  }

}
