package up.pdp.appmeganews.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import up.pdp.appmeganews.entity.Role;
import up.pdp.appmeganews.entity.User;
import up.pdp.appmeganews.enums.RoleTypeEnum;
import up.pdp.appmeganews.exceptions.NotReadyForWorkException;
import up.pdp.appmeganews.payload.ApiResultDTO;
import up.pdp.appmeganews.payload.SignUpDTO;
import up.pdp.appmeganews.repository.RoleRepository;
import up.pdp.appmeganews.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final MailService mailService;

    @Cacheable(value = "user", key = "#username")
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow();
    }


    @Transactional
    @Override
    public ApiResultDTO<?> signUp(SignUpDTO signUp) {
        Role role = roleRepository.findByType(RoleTypeEnum.USER).orElseThrow(() -> new NotReadyForWorkException("System not working!!!"));
        User user = new User(signUp.getFirstName(), signUp.getLastName(), signUp.getUsername(), passwordEncoder.encode(signUp.getPassword()), signUp.getEmail(), false, role);
        userRepository.save(user);
        mailService.sendCode(user.getEmail());
        return ApiResultDTO.success("Ok");
    }
}
