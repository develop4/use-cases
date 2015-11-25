/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.develop4.annotations.source.custom;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author william timpany
 */
public class CustomSourceAnnotationImplTest {

    public CustomSourceAnnotationImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of testAnnotation method, of class CustomSourceAnnotationImpl.
     */
    @Test
    public void testGetSquare() {
        System.out.println("getSquare");
        CustomSourceAnnotationImpl instance = new CustomSourceAnnotationImpl();
        int i = instance.getSquare(2);
        assertEquals(i, 4);
    }

    /**
     * Test of testNoAnnotation method, of class CustomSourceAnnotationImpl.
     */
    @Test
    public void testGetCube() {
        System.out.println("getCube");
        CustomSourceAnnotationImpl instance = new CustomSourceAnnotationImpl();
        int i = instance.getCube(2);
        assertEquals(i, 8);
    }

}
