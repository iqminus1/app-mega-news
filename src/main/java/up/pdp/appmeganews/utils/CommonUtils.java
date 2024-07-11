package up.pdp.appmeganews.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import up.pdp.appmeganews.entity.User;

import java.util.Optional;

public class CommonUtils {

    public static Optional<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null || authentication.getPrincipal().equals("anonymousUser")) {
            return Optional.empty();
        }

        return Optional.of((User) authentication.getPrincipal());
    }

}
