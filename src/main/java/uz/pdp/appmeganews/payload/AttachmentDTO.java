package uz.pdp.appmeganews.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AttachmentDTO {
    private String name;
    private String path;
    private String contentType;
    private String originalName;
    private Long size;
}
