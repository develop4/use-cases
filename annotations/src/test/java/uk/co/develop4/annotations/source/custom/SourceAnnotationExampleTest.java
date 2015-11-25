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
import uk.co.develop4.annotations.utilities.AnnotationPrinter;

/**
 *
 * @author william timpany
 */
public class SourceAnnotationExampleTest {

    public SourceAnnotationExampleTest() {
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
     * Test of testAnnotation method, of class MySourceAnnotationExample.
     */
    @Test
    public void testGetSquare() {
        System.out.println("getSquare");
        MySourceAnnotationExample instance = new MySourceAnnotationExample();
        int i = instance.getSquare(2);
        assertEquals(i, 4);
    }

    /**
     * Test of testNoAnnotation method, of class MySourceAnnotationExample.
     */
    @Test
    public void testGetCube() {
        System.out.println("getCube");
        MySourceAnnotationExample instance = new MySourceAnnotationExample();
        int i = instance.getCube(2);
        assertEquals(i, 8);
    }

    @Test
    public void testInst() {
        MySourceAnnotationExample tester = new MySourceAnnotationExample();
        AnnotationPrinter.printAnnotations(tester.getClass());
    }

}
