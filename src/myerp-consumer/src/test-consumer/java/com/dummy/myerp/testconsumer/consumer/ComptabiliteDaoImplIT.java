package com.dummy.myerp.testconsumer.consumer;

import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoImpl;
import com.dummy.myerp.model.bean.comptabilite.*;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void getEcritureComptableByRefTest_Notfound() {
        EcritureComptable newEC = null;
        Exception exception = assertThrows(NotFoundException.class, () -> dao.getEcritureComptableByRef("AC-2021/00001"));
        logger.info(exception.getMessage());

    }

    @Test
    public void insertEcritureComptable(){
        List<EcritureComptable> listEC =  dao.getListEcritureComptable();
        EcritureComptable newEC = new EcritureComptable();
        newEC.setLibelle("test");
        newEC.setDate(new Date());
        newEC.setJournal(new JournalComptable("AC", "Achat"));
        newEC.setReference("BB-2021/00001");
        newEC.setJournal(new JournalComptable("AC", "Achat"));
        dao.insertEcritureComptable(newEC);
        assertEquals((listEC.size()+1),dao.getListEcritureComptable().size());
        dao.deleteEcritureComptable(newEC.getId());
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
    public void deleteEcritureComptable() throws NotFoundException {
        EcritureComptable newEC = new EcritureComptable();
        newEC.setLibelle("test");
        newEC.setDate(new Date());
        newEC.setJournal(new JournalComptable("AC", "Achat"));
        newEC.setReference("BB-2021/00001");
        dao.insertEcritureComptable(newEC);
        List<EcritureComptable> listEC =  dao.getListEcritureComptable();
        dao.deleteEcritureComptable(newEC.getId());
        assertEquals((listEC.size()-1),dao.getListEcritureComptable().size());

    }

    @Test
    public void loadListLigneEcritureTest() throws NotFoundException {
        List<EcritureComptable> listEC = dao.getListEcritureComptable();
        Map<Integer,List<LigneEcritureComptable>> listLEC = new HashMap<>();
        EcritureComptable newEC = new EcritureComptable();

        for (EcritureComptable e: listEC) {
            listLEC.put(e.getId(),e.getListLigneEcriture());
        }

        listLEC.forEach((ecritureId,list) -> {
            newEC.setId(ecritureId);
            dao.loadListLigneEcriture(newEC);
            assertEquals(newEC.getListLigneEcriture().size(),list.size());
        });

    }


    @Test
    public void getSequenceEcritureComptableByCodeAndYear_Null(){
        assertNull(dao.getSequenceEcritureComptableByCodeAndYear("ZZ",2200));
    }

    @Test
    public void getSequenceEcritureComptableByCodeAndYear_NotNull(){
        assertNotNull(dao.getSequenceEcritureComptableByCodeAndYear("AC",2016));
    }

    @Test
    public void updateSequenceEcritureComptable(){
        SequenceEcritureComptable newSEC = new SequenceEcritureComptable();
        newSEC = dao.getSequenceEcritureComptableByCodeAndYear("AC", 2016);
        int oldValue = newSEC.getDerniereValeur();

        newSEC.setDerniereValeur(123);
        dao.updateSequenceEcritureComptable(newSEC);
        assertEquals(newSEC.getDerniereValeur(),123);

        newSEC.setDerniereValeur(oldValue);
        dao.updateSequenceEcritureComptable(newSEC);
        assertEquals(newSEC.getDerniereValeur(),oldValue);

    }

    @Test
    public void insertSequenceEcritureComptable(){
        SequenceEcritureComptable newSEC= new SequenceEcritureComptable();
        newSEC.setCodeJournal("AC");
        newSEC.setAnnee(2020);
        newSEC.setDerniereValeur(0);

        dao.insertSequenceEcritureComptable(newSEC);
        assertNotNull(dao.getSequenceEcritureComptableByCodeAndYear("AC",2020));

        dao.deleteSequenceEcritureComptable(dao.getSequenceEcritureComptableByCodeAndYear("AC",2020));
    }

    @Test
    public void deleteSequenceEcritureComptable(){
        SequenceEcritureComptable newSEC= new SequenceEcritureComptable();
        newSEC.setCodeJournal("AC");
        newSEC.setAnnee(2020);
        newSEC.setDerniereValeur(1);
        dao.insertSequenceEcritureComptable(newSEC);
        dao.deleteSequenceEcritureComptable(newSEC);
        assertNull(dao.getSequenceEcritureComptableByCodeAndYear("AC",2020));

    }

}
