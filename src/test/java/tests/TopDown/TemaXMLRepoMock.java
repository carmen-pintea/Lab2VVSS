package tests.TopDown;

import domain.Tema;
import repository.TemaXMLRepo;

public class TemaXMLRepoMock extends TemaXMLRepo {
    /**
     * Class constructor
     *
     * @param filename - numele fisierului
     */
    private Tema tema;

    public TemaXMLRepoMock(String filename) {
        super(filename);
    }

    @Override
    public Tema findOne(String s) {
        return tema;
    }

    @Override
    public Tema save(Tema entity) {
        tema = entity;
        return null;
    }
}
