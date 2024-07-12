package uz.pdp.appmeganews.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uz.pdp.appmeganews.payload.ApiResultDTO;

public interface AttachmentService {
    ApiResultDTO<?> readDeleted();
    void readDeleted(HttpServletResponse resp, Integer id);
    void read(HttpServletResponse resp, Integer id);

    ApiResultDTO<?> create(HttpServletRequest req);

    ApiResultDTO<?> update(HttpServletRequest req, Integer id);
    ApiResultDTO<?> updateUserAttachment(HttpServletRequest req, Integer id);

    void delete(Integer id);
    void deleteUserAttachment(Integer id);
}
