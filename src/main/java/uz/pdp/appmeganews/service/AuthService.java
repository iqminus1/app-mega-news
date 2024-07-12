package uz.pdp.appmeganews.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import uz.pdp.appmeganews.payload.ApiResultDTO;
import uz.pdp.appmeganews.payload.SignInDTO;
import uz.pdp.appmeganews.payload.SignUpDTO;

public interface AuthService extends UserDetailsService {
    ApiResultDTO<?> signUp(SignUpDTO signUp);
    ApiResultDTO<?> verifyEmail(String email, String codeString);
    ApiResultDTO<?> signIn(SignInDTO signIn);
}
