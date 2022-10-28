package org.zerock.sampleapi02.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.sampleapi02.entity.SampleTodo;

public interface SampleTodoRepository extends JpaRepository<SampleTodo, Long> {



}
