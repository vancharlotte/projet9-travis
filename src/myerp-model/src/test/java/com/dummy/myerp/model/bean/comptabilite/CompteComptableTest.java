package com.dummy.myerp.model.bean.comptabilite;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CompteComptableTest {

    @Test
    public void getByNumeroTest() {
        CompteComptable compteComptable = new CompteComptable(101,"Test");
        List<CompteComptable> testList = new ArrayList<CompteComptable>();
        testList.add(compteComptable);
        CompteComptable resultat = CompteComptable.getByNumero(testList, 101);

        assertEquals(compteComptable ,resultat);

    }

    @Test
    public void getByNumeroTest_WhenFirstConditionFalse() {
        List<CompteComptable> testList = new ArrayList<CompteComptable>();

        assertNotEquals(new CompteComptable(), CompteComptable.getByNumero(testList, 101));

    }

    @Test
    public void getByNumeroTest_WhenSecondConditionFalse() {
        CompteComptable compteComptable = new CompteComptable();
        compteComptable.setNumero(101);
        compteComptable.setLibelle("Test");
        List<CompteComptable> testList = new ArrayList<CompteComptable>();
        testList.add(compteComptable);

        assertNotEquals(compteComptable, CompteComptable.getByNumero(testList, 102));

    }
}
