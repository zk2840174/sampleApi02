package org.zerock.sampleapi02.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.sampleapi02.dto.*;
import org.zerock.sampleapi02.service.SampleTodoService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/sampleTodos")
public class SampleTodoController {

    private final SampleTodoService sampleTodoService;

    @Value("${org.zerock.upload.path}")// import 시에 springframework으로 시작하는 Value
    private String uploadPath;


    @GetMapping("/list")
    public PageResponseDTO<SampleTodoViewDTO> list(PageRequestDTO pageRequestDTO){

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return sampleTodoService.list(pageRequestDTO);

    }


    @GetMapping("/{id}")
    public SampleTodoViewDTO read(@PathVariable Long id){

        log.info("ID: " + id);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return sampleTodoService.read(id);
    }

    @PostMapping( value = "/" , consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public SampleTodoViewDTO register(SampleTodoDTO sampleTodoDTO){

        log.info("-----------------------------------------");
        log.info(sampleTodoDTO);

        SampleTodoViewDTO result = null;

        //file upload
        MultipartFile[] multipartFiles = sampleTodoDTO.getFiles();

        if(multipartFiles != null && multipartFiles.length > 0){

            List<UploadResultDTO> uploadResultDTOS = uploadFiles(multipartFiles);
            result = sampleTodoService.add(sampleTodoDTO, uploadResultDTOS);
        }else {
            result = sampleTodoService.add(sampleTodoDTO, null);
        }

        return result;
    }

    private List<UploadResultDTO> uploadFiles(MultipartFile[] multipartFiles) {

        List<UploadResultDTO> uploadResultDTOS = new ArrayList<>();

        for (MultipartFile multiFile:multipartFiles) {

            String fileName = multiFile.getOriginalFilename();

            String uuidValue = UUID.randomUUID().toString();

            String savedName = uuidValue+"_"+fileName;

            File saveFile = new File(uploadPath, savedName );

            log.info(saveFile);

            try {
                FileCopyUtils.copy(multiFile.getBytes(), saveFile);

                uploadResultDTOS.add(new UploadResultDTO(uuidValue,"/", fileName));

            } catch (IOException e) {
                throw new RuntimeException(e);
            }//
        }
        return uploadResultDTOS;
    }


}
