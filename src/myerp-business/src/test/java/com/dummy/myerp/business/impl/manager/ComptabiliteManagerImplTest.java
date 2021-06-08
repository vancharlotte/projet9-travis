package com.dummy.myerp.business.impl.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.dummy.myerp.business.contrat.BusinessProxy;
import com.dummy.myerp.business.impl.AbstractBusinessManager;
import com.dummy.myerp.business.impl.TransactionManager;
import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.model.bean.comptabilite.*;
import com.sun.deploy.cache.BaseLocalApplicationProperties;
import org.junit.Assert;
import com.dummy.myerp.technical.exception.FunctionalException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ComptabiliteManagerImplTest {

    private ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();

    @Mock
    private ComptabiliteDao comptabiliteDao;

    @Mock
    private DaoProxy daoProxy;

    @Mock
    private BusinessProxy businessProxy;

    //mock la classe daoproxy
    @BeforeAll
    private static void injectMockDao() {
        DaoProxy daoProxyMock = mock(DaoProxy.class, Mockito.RETURNS_DEEP_STUBS);
        AbstractBusinessManager.configure(null, daoProxyMock, null);

        SequenceEcritureComptable vSEQ = new SequenceEcritureComptable();
        vSEQ.setCodeJournal("AC");
        vSEQ.setAnnee(2021);
        vSEQ.setDerniereValeur(00001);
        when(daoProxyMock.getComptabiliteDao().getSequenceEcritureComptableByCodeAndYear("AC",2021)).thenReturn(vSEQ);
        when(daoProxyMock.getComptabiliteDao().getSequenceEcritureComptableByCodeAndYear("AA",2021)).thenReturn(null);

    }


    @Test
    public void getListCompteComptableTest(){
        List<CompteComptable> listeCompteComptableTest =  new ArrayList<CompteComptable>();
        listeCompteComptableTest = manager.getListCompteComptable();

        Assert.assertNotNull(listeCompteComptableTest.size());
    }

    @Test
    public void getListJournalComptableTest(){
        List<JournalComptable> listeJournalComptableTest =  new ArrayList<JournalComptable>();
        listeJournalComptableTest = manager.getListJournalComptable();

        Assert.assertNotNull(listeJournalComptableTest.size());
    }



    @Test
    public void AddRef_SequenceNotNull(){
        EcritureComptable vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setReference("AC-2021/00005");

        manager.addReference(vEcritureComptable);

        Assert.assertNotNull(vEcritureComptable.getReference());
        Assert.assertEquals("AC-2021/2",vEcritureComptable.getReference());



    }

    @Test
    public void AddRef_SequenceNull(){
        EcritureComptable vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AA", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setReference(null);

        manager.addReference(vEcritureComptable);

        Assert.assertNotNull(vEcritureComptable.getReference());


    }

    //expect no exception
    @Test
    public void checkEcritureComptableUnit() {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setReference("AC-2021/00001");
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));
    }

    //expected throw "L'écriture comptable ne respecte pas les contraintes de validation"
    @Test
    public void checkEcritureComptableUnitViolation() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        assertThrows(FunctionalException.class, () -> manager.checkEcritureComptableUnit(vEcritureComptable));
    }

    //expected throw "L'écriture comptable n'est pas équilibrée."
    @Test
    public void checkEcritureComptableUnitRG2() {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setReference("AC-2021/00001");
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(1234)));
        assertThrows(FunctionalException.class, () -> manager.checkEcritureComptableUnit(vEcritureComptable));
    }

    //expected throw "L'écriture comptable doit avoir au moins deux lignes : une ligne au débit et une ligne au crédit."
    @Test
    public void checkEcritureComptableUnitRG3() {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setReference("AC-2021/00001");
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        assertThrows(FunctionalException.class, () -> manager.checkEcritureComptableUnit(vEcritureComptable));
    }

    //expected throw "La date de la référence ne correspond pas à l'année d'écriture."
    @Test
    public void checkEcritureComptableUnitRG5_Date() {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setReference("AC-2020/00001");
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, null, new BigDecimal(123)));
        assertThrows(FunctionalException.class, () -> manager.checkEcritureComptableUnit(vEcritureComptable));
    }

    //expected throw "Le code journal de la référence est différent du code journal."
    @Test
    public void checkEcritureComptableUnitRG5_CodeJournal(){
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setReference("QQ-2021/00001");
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, null, new BigDecimal(123)));
        assertThrows(FunctionalException.class, () -> manager.checkEcritureComptableUnit(vEcritureComptable));
    }

    @Test
    public void checkEcritureComptableContextTest() {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setId(1);
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setReference("AC-2016/00001");
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, null, new BigDecimal(123)));
        assertThrows(FunctionalException.class, () -> manager.checkEcritureComptableContext(vEcritureComptable));


    }

    @Test
    public void insertEcritureComptableTest() throws FunctionalException {
    }

    public void updateEcritureComptablTest(){

    }

    public void deleteEcritureComptableTest(){

    }
}
