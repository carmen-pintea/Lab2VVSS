package tests;

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
import validation.ValidationException;

public class TestLab3 {
    TemaXMLRepo temaXMLRepository;
    Service service;

    @Before
    public void before(){
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        String filenameStudent = "fisiere/Studenti.xml";
        String filenameTema = "fisiere/Teme.xml";
        String filenameNota = "fisiere/Note.xml";

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
        temaXMLRepository.delete("1");
    }


    @Test
    public void addValidAssignment(){
        String nrTema = "1";
        String descriere = "desc";
        int deadline = 2;
        int primire = 1;
        Tema tema = new Tema(nrTema, descriere, deadline, primire);

        Tema result = service.addTema(tema);
        assert(result == null);
        assert (temaXMLRepository.findOne(nrTema).getDescriere().equals(descriere));
    }

    @Test(expected = ValidationException.class)
    public void addNullIdAssignment(){
        String nrTema = null;
        String descriere = "desc";
        int deadline = 2;
        int primire = 1;
        Tema tema = new Tema(nrTema, descriere, deadline, primire);

        service.addTema(tema);
    }

    @Test(expected = ValidationException.class)
    public void addEmptyIdAssignment(){
        String nrTema = "";
        String descriere = "desc";
        int deadline = 2;
        int primire = 1;
        Tema tema = new  Tema(nrTema, descriere, deadline, primire);

        service.addTema(tema);
    }

    @Test(expected = ValidationException.class)
    public void addEmptyDescriptionAssignment(){
        String nrTema = "1";
        String descriere = "";
        int deadline = 2;
        int primire = 1;
        Tema tema = new  Tema(nrTema, descriere, deadline, primire);

        service.addTema(tema);
    }

    @Test(expected = ValidationException.class)
    public void addNegativeDeadlineAssignment(){
        String nrTema = "1";
        String descriere = "desc";
        int deadline = -1;
        int primire = 2;
        Tema tema = new  Tema(nrTema, descriere, deadline, primire);

        service.addTema(tema);
    }

    @Test(expected = ValidationException.class)
    public void addGreaterDeadlineAssignment(){
        String nrTema = "1";
        String descriere = "desc";
        int deadline = 15;
        int primire = 1;
        Tema tema = new  Tema(nrTema, descriere, deadline, primire);

        service.addTema(tema);
    }

    @Test(expected = ValidationException.class)
    public void addNegativeStartWeekAssignment(){
        String nrTema = "1";
        String descriere = "desc";
        int deadline = 2;
        int primire = -1;
        Tema tema = new  Tema(nrTema, descriere, deadline, primire);

        service.addTema(tema);
    }

    @Test(expected = ValidationException.class)
    public void addGreaterStartWeekAssignment(){
        String nrTema = "1";
        String descriere = "desc";
        int deadline = 2;
        int primire = 15;
        Tema tema = new  Tema(nrTema, descriere, deadline, primire);

        service.addTema(tema);
    }

    @Test
    public void addDuplicateAssignment(){
        String nrTema = "1";
        String descriere1 = "desc";
        String descriere2 = "desc2";
        int deadline = 2;
        int primire = 1;
        Tema tema1 = new  Tema(nrTema, descriere1, deadline, primire);
        Tema tema2 = new  Tema(nrTema, descriere2, deadline, primire);

        service.addTema(tema1);
        Tema result = service.addTema(tema2);

        assert (result.getID() == nrTema && descriere2.equals(result.getDescriere()));
    }
}
