package uz.pdp.appmeganews.service;

import uz.pdp.appmeganews.payload.ApiResultDTO;

public interface CategoryService {
    ApiResultDTO<?> read();

    ApiResultDTO<?> read(Integer id);

    ApiResultDTO<?> create(String name);

    ApiResultDTO<?> update(String name, Integer id);

    void delete(Integer id);
}
