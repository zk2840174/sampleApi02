package org.zerock.sampleapi02.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class SampleTodoDTO {

    private Long id;

    private String title;

    private String writer;

    private LocalDate dueDate;

    private boolean complete;

    private MultipartFile[] files;

}
