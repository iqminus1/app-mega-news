package up.pdp.appmeganews.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import up.pdp.appmeganews.payload.ApiResultDTO;
import up.pdp.appmeganews.payload.SignUpDTO;
import up.pdp.appmeganews.service.AuthService;
import up.pdp.appmeganews.utils.AppConst;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppConst.V1 + "/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    public ApiResultDTO<?> signUp(@RequestBody @Valid SignUpDTO signUp) {
        return authService.signUp(signUp);
    }
}
