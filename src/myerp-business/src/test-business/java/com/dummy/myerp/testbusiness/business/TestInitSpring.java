package com.dummy.myerp.testbusiness.business;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


/**
 * Classe de test de l'initialisation du contexte Spring
 */
public class TestInitSpring extends BusinessTestCase {

    /**
     * Constructeur.
     */
    public TestInitSpring() {
        super();
    }


    /**
     * Teste l'initialisation du contexte Spring
     */
    @Test
    public void testInit() {
        SpringRegistry.init();
        Assertions.assertNotNull(SpringRegistry.getBusinessProxy());
        Assertions.assertNotNull(SpringRegistry.getTransactionManager());

    }


}
