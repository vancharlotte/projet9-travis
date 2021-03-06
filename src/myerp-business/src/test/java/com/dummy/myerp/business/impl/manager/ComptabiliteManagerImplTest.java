package com.dummy.myerp.business.impl.manager;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import com.dummy.myerp.business.contrat.BusinessProxy;
import com.dummy.myerp.business.impl.AbstractBusinessManager;
import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.model.bean.comptabilite.*;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



public class ComptabiliteManagerImplTest {

    private ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();


    @Mock
    private ComptabiliteDao comptabiliteDaoMock;

    @Mock
    private DaoProxy daoProxyMock;

    @Mock
    private BusinessProxy businessProxyMock;

    public static EcritureComptable vEC;

    public static SequenceEcritureComptable vSEQ;


    //mock la classe daoproxy
    @BeforeAll
    private static void injectMockDao() throws NotFoundException {
        DaoProxy daoProxyMock = mock(DaoProxy.class, Mockito.RETURNS_DEEP_STUBS);
        AbstractBusinessManager.configure(null, daoProxyMock, null);

        vEC = new EcritureComptable();
        vEC.setJournal(new JournalComptable("AA", "Achat"));
        vEC.setReference("AA-"+ Calendar.getInstance().get(Calendar.YEAR) +"/00001");
        vEC.setId(-1);
        vEC.setDate(new Date());
        vEC.setLibelle("Libelle");
        vEC.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEC.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, null, new BigDecimal(123)));

        vSEQ = new SequenceEcritureComptable();
        vSEQ.setCodeJournal("AA");
        vSEQ.setAnnee(Calendar.getInstance().get(Calendar.YEAR));
        vSEQ.setDerniereValeur(1);

        when(daoProxyMock.getComptabiliteDao().getSequenceEcritureComptableByCodeAndYear("AA", Calendar.getInstance().get(Calendar.YEAR))).thenReturn(vSEQ);
        when(daoProxyMock.getComptabiliteDao().getSequenceEcritureComptableByCodeAndYear("AC", Calendar.getInstance().get(Calendar.YEAR))).thenReturn(null);
        when(daoProxyMock.getComptabiliteDao().getEcritureComptable(-1)).thenReturn(vEC);
        when(daoProxyMock.getComptabiliteDao().getEcritureComptableByRef("AA-"+ Calendar.getInstance().get(Calendar.YEAR) +"/00001")).thenReturn(vEC);

    }
    //expect no exception
    @Test
    public void checkEcritureComptableTest() {
        vEC = new EcritureComptable();
        vEC.setJournal(new JournalComptable("AA", "Achat"));
        vEC.setReference("AA-"+ Calendar.getInstance().get(Calendar.YEAR) +"/00001");
        vEC.setId(-1);
        vEC.setDate(new Date());
        vEC.setLibelle("Libelle");
        vEC.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEC.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, null, new BigDecimal(123)));
        assertDoesNotThrow(() -> manager.checkEcritureComptable(vEC));

    }
    //expect no exception
    @Test
    public void checkEcritureComptableUnitTest() {
        vEC = new EcritureComptable();
        vEC.setJournal(new JournalComptable("AA", "Achat"));
        vEC.setReference("AA-"+ Calendar.getInstance().get(Calendar.YEAR) +"/00001");
        vEC.setId(-1);
        vEC.setDate(new Date());
        vEC.setLibelle("Libelle");
        vEC.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEC.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, null, new BigDecimal(123)));
        assertDoesNotThrow(() -> manager.checkEcritureComptableUnit(vEC));

    }

    //update existing reference
    @Test
    public void AddRef_SequenceNotNull(){
        vEC.setReference(null);
        manager.addReference(vEC);

        assertNotNull(vEC.getReference());
        assertEquals("AA-2021/2",vEC.getReference());

    }

    //new reference
    @Test
    public void AddRef_SequenceNull(){
        vEC.setReference(null);
        vEC.setJournal(new JournalComptable("AC", "Test"));
        manager.addReference(vEC);

        assertNotNull(vEC.getReference());

    }


    //expected throw "L'??criture comptable ne respecte pas les contraintes de validation"
    @Test
    public void checkEcritureComptableUnitViolation() throws Exception {
        Assertions.assertThrows(FunctionalException.class, () -> manager.checkEcritureComptableUnit(new EcritureComptable()));
    }

    //expected throw "L'??criture comptable n'est pas ??quilibr??e. "
    @Test
    public void checkEcritureComptableUnitRG2Test_False() {
        vEC.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEC.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(1234)));
        assertThrows(FunctionalException.class, () -> manager.checkEcritureComptableUnit(vEC));
    }


    //expected throw "L'??criture comptable doit avoir au moins deux lignes : une ligne au d??bit et une ligne au cr??dit."
    @Test
    public void checkEcritureComptableUnitRG3Test_False() {
        vEC.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEC.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        assertThrows(FunctionalException.class, () -> manager.checkEcritureComptableUnit(vEC));
    }

    //expected throw "La date de la r??f??rence ne correspond pas ?? l'ann??e d'??criture."
    @Test
    public void checkEcritureComptableUnitRG5_TestDate() {
        vEC.setReference("AA-2012/00001");

        assertThrows(FunctionalException.class, () -> manager.checkEcritureComptableUnit(vEC));


    }

    //expected throw "Le code journal de la r??f??rence est diff??rent du code journal."
    @Test
    public void checkEcritureComptableUnitRG5_TestCodeJournal(){
        vEC.setJournal(new JournalComptable("ZZ", "Test"));

        assertThrows(FunctionalException.class, () -> manager.checkEcritureComptableUnit(vEC));
        vEC.setJournal(new JournalComptable("AA", "Achat"));

    }

    //expected throw " RG_Compta_6 : La r??f??rence d'une ??criture comptable doit ??tre unique."
    @Test
    public void checkEcritureComptableContextTest() {
        EcritureComptable vEcritureComptable = new EcritureComptable();
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


}
