package com.dummy.myerp.model.bean.comptabilite;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class LigneEcritureComptableTest {

    @Test
    public void LigneEcritureComptableToStringTestEquals() {
        LigneEcritureComptable newLEC = new LigneEcritureComptable();
        CompteComptable newCC = new CompteComptable();
        newCC.setNumero(101);
        newCC.setLibelle("CompteTest");
        newLEC.setCompteComptable(newCC);
        newLEC.setLibelle("LigneTest");
        newLEC.setDebit(new BigDecimal("123") );
        newLEC.setCredit(null);


        assertEquals("LigneEcritureComptable{compteComptable=CompteComptable{numero=101, libelle='CompteTest'}, " +
                "libelle='LigneTest', debit=123, credit=null}", newLEC.toString());
    }

    @Test
    public void LigneEcritureComptableToStringTestNotEquals() {
        LigneEcritureComptable newLEC = new LigneEcritureComptable(new CompteComptable(101,"CompteTEST"),"LigneTest",new
                BigDecimal("123"),null);
        assertNotEquals("LigneEcritureComptable{compteComptable=CompteComptable{numero=101, libelle='CompteTest'}, " +
                "libelle='LigneTest', debit=null, credit=123}", newLEC.toString());
    }
}
