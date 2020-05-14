package tests.TopDown;

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

public class TopDownIntegrationTest {
    StudentXMLRepo studentXMLRepository;
    TemaXMLRepo temaXMLRepository;
    NotaXMLRepo notaXMLRepository;
    Service service;
    Service mockService;

    @Before
    public void mockSetUp(){
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        String filenameStudent = "fisiere/Studenti.xml";
        String filenameTema = "fisiere/Teme.xml";
        String filenameNota = "fisiere/Note.xml";

        StudentXMLRepo studentXMLRepositoryMock = new StudentXMLRepoMock(filenameStudent); // use the mock repo
        TemaXMLRepo temaXMLRepositoryMock = new TemaXMLRepoMock(filenameTema); // use the mock repo
        NotaValidator notaValidator = new NotaValidator(studentXMLRepositoryMock, temaXMLRepositoryMock);
        notaXMLRepository = new NotaXMLRepo(filenameNota);
        mockService = new Service(studentXMLRepositoryMock, studentValidator, temaXMLRepositoryMock, temaValidator, notaXMLRepository, notaValidator);
    }

    @Before
    public void setUp(){
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
        Student student = new Student(id, name, grupa, email);

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
        service.deleteNota("5");

        // To add a grade, the service.addStudent and service.addTema should be mocked
        Tema tema = new Tema("5", "descriere", 14, 6);
        mockService.addTema(tema); // use the mocked tema repository
        Student student = new Student("5", "Nume", 936, "email@mail.com");
        mockService.addStudent(student); // use the mocked student repository

        Nota grade = new Nota("5", "5", "5", 10, LocalDate.now());

        assert(mockService.addNota(grade, "Feedback") == 10);
    }

    @Test
    public void topDownTest() {
        addGradeTest();
        addStudentTest();
        addAssignmentTest();
    }
}
