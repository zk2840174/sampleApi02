package org.zerock.sampleapi02.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.sampleapi02.dto.*;
import org.zerock.sampleapi02.entity.SampleTodo;
import org.zerock.sampleapi02.repository.SampleTodoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class SampleTodoServiceImpl implements SampleTodoService{

    private final SampleTodoRepository sampleTodoRepository;

    @Override
    public SampleTodoViewDTO add(SampleTodoDTO sampleTodoDTO, List<UploadResultDTO> uploadResultDTOList) {

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
}
