package com.dummy.myerp.business.impl.manager;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.testbusiness.business.BusinessTestCase;
import com.dummy.myerp.testbusiness.business.SpringRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ComptabiliteManagerImplIT extends BusinessTestCase {

    private ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();

    public void initSpring(){
        SpringRegistry.init();
    }

    @Test
    public void getListCompteComptableTest(){
        SpringRegistry.init();
        List<CompteComptable> listeCompteComptableTest =  new ArrayList<CompteComptable>();
        listeCompteComptableTest = manager.getListCompteComptable();
        System.out.println(listeCompteComptableTest.size());
        assertNotNull(listeCompteComptableTest.size());
    }
}
