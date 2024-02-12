package com.example.laza.user;

import com.example.laza.address.AddressRepository;
import com.example.laza.address.dto.AddressCreateDto;
import com.example.laza.address.entity.Address;
import com.example.laza.exception.ExceptionUNAUTHORIZED;
import com.example.laza.exception.SmsVerificationException;
import com.example.laza.otp.OTP;
import com.example.laza.otp.OTPRepository;
import com.example.laza.otp.dto.OtpVerifyDto;
import com.example.laza.payment.PaymentRepository;
import com.example.laza.payment.dto.PaymentCreateDto;
import com.example.laza.payment.entity.Payment;
import com.example.laza.user.dto.*;
import com.example.laza.user.entity.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper;
    private final OTPRepository otpRepository;
    private final AddressRepository addressRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User email not found"));
    }

    public UserResponseDto signUp(UserCreateDto userCreateDto) {

        User user = mapper.map(userCreateDto, User.class);
        user.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));

        return mapper.map(userRepository.save(user), UserResponseDto.class);
    }

    public UserResponseDto signIn(UserSignInDto userSignInDto) {

        if (isValidGmail(userSignInDto.getFirstnameOrEmail())) {

            User user = userRepository.findByEmail(userSignInDto.getFirstnameOrEmail())
                    .orElseThrow(() -> new EntityNotFoundException("user email not found"));

            if (passwordEncoder.matches(userSignInDto.getPassword(), user.getPassword())) {
                return mapper.map(user, UserResponseDto.class);
            } else {
                throw new RuntimeException("Incorrect password");
            }

        } else {
            User user = userRepository.findByFirstname(userSignInDto.getFirstnameOrEmail())
                    .orElseThrow(() -> new EntityNotFoundException("user name not found"));

            if (passwordEncoder.matches(userSignInDto.getPassword(), user.getPassword())) {
                return mapper.map(user, UserResponseDto.class);
            } else {
                throw new RuntimeException("Incorrect password");
            }
        }

    }

    public static boolean isValidGmail(String email) {
        String regex = "^[a-zA-Z0-9_.+-]+@gmail\\.com$";
        return Pattern.compile(regex).matcher(email).matches();
    }

    public UserResponseDto forgotPassword(ForgotPassword email) {

        User user = userRepository.findByEmail(email.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("User email not found"));

        sendEmail(email.getEmail(), user);
        return mapper.map(user, UserResponseDto.class);
    }

    private void sendEmail(String recipientEmail, User user) {
        String host = "smtp.gmail.com";
        String port = "587";
        String username = "rikhsiboyevzaxa@gmail.com";
        String password = "duhd sbzo hpxn fkkg";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Laza Forgot password");

            OTP otp = mapper.map(user, OTP.class);
            int code = new Random().nextInt(1000, 9999);
            otp.setCode(code);
            otp.setSendTime(LocalDateTime.now());
            otp.setSentCount(otp.getSentCount() + 1);
            message.setText(String.valueOf(code));
            Transport.send(message);
            otpRepository.save(otp);

        } catch (MessagingException e) {
            e.getCause();
        }
    }

    public UserResponseDto verifyForgotPassCode(OtpVerifyDto verifyDto) {

        User user = userRepository.findByEmail(verifyDto.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("User email not found"));

        OTP otp = otpRepository.findById(verifyDto.getEmail())
                .orElseThrow(() -> new ExceptionUNAUTHORIZED("You need to register first"));

        if (otp.getCode() == verifyDto.getCode()) {

            user.setPassword(passwordEncoder.encode(verifyDto.getNewPassword()));

            otpRepository.delete(otp);

            userRepository.save(user);

            return mapper.map(user, UserResponseDto.class);

        } else throw new SmsVerificationException("Invalid verification code");


    }

    public Page<UserResponseDto> getAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(user -> mapper.map(user, UserResponseDto.class));
    }

    public UserResponseDto getById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("user id not found"));
        return mapper.map(user, UserResponseDto.class);
    }

    public UserResponseDto update(Integer id, UserUpdateDto userUpdateDto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("user id not found"));

        mapper.map(userUpdateDto, user);

        userRepository.save(user);
        return mapper.map(user, UserResponseDto.class);
    }

    public UserResponseDto patch(Integer id, UserUpdateDto userUpdate) throws IllegalAccessException, NoSuchFieldException {

        User username = userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        Class<?> usernameClass = username.getClass();
        Class<?> patchDtoClass = userUpdate.getClass();

        for (Field field : patchDtoClass.getDeclaredFields()) {
            field.setAccessible(true);
            Object value = field.get(userUpdate);
            if (value != null) {
                Field usernameClassField = usernameClass.getDeclaredField(field.getName());
                usernameClassField.setAccessible(true);
                usernameClassField.set(username, value);
            }
        }
        User user = userRepository.save(username);
        return mapper.map(user, UserResponseDto.class);
    }

    public void delete(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("user id not found"));
        userRepository.delete(user);

    }

    public UserResponseDto addUserAddress(Integer id, AddressCreateDto addressCreateDto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("user id not found"));

        Address address = mapper.map(addressCreateDto, Address.class);

        address.setUser(user);
        addressRepository.save(address);
        user.getAddresses().add(address);


        return mapper.map(userRepository.save(user), UserResponseDto.class);
    }

    public UserResponseDto addUserCard(Integer id, PaymentCreateDto createDto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("user id not found"));

        Payment payment = mapper.map(createDto, Payment.class);

        payment.setUser(user);

        paymentRepository.save(payment);

        user.getPayments().add(payment);
        return mapper.map(userRepository.save(user), UserResponseDto.class);
    }
}

