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
    }


    @Test
    public void addStudentTest(){
        String id = "999";
        String name = "Nume1";
        String email = "email@mail.com";
        int grupa = 936;
        Student student = new  Student(id, name, grupa, email);

        service.addStudent(student);

        assert (studentXMLRepository.findOne(id).getNume().equals(name));
    }

    @Test(expected = ValidationException.class)
    public void addInvalidStudentTest(){
        String id = "100";
        String name = "Nume1";
        String email = "email@mail.com";
        int grupa = -936;
        Student student = new  Student(id, name, grupa, email);
        service.addStudent(student);
    }

}
