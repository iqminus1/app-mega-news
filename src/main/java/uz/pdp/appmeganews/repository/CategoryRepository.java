package uz.pdp.appmeganews.repository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appmeganews.entity.Category;
import uz.pdp.appmeganews.exceptions.NotFoundException;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Cacheable(value = "categoryEntity", key = "#id")
    default Category getById(Integer id) {
        return findById(id).orElseThrow(() -> new NotFoundException("Category not found by id -> " + id));
    }

    @CachePut(value = "categoryEntity", key = "#result.id")
    @Override
    Category save(Category code);

    @CacheEvict(value = "categoryEntity", key = "#code.id")
    @Override
    void delete(Category code);

    @CacheEvict(value = "categoryEntity", key = "#id")
    @Override
    void deleteById(Integer id);
}