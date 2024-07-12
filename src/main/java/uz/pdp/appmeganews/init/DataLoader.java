package uz.pdp.appmeganews.init;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.appmeganews.entity.Role;
import uz.pdp.appmeganews.entity.User;
import uz.pdp.appmeganews.enums.PermissionEnum;
import uz.pdp.appmeganews.enums.RoleTypeEnum;
import uz.pdp.appmeganews.repository.RoleRepository;
import uz.pdp.appmeganews.repository.UserRepository;

import java.util.ArrayList;
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

        List<PermissionEnum> permissions = new ArrayList<>(user.getRole().getPermissions()); // Создаем изменяемую копию
        if (permissions.size() == PermissionEnum.values().length)
            return;


        adminRole.setPermissions(Arrays.stream(PermissionEnum.values()).toList()); // Устанавливаем измененные разрешения в объект Role

        roleRepository.save(adminRole); // Сохраняем изменения
    }

    private Role getAdminRole() {
        return roleRepository.findByName("Admin").orElseGet(() -> {
            List<PermissionEnum> permissions = new ArrayList<>(Arrays.asList(PermissionEnum.values()));
            Role adminRole = new Role("Admin", RoleTypeEnum.ADMIN, permissions);
            roleRepository.save(adminRole);
            return adminRole;
        });
    }

    private void checkUserRole() {
        if (roleRepository.findByName("User").isEmpty()) {
            Role userRole = new Role("User", RoleTypeEnum.USER, List.of(PermissionEnum.USER_PERMISSION));
            roleRepository.save(userRole);
        }
    }
}
