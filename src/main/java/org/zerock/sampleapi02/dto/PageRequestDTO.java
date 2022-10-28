package org.zerock.sampleapi02.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

    @Builder.Default
    private int page = 1;
    @Builder.Default
    private int size = 10;


    public Pageable getPageable(Sort sort){

        Pageable pageable = PageRequest.of(page -1, size, sort);

        return pageable;

    }


}
