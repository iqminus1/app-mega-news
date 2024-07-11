package up.pdp.appmeganews.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Service;
import up.pdp.appmeganews.entity.User;
import up.pdp.appmeganews.entity.template.AbsIntEntity;
import up.pdp.appmeganews.utils.CommonUtils;

import java.util.Optional;

@Service
public class IntAuditConfig implements AuditorAware<Integer> {
    @Override
    public Optional<Integer> getCurrentAuditor() {
        Optional<User> currentUser = CommonUtils.getCurrentUser();
        return currentUser.map(AbsIntEntity::getId);
    }
}


