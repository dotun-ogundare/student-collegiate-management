package com.scm.ftn.student;

import com.scm.ftn.student.exceptions.BadRequestException;
import com.scm.ftn.student.exceptions.StudentNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class) //
class StudentServiceTest {

    @Mock private StudentRepository studentRepository;
   // private AutoCloseable autoClosable;
    private StudentService underTest;

    @BeforeEach
    void setUp() {
      //  autoClosable = MockitoAnnotations.openMocks(this);
        underTest = new StudentService(studentRepository);
    }

   /* @AfterEach
    void tearDown() throws Exception {
        autoClosable.close();
    } */

    @Test
    void canGetAllStudents() {
        //when
        underTest.getAllStudents();

        //then
        verify(studentRepository).findAll(); //it verifies that .findAll() was invoked
    }

    @Test
    void canAddStudent() {
        //Given
        Student student = new Student(
                "Kamila",
                "kamila@gmail.com",
                Gender.FEMALE
        );

        //When
        underTest.addStudent(student);

        //then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentArgumentCaptor.capture());

        Student capturedStudent = studentArgumentCaptor.getValue();

        assertThat(capturedStudent).isEqualTo(student);
    }

    @Test
    void willThrowWhenEmailIsTaken() {
        //Given
        Student student = new Student(
                "Kamila",
                "kamila@gmail.com",
                Gender.FEMALE
        );

        given(studentRepository.selectExistsEmail(student.getEmail()))
                .willReturn(true);

        //When
        //then
        assertThatThrownBy(() -> underTest.addStudent(student))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Email " + student.getEmail() + " already exist");

        verify(studentRepository, never()).save(any());
    }

    @Test
    void canDeleteStudent() {
        // given
        long id = 10;
        given(studentRepository.existsById(id))
                .willReturn(true);
        // when
        underTest.deleteStudent(id);

        // then
        verify(studentRepository).deleteById(id);
    }

    @Test
    void willThrowWhenDeleteStudentNotFound() {
        // given
        long studentId = 10;
        given(studentRepository.existsById(studentId))
                .willReturn(false);
        // when
        // then
        assertThatThrownBy(() -> underTest.deleteStudent(studentId))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("Student with id " + studentId + "does not exists");

        verify(studentRepository, never()).deleteById(any());
    }
}