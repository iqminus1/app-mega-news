package uz.pdp.appmeganews.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import uz.pdp.appmeganews.entity.Attachment;
import uz.pdp.appmeganews.payload.AttachmentDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AttachmentMapper {
    AttachmentDTO toDTO(Attachment attachment);
}