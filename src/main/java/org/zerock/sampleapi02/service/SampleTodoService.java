package org.zerock.sampleapi02.service;

import org.zerock.sampleapi02.dto.*;
import org.zerock.sampleapi02.entity.SampleTodo;
import org.zerock.sampleapi02.entity.SampleTodoFile;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public interface SampleTodoService {

    SampleTodoViewDTO add(SampleTodoDTO sampleTodoDTO);

    SampleTodoViewDTO read(Long id);

    PageResponseDTO<SampleTodoViewDTO> list(PageRequestDTO pageRequestDTO);

    void remove(Long id);

    SampleTodoViewDTO modify(SampleTodoDTO sampleTodoDTO);


    default SampleTodo dtoToEntity(SampleTodoDTO sampleTodoDTO, List<UploadResultDTO> uploadResultDTOS){

        Set<SampleTodoFile> fileSet = null;

        if(uploadResultDTOS!= null && uploadResultDTOS.size() > 0){

            fileSet = new TreeSet<>();

            for (int i = 0; i < uploadResultDTOS.size() ; i++) {

                UploadResultDTO uploadResultDTO = uploadResultDTOS.get(i);

                fileSet.add(SampleTodoFile.builder()
                                .idx(i)
                                .uuid(uploadResultDTO.getUuid())
                                .type(uploadResultDTO.getType())
                                .fileName(uploadResultDTO.getFileName())
                        .build());
            }

        }


        SampleTodo entity = SampleTodo.builder()
                .id(sampleTodoDTO.getId())
                .title(sampleTodoDTO.getTitle())
                .writer(sampleTodoDTO.getWriter())
                .dueDate(sampleTodoDTO.getDueDate())
                .complete(sampleTodoDTO.isComplete())
                .files(fileSet)
                .build();

        return entity;
    }

    default SampleTodoViewDTO entityToDto(SampleTodo sampleTodo){

        List<UploadResultDTO> uploadResultDTOList = null;

        if(sampleTodo.getFiles().size() > 0){

            uploadResultDTOList = sampleTodo.getFiles().stream().map(todoFile -> {

                UploadResultDTO uploadResultDTO = UploadResultDTO.builder()
                        .uuid(todoFile.getUuid())
                        .type(todoFile.getType())
                        .fileName(todoFile.getFileName())
                        .build();

                return uploadResultDTO;

            }).collect(Collectors.toList());

        }

        SampleTodoViewDTO dto = SampleTodoViewDTO.builder()
                .id(sampleTodo.getId())
                .title( sampleTodo.getTitle())
                .writer( sampleTodo.getWriter())
                .dueDate( sampleTodo.getDueDate())
                .complete( sampleTodo.isComplete())
                .files( uploadResultDTOList)
                .build();

        return dto;
    }

}
