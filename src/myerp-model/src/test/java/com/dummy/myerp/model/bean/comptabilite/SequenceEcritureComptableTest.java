package com.dummy.myerp.model.bean.comptabilite;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SequenceEcritureComptableTest {

    @Test
    public void SequenceEcritureComptableToStringTestEquals() {
        SequenceEcritureComptable newSEC = new SequenceEcritureComptable();
        newSEC.setCodeJournal("AC");
        newSEC.setAnnee(2020);
        newSEC.setDerniereValeur(10);
        assertEquals("SequenceEcritureComptable{codeJournal='AC', annee=2020, derniereValeur=10}", newSEC.toString());
    }

    @Test
    public void SequenceEcritureComptableToStringTestNotEquals() {
        SequenceEcritureComptable newSEC = new SequenceEcritureComptable("AC",2020,10);
        assertNotEquals("SequenceEcritureComptable{codeJournal='AC', annee=2020, derniereValeur=100}", newSEC.toString());
    }


}
