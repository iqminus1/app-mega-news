package uz.pdp.appmeganews.config;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Service;
import uz.pdp.appmeganews.entity.User;
import uz.pdp.appmeganews.entity.template.AbsIntEntity;
import uz.pdp.appmeganews.repository.UserRepository;
import uz.pdp.appmeganews.utils.CommonUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IntAuditConfig implements AuditorAware<Integer> {
    private final UserRepository userRepository;


    @Override
    public Optional<Integer> getCurrentAuditor() {
        Optional<User> currentUser = CommonUtils.getCurrentUser();
        return currentUser.map(AbsIntEntity::getId);
    }
}


