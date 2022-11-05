package org.zerock.sampleapi02.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.sampleapi02.dto.*;
import org.zerock.sampleapi02.entity.SampleTodo;
import org.zerock.sampleapi02.entity.SampleTodoFile;
import org.zerock.sampleapi02.repository.SampleTodoRepository;
import org.zerock.sampleapi02.util.UploadFileUtil;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class SampleTodoServiceImpl implements SampleTodoService{

    private final SampleTodoRepository sampleTodoRepository;
    private final UploadFileUtil uploadFileUtil;

    @Override
    public SampleTodoViewDTO add(SampleTodoDTO sampleTodoDTO) {

        //file upload
        List<UploadResultDTO> uploadResultDTOList = uploadFileUtil.uploadFiles(sampleTodoDTO.getFiles());

        SampleTodo sampleTodo = dtoToEntity(sampleTodoDTO, uploadResultDTOList);

        SampleTodo result = sampleTodoRepository.save(sampleTodo);

        return entityToDto(result);
    }


    @Override
    public SampleTodoViewDTO read(Long id) {

        Optional<SampleTodo> result = sampleTodoRepository.findById(id);

        SampleTodo sampleTodo = result.orElseThrow();

        return entityToDto(sampleTodo);
    }


    @Override
    public PageResponseDTO<SampleTodoViewDTO> list(PageRequestDTO pageRequestDTO) {

        Page<SampleTodo> result = sampleTodoRepository.findAll(pageRequestDTO.getPageable(Sort.by("id").descending()));

        long totalCount = result.getTotalElements();

        List<SampleTodo> list = result.getContent();

        List<SampleTodoViewDTO> dtoList = list.stream().map(todo -> entityToDto(todo)).collect(Collectors.toList());

        return new PageResponseDTO<>(pageRequestDTO, dtoList, (int)totalCount);
    }

    @Override
    public void remove(Long id) {

        List<String> fileNames = getOldFiles(id);
        //database delete...
        sampleTodoRepository.deleteById(id);

        uploadFileUtil.deleteFiles(fileNames);

    }

    private List<String> getOldFiles(Long id){

        Optional<SampleTodo> result = sampleTodoRepository.findById(id);

        SampleTodo todo = result.orElseThrow();

        Set<SampleTodoFile> fileSet = todo.getFiles();

        List<String> fileNames = fileSet.stream().map(sampleTodoFile ->  sampleTodoFile.getUuid()+"_"+ sampleTodoFile.getFileName()).collect(Collectors.toList());

        return fileNames;

    }


    @Override
    public SampleTodoViewDTO modify(SampleTodoDTO sampleTodoDTO) {

        Optional<SampleTodo> result = sampleTodoRepository.findById(sampleTodoDTO.getId());

        SampleTodo currentTodo = result.orElseThrow();

        //old files
        List<String> oldFiles = currentTodo.getFiles().stream().map(sampleTodoFile ->  sampleTodoFile.getUuid()+"_"+ sampleTodoFile.getFileName()).collect(Collectors.toList());

        //upload new files
        List<UploadResultDTO> uploadResultDTOList = uploadFileUtil.uploadFiles(sampleTodoDTO.getFiles());

        //dto To entity
        SampleTodo tobeTodo = dtoToEntity(sampleTodoDTO, uploadResultDTOList);

        currentTodo.change(tobeTodo.getTitle(), tobeTodo.getDueDate(),tobeTodo.isComplete(),tobeTodo.getFiles());

        //database update
        sampleTodoRepository.save(currentTodo);

        //delete old files
        uploadFileUtil.deleteFiles(oldFiles);

        return entityToDto(tobeTodo);
    }
}











