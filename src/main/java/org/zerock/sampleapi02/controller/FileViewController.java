package org.zerock.sampleapi02.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.nio.file.Files;

@Controller
@Log4j2
public class FileViewController {

    @Value("${org.zerock.upload.path}")// import 시에 springframework으로 시작하는 Value
    private String uploadPath;

    @GetMapping("/view/{fileName}")
    public ResponseEntity<byte[]> viewFileGET(@PathVariable String fileName){

        Resource resource = null;
        HttpHeaders headers = new HttpHeaders();;

        try{
            resource = new FileSystemResource(uploadPath+ File.separator + fileName);
            String resourceName = resource.getFilename();
            headers.add("Content-Type", Files.probeContentType( resource.getFile().toPath() ));
            return ResponseEntity.ok().headers(headers).body(resource.getInputStream().readAllBytes());

        } catch(Throwable e){
            log.error("==========================");
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }


}
