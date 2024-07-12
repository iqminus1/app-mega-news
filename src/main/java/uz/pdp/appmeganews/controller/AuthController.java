package uz.pdp.appmeganews.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appmeganews.payload.ApiResultDTO;
import uz.pdp.appmeganews.payload.SignInDTO;
import uz.pdp.appmeganews.payload.SignUpDTO;
import uz.pdp.appmeganews.service.AuthService;
import uz.pdp.appmeganews.utils.AppConst;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppConst.V1 + "/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    public ApiResultDTO<?> signUp(@RequestBody @Valid SignUpDTO signUp) {
        return authService.signUp(signUp);
    }

    @PostMapping("/verification")
    public ApiResultDTO<?> verifyEmail(@RequestParam String email, @RequestParam String code) {
        return authService.verifyEmail(email, code);
    }

    @PostMapping("/sign-in")
    public ApiResultDTO<?> signIn(@RequestBody @Valid SignInDTO signIn) {
        return authService.signIn(signIn);
    }
}
