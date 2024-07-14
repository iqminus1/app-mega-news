package uz.pdp.appmeganews.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.appmeganews.entity.Category;
import uz.pdp.appmeganews.payload.ApiResultDTO;
import uz.pdp.appmeganews.payload.CategoryDTO;
import uz.pdp.appmeganews.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public ApiResultDTO<?> read() {
        return ApiResultDTO.success(categoryRepository.findAll().stream().map(this::toDTO).toList());
    }

    @Override
    public ApiResultDTO<?> read(Integer id) {
        return ApiResultDTO.success(toDTO(categoryRepository.getById(id)));
    }

    @Override
    public ApiResultDTO<?> create(String name) {
        Category category = new Category(name);
        categoryRepository.save(category);
        return ApiResultDTO.success(toDTO(category));
    }

    @Override
    public ApiResultDTO<?> update(String name, Integer id) {
        Category category = categoryRepository.getById(id);
        category.setName(name);
        categoryRepository.save(category);
        return ApiResultDTO.success(toDTO(category));
    }

    @Override
    public void delete(Integer id) {
        categoryRepository.deleteById(id);
    }

    public CategoryDTO toDTO(Category category) {
        return new CategoryDTO(category.getId(), category.getName());
    }
}
