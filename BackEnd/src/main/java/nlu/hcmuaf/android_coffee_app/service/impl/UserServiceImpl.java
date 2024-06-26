package nlu.hcmuaf.android_coffee_app.service.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.nimbusds.jose.shaded.gson.JsonObject;
import jakarta.transaction.Transactional;
import nlu.hcmuaf.android_coffee_app.config.CustomUserDetails;
import nlu.hcmuaf.android_coffee_app.config.JwtService;
import nlu.hcmuaf.android_coffee_app.dto.json.carts.CartJSON;
import nlu.hcmuaf.android_coffee_app.dto.json.products.ProductJSON;
import nlu.hcmuaf.android_coffee_app.dto.json.users.AddressJSON;
import nlu.hcmuaf.android_coffee_app.dto.json.users.UserJSON;
import nlu.hcmuaf.android_coffee_app.dto.request.LoginRequestDTO;
import nlu.hcmuaf.android_coffee_app.dto.request.RegisterRequestDTO;
import nlu.hcmuaf.android_coffee_app.dto.request.VerifyRequestDTO;
import nlu.hcmuaf.android_coffee_app.dto.response.MessageResponseDTO;
import nlu.hcmuaf.android_coffee_app.dto.response.TokenResponseDTO;
import nlu.hcmuaf.android_coffee_app.entities.*;
import nlu.hcmuaf.android_coffee_app.enums.EGender;
import nlu.hcmuaf.android_coffee_app.enums.ERole;
import nlu.hcmuaf.android_coffee_app.repositories.*;
import nlu.hcmuaf.android_coffee_app.service.templates.ICartService;
import nlu.hcmuaf.android_coffee_app.service.templates.IEmailService;
import nlu.hcmuaf.android_coffee_app.service.templates.IUserService;
import nlu.hcmuaf.android_coffee_app.utils.MyUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
public class UserServiceImpl extends AService implements IUserService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private JwtService jwtService;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private RoleRepository roleRepository;
  @Autowired
  private UserDetailRepository userDetailRepository;
  @Autowired
  private ModelMapper modelMapper;
  @Autowired
  private IEmailService emailService;
  @Autowired
  private CartRepository cartRepository;
  @Autowired
  private MyUtils myUtils;
  @Autowired
  private AddressRepository addressRepository;


  private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  @Override
  public TokenResponseDTO login(LoginRequestDTO requestDTO) {
    try {
      Optional<Users> user = userRepository.findAllInfoByEmail(requestDTO.getEmail());
      if (user.isPresent()) {
        if (passwordEncoder.matches(requestDTO.getPassword(),
            user.get().getPassword())) {
          if (user.get().getUserDetails().isVerified()) {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.get().getUsername(),
                    requestDTO.getPassword())
            );

            String jwtToken = jwtService.generateToken(new CustomUserDetails(user.get()));
            return TokenResponseDTO
                .builder()
                .token(jwtToken)
                .role(user.get().getRoles().getRoleName().toString())
                .message("Login success!")
                .build();
          } else {
            return TokenResponseDTO
                .builder()
                .message("Please verified your account!")
                .build();
          }
        } else {
          return TokenResponseDTO
              .builder()
              .message("WRONG PASSWORD!")
              .build();
        }
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
      return TokenResponseDTO
          .builder()
          .message(e.getMessage())
          .build();
    }
    return TokenResponseDTO
        .builder()
        .message("User not found")
        .build();
  }


  @Override
  public MessageResponseDTO register(@Validated RegisterRequestDTO requestDTO) {
    try {
      Optional<UserDetails> userDetail = userDetailRepository.findUserDetailsByEmail(
          requestDTO.getEmail());
      Optional<Users> checkUser = userRepository.findUsersByUsername(requestDTO.getUsername());

      if (checkUser.isPresent()) {
        return MessageResponseDTO
            .builder()
            .message("Username used")
            .build();
      } else if (!userDetail.isPresent()) {
        requestDTO.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        Users users = modelMapper.map(requestDTO, Users.class);

        Iterable<Roles> roles = roleRepository.findAll();
        roles.forEach(role -> {
          if (role.getRoleName().equals(ERole.ROLE_USER)) {
            users.setRoles(role);
          }
        });

        UserDetails newUserDetail = new UserDetails();
        newUserDetail.setUser(users);
        newUserDetail.setEmail(requestDTO.getEmail());
        // set User verification code
        newUserDetail.setVerified(false);
        String otp = myUtils.generateOtp();
        newUserDetail.setOtp(otp);
        newUserDetail.setOtpExpiryTime(LocalDateTime.now().plusMinutes(5));

        users.setCreatedDate(LocalDate.now());
        users.setUserDetails(newUserDetail);
        // send email to User
        emailService.sendVerificationCode(requestDTO.getEmail(), otp);
        userRepository.save(users);
        return MessageResponseDTO
            .builder()
            .message("Register success")
            .build();
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
      return MessageResponseDTO
          .builder()
          .message(e.getMessage())
          .build();
    }
    return MessageResponseDTO
        .builder()
        .message("User already exist")
        .build();
  }

  @Override
  public MessageResponseDTO verifyAccount(@Validated VerifyRequestDTO requestDTO) {
    try {
      Optional<Users> users = userRepository.findAllInfoByEmail(requestDTO.getEmail());
      if (users.isPresent()) {

        // 1. if user verify again
        if (users.get().getUserDetails().isVerified()) {
          return MessageResponseDTO
              .builder()
              .message("User already verified")
              .build();
        }

        // 2. if otp is expiry
        if (users.get().getUserDetails().getOtpExpiryTime().isBefore(LocalDateTime.now())) {
          String otp = myUtils.generateOtp();
          userDetailRepository.updateUserOtp(otp, LocalDateTime.now().plusMinutes(5),
              requestDTO.getEmail());
          emailService.sendVerificationCode(requestDTO.getEmail(), otp);
          return MessageResponseDTO
              .builder()
              .message("Your otp is expired! Please validate again")
              .build();
        }

        // 3. if user enter wrong otp
        if (!users.get().getUserDetails().getOtp().equals(requestDTO.getOtp())) {
          return MessageResponseDTO
              .builder()
              .message("Please enter right OTP")
              .build();
        }

        int rowsUpdated = userDetailRepository.updateUserVerified(requestDTO.getEmail());
        if (rowsUpdated != 0) {
          emailService.sendThankYou(users.get().getUserDetails().getEmail());
          return MessageResponseDTO
              .builder()
              .message("Verified success")
              .build();
        }
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
      return MessageResponseDTO
          .builder()
          .message("500 Error")
          .build();
    }
    return MessageResponseDTO
        .builder()
        .message("User not exist")
        .build();
  }


  @Override
  @Transactional(rollbackOn = Exception.class)
  public void initData() {
    if (userRepository.findAll().isEmpty()) {
      try {
        ClassPathResource resource = new ClassPathResource("users.json");
        List<UserJSON> userJSONS = objectMapper.readValue(resource.getInputStream(),
                new TypeReference<List<UserJSON>>() {});
        List<Users> users = new ArrayList<>();
        for (UserJSON json : userJSONS) {
          // Users
          Users user = new Users();
          var oRole = roleRepository.getRolesByRoleName(json.getRole());
          if (oRole.isEmpty()) throw new Exception("Role không tồn tại!");
          user.setRoles(oRole.get());
          user.setUsername(json.getUsername());
          user.setPassword(passwordEncoder.encode(json.getPassword()));
          user.setCreatedDate(LocalDate.now());
          // create cart for this user
          var cart = Cart.builder().user(user).build();
          user.setCart(cart);
          // User Details
          UserDetails details = new UserDetails();
          {
            var json_details = json.getDetails();
            details.setUser(user);
            details.setGender(json_details.getGender());
            details.setImg(json_details.getImg());
            details.setEmail(json_details.getEmail());
            details.setVerified(json_details.isVerified());
            details.setOtpExpiryTime(LocalDateTime.now().plusMinutes(5));
            details.setPhoneNum(json_details.getPhone());
            details.setFirstname(json_details.getFirstName());
            details.setLastname(json_details.getLastName());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            details.setDob(LocalDate.parse(json_details.getDob(), formatter));
            details.setOtp(myUtils.generateOtp());
          }
          Set<OwnAddress> addressesSet = new HashSet<>();
          {
            var json_addresses = json.getAddresses();
            for (AddressJSON addressJSON : json_addresses) {
              var address = new Addresses();
              {
                address.setCity(addressJSON.getCity());
                address.setWard(addressJSON.getWard());
                address.setDistrict(addressJSON.getDistrict());
                address.setStreet(addressJSON.getStreet());
              }
              addressesSet.add(new OwnAddress(details, address, addressJSON.isMainAddress()));
              addressRepository.save(address);
            }
          }
          details.setUserAddresses(addressesSet);
          user.setUserDetails(details);
          users.add(user);
        }
        userRepository.saveAll(users);
      } catch (Exception e) {
        throw new RuntimeException(e.getMessage());
      }
    }
  }
}
