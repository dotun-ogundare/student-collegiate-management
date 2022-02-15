package com.scm.ftn.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
public interface StudentRepository extends JpaRepository<Student, Long> {
}
