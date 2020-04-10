package tests;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.Before;
import org.junit.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;

import java.time.LocalDate;

import static junit.framework.TestCase.assertNull;

public class IntegrationTest {
    StudentXMLRepo studentXMLRepository;
    TemaXMLRepo temaXMLRepository;
    NotaXMLRepo notaXMLRepository;
    Service service;

    @Before
    public void before(){
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        String filenameStudent = "fisiere/Studenti.xml";
        String filenameTema = "fisiere/Teme.xml";
        String filenameNota = "fisiere/Note.xml";

        studentXMLRepository = new StudentXMLRepo(filenameStudent);
        temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        notaXMLRepository = new NotaXMLRepo(filenameNota);
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }

    @Test
    public void addStudentTest() {
        studentXMLRepository.delete("999");

        String id = "999";
        String name = "Nume1";
        String email = "email@mail.com";
        int grupa = 936;
        Student student = new  Student(id, name, grupa, email);

        assertNull(service.addStudent(student));

        assert (studentXMLRepository.findOne(id).getNume().equals(name));
    }

    @Test
    public void addAssignmentTest() {
        temaXMLRepository.delete("1");

        String nrTema = "1";
        String descriere = "desc";
        int deadline = 2;
        int primire = 1;
        Tema tema = new Tema(nrTema, descriere, deadline, primire);

        assertNull(service.addTema(tema));

        assert (temaXMLRepository.findOne(nrTema).getDescriere().equals(descriere));
    }

    @Test
    public void addGradeTest() {
        notaXMLRepository.delete("1");

        String id = "1";
        String idStudent = "1";
        String idTema = "5";
        double nota = 10;
        LocalDate data = LocalDate.now();
        Nota grade = new Nota(id, idStudent, idTema, nota, data);

        System.out.println(service.addNota(grade, "Feedback"));
    }

    @Test
    public void bigBangTest() {
        addStudentTest();
        addAssignmentTest();
        addGradeTest();
    }
}
