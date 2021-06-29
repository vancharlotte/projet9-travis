package com.dummy.myerp.model.bean.comptabilite;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class JournalComptableTest {

    @Test
    public void getByCodeTest() {
        JournalComptable journalComptable = new JournalComptable("1","Test1");
        List<JournalComptable> testList = new ArrayList<JournalComptable>();
        testList.add(journalComptable);
        JournalComptable resultat = JournalComptable.getByCode(testList, "1");

        assertEquals(journalComptable ,resultat);

    }

    @Test
    public void getByCodeTest_WhenFirstConditionFalse() {
        List<JournalComptable> testList = new ArrayList<JournalComptable>();

        assertNotEquals(new JournalComptable(), JournalComptable.getByCode(testList, "1"));

    }

    @Test
    public void getByCodeTest_WhenSecondConditionFalse() {
        JournalComptable journalComptable = new JournalComptable();
        journalComptable.setCode("1");
        journalComptable.setLibelle("Test");
        List<JournalComptable> testList = new ArrayList<JournalComptable>();
        testList.add(journalComptable);

        assertNotEquals(journalComptable, JournalComptable.getByCode(testList, "2"));

    }


    @Test
    public void JournalComptableToStringTest() {
        JournalComptable newJC= new JournalComptable();
        newJC.setCode("AC");
        newJC.setLibelle("JournalTest");
        assertEquals("JournalComptable{code='AC', libelle='JournalTest'}", newJC.toString());
    }
}
