package up.pdp.appmeganews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import up.pdp.appmeganews.entity.Role;
import up.pdp.appmeganews.enums.RoleTypeEnum;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByType(RoleTypeEnum type);
}