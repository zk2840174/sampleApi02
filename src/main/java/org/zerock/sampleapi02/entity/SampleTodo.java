package org.zerock.sampleapi02.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="tbl_diary")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"tags", "files"})
public class SampleTodo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="sid")
    private Long id;


}
