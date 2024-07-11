package up.pdp.appmeganews.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import up.pdp.appmeganews.payload.ApiResultDTO;
import up.pdp.appmeganews.payload.SignUpDTO;

public interface AuthService extends UserDetailsService {
    ApiResultDTO<?> signUp(SignUpDTO signUp);
}
