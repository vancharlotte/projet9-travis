package com.dummy.myerp.testconsumer.consumer;

import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoImpl;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ComptabiliteDaoImplIT {

    private static final Logger logger = LoggerFactory.getLogger(ComptabiliteDaoImplIT.class);

    private ComptabiliteDaoImpl dao = new ComptabiliteDaoImpl();

    @BeforeAll
    public static void initSpring(){
        SpringRegistry.init();

    }

    @Test
    public void getListCompteComptableTest(){
        List<CompteComptable> listeCompteComptableTest =  dao.getListCompteComptable();
        assertNotNull(listeCompteComptableTest.size());
    };

    @Test
    public void getListJournalComptableTest(){
        List<JournalComptable> listeJournalComptableTest =  dao.getListJournalComptable();
        assertNotNull(listeJournalComptableTest.size());

    };

    @Test
    public void getListEcritureComptableTest() {
        List<EcritureComptable> listeEcritureComptableTest =  dao.getListEcritureComptable();
        assertNotNull(listeEcritureComptableTest.size());
    }

    @Test
    public void getEcritureComptableTest() throws NotFoundException {
        EcritureComptable newEC = new EcritureComptable();
        newEC = dao.getEcritureComptable(-1);
        assertEquals("AC-2016/00001",newEC.getReference());
    }

    @Test
    public void getEcritureComptableByRefTest() throws NotFoundException {
        EcritureComptable newEC = dao.getEcritureComptableByRef("AC-2016/00001");
        assertEquals(-1,newEC.getId());
    }

    @Test
    public void getEcritureComptableByRefTest_Notfound() throws NotFoundException {
        EcritureComptable newEC = dao.getEcritureComptableByRef("AC-2021/00001");
        Exception exception = assertThrows(FunctionalException.class, () -> dao.updateEcritureComptable(newEC));
        logger.info(exception.getMessage());
    }

    @Test
    public void insertEcritureComptable(){
        List<EcritureComptable> listEC =  dao.getListEcritureComptable();
        EcritureComptable newEC = new EcritureComptable();
        newEC.setId(1);
        newEC.setReference("BB-2021/00001");
        newEC.setJournal(new JournalComptable("BB","Test"));
        dao.insertEcritureComptable(newEC);
        assertEquals((listEC.size()+1),dao.getListEcritureComptable().size());
        dao.deleteEcritureComptable(1);
    }

    @Test
    public void updateEcritureComptable() throws NotFoundException {
        EcritureComptable newEC = dao.getEcritureComptable(-1);
        String oldLibelle = newEC.getLibelle();
        newEC.setLibelle("TestUpdateEC");
        dao.updateEcritureComptable(newEC);
        assertEquals("TestUpdateEC",dao.getEcritureComptable(-1).getLibelle());

        newEC.setLibelle(oldLibelle);
        dao.updateEcritureComptable(newEC);
        assertEquals(oldLibelle,dao.getEcritureComptable(-1).getLibelle());


    }

    @Test
    public void deleteEcritureComptable(){
        EcritureComptable newEC = new EcritureComptable();
        newEC.setId(1);
        newEC.setReference("BB-2021/00001");
        newEC.setJournal(new JournalComptable("BB","Test"));
        dao.insertEcritureComptable(newEC);
        List<EcritureComptable> listEC =  dao.getListEcritureComptable();
        dao.deleteEcritureComptable(1);
        assertEquals((listEC.size()-1),dao.getListEcritureComptable().size());

    }

    @Test
    public void loadListLigneEcritureTest(){}

    @Test
    public void insertListLigneEcritureComptable(){}


    @Test
    public void deleteListLigneEcritureComptable(){}

    @Test
    public void getSequenceEcritureComptableByCodeAndYear(){}

    @Test
    public void updateSequenceEcritureComptable(){}

    @Test
    public void insertSequenceEcritureComptable(){}

    @Test
    public void deleteSequenceEcritureComptable(){}

}
