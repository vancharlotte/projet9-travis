package com.dummy.myerp.testconsumer.consumer;

import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoImpl;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        EcritureComptable newEC = new EcritureComptable();
        newEC = dao.getEcritureComptableByRef("AC-2016/00001");
        assertEquals(-1,newEC.getId());

    }


    @Test
    public void insertEcritureComptable(){
    }

    @Test
    public void updateEcritureComptable(){}

    @Test
    public void deleteEcritureComptable(){}

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
