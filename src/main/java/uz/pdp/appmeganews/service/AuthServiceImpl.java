package uz.pdp.appmeganews.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.coyote.BadRequestException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.appmeganews.entity.Code;
import uz.pdp.appmeganews.entity.Role;
import uz.pdp.appmeganews.entity.User;
import uz.pdp.appmeganews.exceptions.NotReadyForWorkException;
import uz.pdp.appmeganews.payload.TokenDTO;
import uz.pdp.appmeganews.payload.ApiResultDTO;
import uz.pdp.appmeganews.payload.SignInDTO;
import uz.pdp.appmeganews.payload.SignUpDTO;
import uz.pdp.appmeganews.repository.CodeRepository;
import uz.pdp.appmeganews.repository.RoleRepository;
import uz.pdp.appmeganews.repository.UserRepository;
import uz.pdp.appmeganews.security.JwtProvider;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final MailService mailService;
    private final CodeRepository codeRepository;
    private final JwtProvider jwtProvider;
    private final AuthenticationProvider authenticationProvider;

    @Cacheable(value = "user", key = "#username")
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow();
    }


    @Transactional
    @Override
    public ApiResultDTO<?> signUp(SignUpDTO signUp) {
        Role role = roleRepository.findByName("User").orElseThrow(() -> new NotReadyForWorkException("System not working!!!"));
        User user = new User(signUp.getFirstName(), signUp.getLastName(), signUp.getUsername(), passwordEncoder.encode(signUp.getPassword()), signUp.getEmail(), false, role);
        userRepository.save(user);
        mailService.sendCode(user.getEmail());
        return ApiResultDTO.success("Ok");
    }

    @SneakyThrows
    @Override
    public ApiResultDTO<?> verifyEmail(String email, String codeString) {
        Optional<Code> byEmail = codeRepository.findByEmail(email);
        if (byEmail.isEmpty())
            throw new BadRequestException("email not found for verify");

        Code code = byEmail.get();
        if (!code.getCode().equals(codeString)) {
            if (code.getAttempt() == 1) {
                User user = userRepository.findByEmail(email).orElseThrow();
                userRepository.delete(user);
                codeRepository.delete(code);
                throw new BadRequestException("Your verify code deleted");
            }
            code.setAttempt(code.getAttempt() - 1);
            codeRepository.save(code);
            return ApiResultDTO.error("Code not equals");
        }
        codeRepository.delete(code);

        User user = userRepository.findByEmail(email).orElseThrow();
        user.setEnable(true);
        userRepository.save(user);

        String token = jwtProvider.generateToken(user.getUsername());

        return ApiResultDTO.success(new TokenDTO(token));
    }

    @Override
    public ApiResultDTO<?> signIn(SignInDTO signIn) {
        try {
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(signIn.getUsername(), signIn.getPassword());
            authenticationProvider.authenticate(auth);
            String token = jwtProvider.generateToken(signIn.getUsername());
            return ApiResultDTO.success(new TokenDTO(token));
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
