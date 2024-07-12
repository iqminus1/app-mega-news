package uz.pdp.appmeganews.repository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appmeganews.entity.Code;

import java.util.Optional;

@Repository
public interface CodeRepository extends JpaRepository<Code, Integer> {
    @Cacheable(value = "code", key = "#email")
    Optional<Code> findByEmail(String email);

    @CachePut(value = "code", key = "#result.email")
    @Override
    Code save(Code code);

    @CacheEvict(value = "code", key = "#code.email")
    @Override
    void delete(Code code);
}