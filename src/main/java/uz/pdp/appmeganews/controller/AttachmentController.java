package uz.pdp.appmeganews.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appmeganews.payload.ApiResultDTO;
import uz.pdp.appmeganews.service.AttachmentService;
import uz.pdp.appmeganews.utils.AppConst;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppConst.V1 + "/attachment")
public class AttachmentController {
    private final AttachmentService attachmentService;

    @PreAuthorize("hasAuthority(T(uz.pdp.appmeganews.enums.PermissionEnum).READ_DELETED_ATTACHMENTS_ID.name())")
    @GetMapping("/deleted-ids")
    public ApiResultDTO<?> readDeletedIds() {
        return ApiResultDTO.success(attachmentService.readDeleted());
    }

    @PreAuthorize("hasAuthority(T(uz.pdp.appmeganews.enums.PermissionEnum).READ_DELETED_ATTACHMENT.name())")
    @GetMapping("/read-deleted/{id}")
    public void readDeleted(HttpServletResponse resp,@PathVariable Integer id){
        attachmentService.readDeleted(resp,id);
    }

    @GetMapping("/{id}")
    public void read(HttpServletResponse resp, @PathVariable Integer id) {
        attachmentService.read(resp, id);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public ApiResultDTO<?> create(HttpServletRequest req) {
        return attachmentService.create(req);
    }

    @PreAuthorize("hasAuthority(T(uz.pdp.appmeganews.enums.PermissionEnum).UPDATE_ATTACHMENT.name())")
    @PutMapping("/update/{id}")
    public ApiResultDTO<?> update(HttpServletRequest req, @PathVariable Integer id) {
        return attachmentService.update(req, id);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    public ApiResultDTO<?> updateUserAttachment(HttpServletRequest req, @PathVariable Integer id) {
        return attachmentService.updateUserAttachment(req, id);
    }

    @PreAuthorize("hasAuthority(T(uz.pdp.appmeganews.enums.PermissionEnum).DELETE_ATTACHMENT.name())")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {
        attachmentService.delete(id);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public void deleteUserAttachment(@PathVariable Integer id) {
        attachmentService.deleteUserAttachment(id);
    }
}
