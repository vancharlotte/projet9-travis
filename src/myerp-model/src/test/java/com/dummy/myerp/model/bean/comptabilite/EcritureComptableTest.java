package com.dummy.myerp.model.bean.comptabilite;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import org.apache.commons.lang3.ObjectUtils;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;



public class EcritureComptableTest {

    private LigneEcritureComptable createLigne(Integer pCompteComptableNumero, String pDebit, String pCredit) {
        BigDecimal vDebit = pDebit == null ? null : new BigDecimal(pDebit);
        BigDecimal vCredit = pCredit == null ? null : new BigDecimal(pCredit);
        String vLibelle = ObjectUtils.defaultIfNull(vDebit, BigDecimal.ZERO)
                .subtract(ObjectUtils.defaultIfNull(vCredit, BigDecimal.ZERO)).toPlainString();
        LigneEcritureComptable vRetour = new LigneEcritureComptable(new CompteComptable(pCompteComptableNumero),
                vLibelle,
                vDebit, vCredit);
        return vRetour;
    }


    @Test
    public void getTotalDebitTest(){
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "200", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "100.50", "50.00"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "100"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "40.00", "40.50"));
        assertEquals(new BigDecimal(200+100.50+40.00).setScale(2, RoundingMode.HALF_UP),vEcriture.getTotalDebit());

    }

    @Test
    public void getTotalCreditTest(){
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "200", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "100.50", "50.00"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "100"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "40.00", "40.50"));
        assertEquals(new BigDecimal(50.00+100+40.50).setScale(2, RoundingMode.HALF_UP),vEcriture.getTotalCredit());

    }

    @Test
    public void isEquilibree() {
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();
        vEcriture.setId(1);
        vEcriture.setJournal(new JournalComptable("AC", "Achat"));
        vEcriture.setDate(new Date());
        vEcriture.setReference("AC-2020/00001");
        vEcriture.setLibelle("Equilibrée");

        vEcriture.getListLigneEcriture().add(this.createLigne(1, "200.50", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "100.50", "33"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "301"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "40", "7"));
        assertTrue(vEcriture.isEquilibree(), vEcriture.toString());

        vEcriture.getListLigneEcriture().clear();
        vEcriture.setLibelle("Non équilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "10", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "20", "1"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "30"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "1", "2"));
        assertFalse(vEcriture.isEquilibree(), vEcriture.toString());

    }

}
