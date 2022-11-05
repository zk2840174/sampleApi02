package org.zerock.sampleapi02.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.sampleapi02.dto.UploadResultDTO;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Component
public class UploadFileUtil {

    @Value("${org.zerock.upload.path}")// import 시에 springframework으로 시작하는 Value
    private String uploadPath;


    public void deleteFiles(List<String> fileNames) {

        for (String fileName : fileNames) {
            File file = new File(uploadPath, fileName);
            file.delete();
        }//end file
    }

    public List<UploadResultDTO> uploadFiles(MultipartFile[] multipartFiles) {

        if(multipartFiles == null || multipartFiles.length < 1) {
            return null;
        }


        return Arrays.stream(multipartFiles).map(multipartFile ->  {

            UUID uuid = UUID.randomUUID();
            String fileName = multipartFile.getOriginalFilename();

            String type = multipartFile.getContentType();


            try {
                FileCopyUtils.copy(multipartFile.getBytes(), new File(uploadPath, uuid.toString()+"_"+ fileName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            UploadResultDTO uploadResultDTO = UploadResultDTO.builder()
                    .uuid(uuid.toString())
                    .fileName(fileName)
                    .type(type)
                    .build();

            return uploadResultDTO;
        }).collect(Collectors.toList());

    }
}
