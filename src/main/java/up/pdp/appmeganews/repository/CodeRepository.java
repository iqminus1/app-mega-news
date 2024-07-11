package up.pdp.appmeganews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import up.pdp.appmeganews.entity.Code;

@Repository
public interface CodeRepository extends JpaRepository<Code, Integer> {
}