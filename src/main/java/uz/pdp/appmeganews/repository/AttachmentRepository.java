package uz.pdp.appmeganews.repository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.appmeganews.entity.Attachment;
import uz.pdp.appmeganews.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {
    @Query(value = ("select a from Attachment a where a.deleted = true"))
    List<Attachment> findAllDeletedIds();

    @Query(value = ("select a from Attachment a where a.id = ?1 and a.deleted = true"))
    Optional<Attachment> findDeletedAttachment(Integer id);

    @Cacheable(value = "attachmentEntity", key = "#id")
    default Attachment readById(Integer id) {
        return findById(id).orElseThrow(() -> new NotFoundException("attachment not found by id -> " + id));
    }

    @CachePut(value = "attachmentEntity", key = "#result.id")
    @Override
    Attachment save(Attachment attachment);

    @CacheEvict(value = "attachmentEntity", key = "#id")
    @Override
    void deleteById(Integer id);

    @CacheEvict(value = "attachmentEntity", key = "#attachment.id")
    @Override
    void delete(Attachment attachment);
}