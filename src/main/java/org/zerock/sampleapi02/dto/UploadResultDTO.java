package org.zerock.sampleapi02.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadResultDTO {

    private String uuid;
    private String savePath;
    private String fileName;

}
