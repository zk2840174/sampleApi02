package org.zerock.sampleapi02.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;


@Data
@Builder
public class SampleTodoViewDTO {

    private Long id;

    private String title;

    private String writer;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate dueDate;

    private boolean complete;

    private List<UploadResultDTO> files;
}
