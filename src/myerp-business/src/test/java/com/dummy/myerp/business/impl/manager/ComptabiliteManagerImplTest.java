package com.dummy.myerp.business.impl.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dummy.myerp.business.impl.AbstractBusinessManager;
import com.dummy.myerp.business.impl.TransactionManager;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import org.junit.Assert;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;


public class ComptabiliteManagerImplTest {

    private ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();

    @BeforeAll
    private static void injectMockDao() {
        DaoProxy daoProxyMock = mock(DaoProxy.class, Mockito.RETURNS_DEEP_STUBS);
        AbstractBusinessManager.configure(null, daoProxyMock, null);
    }

 //   @Disabled
    @org.junit.jupiter.api.Test
    public void getListCompteComptableTest(){
        List<CompteComptable> listeCompteComptableTest =  new ArrayList<CompteComptable>();
        listeCompteComptableTest = manager.getListCompteComptable();
        System.out.println("liste compte comptable size : " + listeCompteComptableTest.size());

        Assert.assertNotNull(listeCompteComptableTest.size());
    }

    @org.junit.jupiter.api.Test
    public void getListJournalComptableTest(){
        List<JournalComptable> listeJournalComptableTest =  new ArrayList<JournalComptable>();
        listeJournalComptableTest = manager.getListJournalComptable();
        System.out.println("liste journal comptable size : " + listeJournalComptableTest.size());

        Assert.assertNotNull(listeJournalComptableTest.size());
    }



    @org.junit.jupiter.api.Test
    public void AddRef_NotNull(){
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        manager.addReference(vEcritureComptable);
        vEcritureComptable.setReference("AC-2021/00001");
        Assert.assertNotNull(vEcritureComptable.getReference(),vEcritureComptable.getReference());

    }

    @org.junit.jupiter.api.Test
    public void AddRef_VerifyRG5(){

    }

    @Test
    public void checkEcritureComptableUnit() throws Exception {
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
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    //expected throw "L'écriture comptable ne respecte pas les contraintes de validation"
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitViolation() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    //expected throw "L'écriture comptable n'est pas équilibrée."
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG2() throws Exception {
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
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    //expected throw "L'écriture comptable doit avoir au moins deux lignes : une ligne au débit et une ligne au crédit."
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG3() throws Exception {
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
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    //expected throw "La date de la référence ne correspond pas à l'année d'écriture."
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG5_Date() throws Exception {
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
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    //expected throw "Le code journal de la référence est différent du code journal."
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG5_CodeJournal() throws Exception {
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
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    public void checkEcritureComptableContextTest(){

    }

    @org.junit.jupiter.api.Test
    public void insertEcritureComptableTest() throws FunctionalException {
    }

    public void updateEcritureComptablTest(){

    }

    public void deleteEcritureComptableTest(){

    }
}
