package uz.pdp.appmeganews.repository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appmeganews.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    @Cacheable(value = "userEntity", key = "#username")
    Optional<User> findByUsername(String username);

    @CachePut(value = "userEntity", key = "#result.username")
    @Override
    User save(User user);

    @CacheEvict(value = "userEntity", key = "#user.username")
    @Override
    void delete(User user);
}