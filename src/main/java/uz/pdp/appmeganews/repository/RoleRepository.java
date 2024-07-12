package uz.pdp.appmeganews.repository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appmeganews.entity.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Cacheable(value = "role", key = "#name")
    Optional<Role> findByName(String name);

    @CachePut(value = "role", key = "#result.name")
    @Override
    Role save(Role role);

    @CacheEvict(value = "role", key = "#role.name")
    @Override
    void delete(Role role);
}