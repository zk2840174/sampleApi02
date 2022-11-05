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


    @GetMapping("/list")
    public PageResponseDTO<SampleTodoViewDTO> list(PageRequestDTO pageRequestDTO){

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return sampleTodoService.list(pageRequestDTO);

    }


    @GetMapping("/{id}")
    public SampleTodoViewDTO read(@PathVariable Long id){

        log.info("ID: " + id);

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return sampleTodoService.read(id);
    }

    @PostMapping( value = "/" , consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public SampleTodoViewDTO register(SampleTodoDTO sampleTodoDTO){

        log.info("-----------------------------------------");
        log.info(sampleTodoDTO);

        //file upload
        MultipartFile[] multipartFiles = sampleTodoDTO.getFiles();

        SampleTodoViewDTO result = sampleTodoService.add(sampleTodoDTO);

        return result;
    }

    @DeleteMapping("/{id}")
    public Map<String, String> removeAll( @PathVariable("id")Long id ) {

        sampleTodoService.remove(id);

        return Map.of("result", "success");

    }

    @PostMapping(value ="/modify/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public SampleTodoViewDTO modify( @PathVariable("id") Long id,  SampleTodoDTO sampleTodoDTO ){

        log.info("modify......................" + id);
        log.info(sampleTodoDTO);

        return sampleTodoService.modify(sampleTodoDTO);
    }

}
