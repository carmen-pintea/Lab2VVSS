package tests;

import domain.Student;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;
import view.UI;

import static junit.framework.TestCase.assertNull;

public class Test1 {

    StudentXMLRepo studentXMLRepository;
    Service service;

    @Before
    public void before(){
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        String filenameStudent = "fisiere/Studenti.xml";
        String filenameTema = "fisiere/Teme.xml";
        String filenameNota = "fisiere/Note.xml";

        studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);

        for (Student e : studentXMLRepository.findAll()) {
            studentXMLRepository.delete(e.getID());
        }
    }


    @Test
    public void addStudentTest(){
        String id = "999";
        String name = "Nume1";
        String email = "email@mail.com";
        int grupa = 936;
        Student student = new  Student(id, name, grupa, email);

        assertNull(service.addStudent(student));

        assert (studentXMLRepository.findOne(id).getNume().equals(name));
    }

    @Test
    public void addSameStudentTest(){
        String id = "999";
        String name = "Nume1";
        String email = "email@mail.com";
        int grupa = 936;
        Student student = new  Student(id, name, grupa, email);

        assertNull(service.addStudent(student));
        Student std = service.addStudent(student);
        assert(std.getID().equals(id));

        assert (studentXMLRepository.findOne(id).getNume().equals(name));
    }

    @Test(expected = ValidationException.class)
    public void addInvalidGroupStudentTest1(){
        String id = "100";
        String name = "Nume1";
        String email = "email@mail.com";
        int grupa = -936;
        Student student = new  Student(id, name, grupa, email);
        service.addStudent(student);
    }

    @Test(expected = ValidationException.class)
    public void addInvalidEmptyNameStudentTest(){
        String id = "101";
        String name = "";
        String email = "email@mail.com";
        int grupa = 936;
        Student student = new  Student(id, name, grupa, email);
        service.addStudent(student);
    }

    @Test(expected = ValidationException.class)
    public void addInvalidNullNameStudentTest(){
        String id = "102";
        String name = null;
        String email = "email@mail.com";
        int grupa = 936;
        Student student = new  Student(id, name, grupa, email);
        service.addStudent(student);
    }

    @Test(expected = ValidationException.class)
    public void addInvalidNullIdStudentTest(){
        String id = null;
        String name = "name1";
        String email = "email@mail.com";
        int grupa = 936;
        Student student = new  Student(id, name, grupa, email);
        service.addStudent(student);
    }

    @Test(expected = ValidationException.class)
    public void addInvalidEmptyIdStudentTest(){
        String id = "";
        String name = "name1";
        String email = "email@mail.com";
        int grupa = 936;
        Student student = new  Student(id, name, grupa, email);
        service.addStudent(student);
    }

    @Test(expected = ValidationException.class)
    public void addInvalidNullEmailStudentTest(){
        String id = "103";
        String name = "name1";
        String email = null;
        int grupa = 936;
        Student student = new  Student(id, name, grupa, email);
        service.addStudent(student);
    }

    @Test(expected = ValidationException.class)
    public void addInvalidEmptyEmailStudentTest(){
        String id = "103";
        String name = "name1";
        String email = "";
        int grupa = 936;
        Student student = new  Student(id, name, grupa, email);
        service.addStudent(student);
    }

    // BVA

    @Test(expected = ValidationException.class)
    public void addStudentBVA1Test() {
        String id = "999";
        String name = "Nume1";
        String email = "email@mail.com";
        int grupa = -1;
        Student student = new  Student(id, name, grupa, email);

        service.addStudent(student);
    }

    @Test
    public void addStudentBVA2Test() {
        String id = "999";
        String name = "Nume1";
        String email = "email@mail.com";
        int grupa = 0;
        Student student = new  Student(id, name, grupa, email);

        assertNull(service.addStudent(student));
    }

    @Test
    public void addStudentBVA3Test() {
        String id = "999";
        String name = "Nume1";
        String email = "email@mail.com";
        int grupa = 1;
        Student student = new  Student(id, name, grupa, email);

        assertNull(service.addStudent(student));
    }

    @Test
    public void addStudentBVA4Test() {
        String id = "999";
        String name = "Nume1";
        String email = "email@mail.com";
        int grupa = 998;
        Student student = new  Student(id, name, grupa, email);

        assertNull(service.addStudent(student));
    }

    @Test
    public void addStudentBVA5Test() {
        String id = "999";
        String name = "Nume1";
        String email = "email@mail.com";
        int grupa = 999;
        Student student = new  Student(id, name, grupa, email);

        assertNull(service.addStudent(student));
    }

    @Test(expected = ValidationException.class)
    public void addStudentBVA6Test() {
        String id = "999";
        String name = "Nume1";
        String email = "email@mail.com";
        int grupa = 1000;
        Student student = new  Student(id, name, grupa, email);

        service.addStudent(student);
    }
}
