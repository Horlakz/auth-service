package com.zeyza.auth.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import com.zeyza.auth.config.EmailConfig;
import com.zeyza.auth.entity.Code;
import com.zeyza.auth.entity.Role;
import com.zeyza.auth.entity.User;
import com.zeyza.auth.enums.ERole;
import com.zeyza.auth.exception.EntityNotFoundException;
import com.zeyza.auth.exception.RoleException;
import com.zeyza.auth.repository.CodeRepository;
import com.zeyza.auth.repository.RoleRepository;
import com.zeyza.auth.repository.UserRepository;
import com.zeyza.auth.utils.JwtUtils;
import com.zeyza.auth.utils.StringHelper;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final CodeRepository codeRepository;
    private final RoleRepository roleRepository;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder encoder;
    private final EmailConfig emailConfig;

    private String generateCode(User user) {
        Code code = new Code();
        String otp = StringHelper.otpGenerator();
        if (codeRepository.existsByCodeAndDeletedAtIsNull(otp)) {
            otp = StringHelper.otpGenerator();
        }

        code.setCode(otp);
        code.setUser(user);

        codeRepository.save(code);

        return code.getCode();
    }

    private void sendMail(String code, User user, String templateName, String subject) throws MessagingException {

        Context context = new Context();
        context.setVariable("code", code);
        context.setVariable("name", user.getUsername());

        emailConfig.sendMail(user.getEmail(), subject, templateName, context);
    }

    private Code getCodeEntity(String code) throws EntityNotFoundException {
        Code codeEntityOptional = codeRepository.findByCodeAndDeletedAtIsNull(code)
                .orElseThrow(() -> new EntityNotFoundException());
        return codeEntityOptional;
    }

    public String login(String email, String password) {
        User user = userService.findByEmail(email);
        String username = user.getUsername();

        Boolean isPasswordMatch = encoder.matches(password, user.getPassword());

        if (!isPasswordMatch) {
            throw new RuntimeException("Invalid password!");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                password);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);

        return jwt;
    }

    public void register(String username, String email, String password) throws MessagingException, RoleException {
        Set<Role> roles = new HashSet<>();
        User user = new User();

        Role userRole = roleRepository.findByName(ERole.USER)
                .orElseThrow(() -> new RoleException("Error: Role is not found."));

        roles.add(userRole);

        user.setUsername(StringHelper.lowerCase(username));
        user.setEmail(email);
        user.setPassword(encoder.encode(password));
        user.setIsEmailVerified(false);
        user.setRoles(roles);

        userRepository.save(user);

        User userEntity = userRepository.findByUsername(username).get();

        String code = generateCode(userEntity);
        sendMail(code, user, "confirm-email", "Confirm Your Email");

    }

    public void resendVerificationEmail(String email)
            throws MessagingException, RuntimeException, EntityNotFoundException {
        User user = userService.findByEmail(email);

        if (user.getIsEmailVerified()) {
            throw new RuntimeException("User is already verified!");
        }

        Code existingCode = codeRepository.findByUserIdAndDeletedAtIsNull(user.getId())
                .orElseThrow(() -> new EntityNotFoundException());

        if (existingCode != null) {
            existingCode.delete();
            codeRepository.save(existingCode);
        }

        String code = generateCode(user);
        sendMail(code, user, "confirm-email", "Confirm Your Email");

    }

    public void verifyEmail(String code) {
        Code codeEntity = getCodeEntity(code);

        User user = codeEntity.getUser();
        user.setIsEmailVerified(true);
        userRepository.save(user);

        codeEntity.delete();
        codeRepository.save(codeEntity);

    }

    public void forgotPassword(String email) throws MessagingException {
        User user = userService.findByEmail(email);

        String code = generateCode(user);
        sendMail(code, user, "reset-password", "Reset Your Password");
    }

    public void resetPassword(String email, String code, String password) {
        Code codeEntity = getCodeEntity(code);
        User user = codeEntity.getUser();

        if (!user.getEmail().equals(email)) {
            throw new RuntimeException("Email is not valid!");
        }

        user.setPassword(encoder.encode(password));
        userRepository.save(user);

        codeEntity.delete();
        codeRepository.save(codeEntity);
    }

}
