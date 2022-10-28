package org.zerock.sampleapi02.entity;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class SampleTodoFile implements Comparable<SampleTodoFile>{

    private String uuid;
    private String savePath;
    private String fileName;
    private int idx;


    @Override
    public int compareTo(SampleTodoFile o) {
        return o.idx - this.idx;
    }
}
