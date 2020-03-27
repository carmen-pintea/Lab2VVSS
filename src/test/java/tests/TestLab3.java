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
    }


    @Test
    public void addValidAssignmentTest(){
        String nrTema = "10";
        String descriere = "LabAssignment";
        int deadline = 10;
        int primire = 6;
        Tema tema = new  Tema(nrTema, descriere, deadline, primire);

        service.addTema(tema);

        assert (temaXMLRepository.findOne(nrTema).getDescriere().equals(descriere));
    }

    @Test(expected = ValidationException.class)
    public void addInvalidAssignmentTest(){
        String nrTema = "10";
        String descriere = "LabAssignment";
        int deadline = 20;
        int primire = 6;
        Tema tema = new  Tema(nrTema, descriere, deadline, primire);

        service.addTema(tema);
    }
}
