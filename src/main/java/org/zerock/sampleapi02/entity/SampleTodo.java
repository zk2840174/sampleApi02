package org.zerock.sampleapi02.entity;


import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name="tbl_sample_todo")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"files"})
public class SampleTodo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="sid")
    private Long id;

    private String title;

    private String writer;

    private LocalDate dueDate;

    private boolean complete;

    @ElementCollection
    @CollectionTable(name = "tbl_sample_todo_filess")
    @Builder.Default
    @Fetch(FetchMode.JOIN)
    @BatchSize(size = 100)
    private Set<SampleTodoFile> files = new TreeSet<>();

    public void change(String title, LocalDate dueDate, boolean complete, Set<SampleTodoFile> files){

        this.title = title;
        this.dueDate = dueDate;
        this.complete = complete;
        this.files = files;
    }


}
