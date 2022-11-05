package org.zerock.sampleapi02.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UploadResultDTO {

    private String uuid;
    private String fileName;
    private String type;

}
