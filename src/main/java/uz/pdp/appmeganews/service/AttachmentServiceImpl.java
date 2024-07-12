package uz.pdp.appmeganews.service;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uz.pdp.appmeganews.entity.Attachment;
import uz.pdp.appmeganews.entity.User;
import uz.pdp.appmeganews.mapper.AttachmentMapper;
import uz.pdp.appmeganews.payload.ApiResultDTO;
import uz.pdp.appmeganews.payload.AttachmentDTO;
import uz.pdp.appmeganews.repository.AttachmentRepository;
import uz.pdp.appmeganews.utils.CommonUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AttachmentServiceImpl implements AttachmentService {
    @Value("${app.base-path}")
    private String BASE_PATH;

    private final AttachmentRepository attachmentRepository;
    private final AttachmentMapper attachmentMapper;

    @Override
    public ApiResultDTO<?> readDeleted() {
        return ApiResultDTO.success(attachmentRepository.findAllDeletedIds());
    }

    @Override
    public void readDeleted(HttpServletResponse resp, Integer id) {
        Attachment attachment = attachmentRepository.findDeletedAttachment(id).orElseThrow();

        Path path = Path.of(attachment.getPath());

        try (ServletOutputStream outputStream = resp.getOutputStream()) {
            Files.copy(path, outputStream);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void read(HttpServletResponse resp, Integer id) {
        Path path = Path.of(attachmentRepository.readById(id).getPath());

        try (ServletOutputStream outputStream = resp.getOutputStream()) {
            Files.copy(path, outputStream);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @SneakyThrows
    @Override
    public ApiResultDTO<?> create(HttpServletRequest req) {
        List<AttachmentDTO> attachments = new ArrayList<>();
        for (Part part : req.getParts()) {
            Attachment attachment = createOrUpdate(part, new Attachment(), false);
            attachments.add(attachmentMapper.toDTO(attachment));
        }
        return ApiResultDTO.success(attachments);
    }

    @SneakyThrows
    @Override
    public ApiResultDTO<?> update(HttpServletRequest req, Integer id) {
        AttachmentDTO dto = null;
        for (Part part : req.getParts()) {
            Attachment attachment = createOrUpdate(part, attachmentRepository.readById(id), true);
            dto = attachmentMapper.toDTO(attachment);
            break;
        }
        return ApiResultDTO.success(dto);
    }

    @Override
    public ApiResultDTO<?> updateUserAttachment(HttpServletRequest req, Integer id) {
        checkAndGetAttachment(id);
        return update(req, id);
    }

    @Override
    public void delete(Integer id) {
        attachmentRepository.deleteById(id);
    }

    @Override
    public void deleteUserAttachment(Integer id) {
        Attachment attachment = checkAndGetAttachment(id);
        attachmentRepository.delete(attachment);
    }

    private Attachment checkAndGetAttachment(Integer id) {
        User user = CommonUtils.getCurrentUser().orElseThrow();
        Attachment attachment = attachmentRepository.readById(id);
        if (!user.getId().equals(attachment.getCreateBy())) {
            throw new RuntimeException();
        }
        return attachment;
    }

    private Attachment createOrUpdate(Part part, Attachment attachment, boolean isUpdate) {
        if (isUpdate) {
            Attachment deletedAttachment = new Attachment(attachment.getName(),
                    attachment.getPath(),
                    attachment.getContentType(),
                    attachment.getOriginalName(),
                    attachment.getSize());
            deletedAttachment.setDeleted(true);
            attachmentRepository.save(deletedAttachment);
        }
        try {

            String contentType = part.getContentType();
            String originalName = part.getSubmittedFileName();
            long size = part.getSize();

            String[] split = originalName.split("\\.");
            String s = split[split.length - 1];
            UUID uuid = UUID.randomUUID();

            String name = uuid + "." + s;

            String pathString = BASE_PATH + "/" + name;

            Files.copy(part.getInputStream(), Path.of(pathString));

            attachment.setName(name);
            attachment.setSize(size);
            attachment.setPath(pathString);
            attachment.setContentType(contentType);
            attachment.setOriginalName(originalName);

            attachmentRepository.save(attachment);

            return attachment;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
