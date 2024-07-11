package up.pdp.appmeganews.init;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import up.pdp.appmeganews.entity.Role;
import up.pdp.appmeganews.entity.User;
import up.pdp.appmeganews.enums.PermissionEnum;
import up.pdp.appmeganews.enums.RoleTypeEnum;
import up.pdp.appmeganews.repository.RoleRepository;
import up.pdp.appmeganews.repository.UserRepository;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DataLoader implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.firstName}")
    private String firstName;
    @Value("${app.admin.lastName}")
    private String lastName;
    @Value("${app.admin.username}")
    private String username;
    @Value("${app.admin.password}")
    private String password;
    @Value("${app.admin.email}")
    private String email;

    @Override
    public void run(String... args) {
        checkUserRole();

        checkAdmin();

    }


    private void checkAdmin() {
        Role adminRole = getAdminRole();

        User user = userRepository.findByUsername(username).orElseGet(() -> {
            User admin = new User(firstName, lastName, username, passwordEncoder.encode(password), email, true, adminRole);
            userRepository.save(admin);
            return admin;
        });

        List<PermissionEnum> permissions = user.getRole().getPermissions();
        if (permissions.size() == PermissionEnum.values().length)
            return;

        for (PermissionEnum value : PermissionEnum.values()) {
            if (!permissions.contains(value)) {
                permissions.add(value);
            }
        }

        roleRepository.save(user.getRole());
    }

    private Role getAdminRole() {
        return roleRepository.findByType(RoleTypeEnum.ADMIN).orElseGet(() -> {
                    Role adminRole = new Role("Admin", RoleTypeEnum.ADMIN, Arrays.stream(PermissionEnum.values()).toList());
                    roleRepository.save(adminRole);
                    return adminRole;
                }
        );
    }

    private void checkUserRole() {
        if (roleRepository.findByType(RoleTypeEnum.USER).isEmpty()) {
            Role userRole = new Role("User", RoleTypeEnum.USER, List.of(PermissionEnum.USER_PERMISSION));
            roleRepository.save(userRole);
        }
    }
}
