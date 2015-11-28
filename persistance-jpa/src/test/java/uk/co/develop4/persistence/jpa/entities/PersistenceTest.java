/*
 * Copyright 2015 Develop4 Technologies Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.co.develop4.persistence.jpa.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 *
 * @author williamtimpany
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PersistenceTest {

    private static Logger logger = Logger.getLogger(PersistenceTest.class.getName());

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static final String testPersistenceUnit = "testPU";
    private static Map registry = new HashMap();

    @BeforeClass
    public static void initTestFixture() throws Exception {
        registry.clear();
        emf = Persistence.createEntityManagerFactory(testPersistenceUnit);
        em = emf.createEntityManager();
    }

    /**
     * Cleans up the session.
     */
    @AfterClass
    public static void closeTestFixture() {
        em.close();
        emf.close();
        registry.clear();
    }

    @Test
    public void test1Persistence() {
        try {
            em.getTransaction().begin();
            Person p = Person.builder().email("person1@test.co.uk").build();
            em.persist(p);
            assertThat(em.contains(p)).as("Person has been persisted",p.getEmail()).isNotNull();
            assertThat(p.getId()).as("Person id has been set",p.getEmail()).isNotNull();
            em.getTransaction().commit();
            registry.put("p1_pk",p.getId());
        } catch (Exception ex) {
            em.getTransaction().rollback();
            ex.printStackTrace();
        }
    }
    
    @Test
    public void test2Persistence() {
        try {
            em.getTransaction().begin();
            Person p = Person.builder().email("person2@test.co.uk").build();
            em.persist(p);
            assertThat(em.contains(p)).as("Person has been persisted",p.getEmail()).isNotNull();
            assertThat(p.getId()).as("Person id has been set",p.getEmail()).isNotNull();
            em.getTransaction().commit();
            registry.put("p2_pk",p.getId());        
        } catch (Exception ex) {
            em.getTransaction().rollback();
            ex.printStackTrace();
        }
    }
    
    @Test
    public void test3Persistence() {
        try {
            Object p_pk = (UUID)registry.get("p1_pk");
            em.getTransaction().begin();
            Person p = em.find(Person.class, p_pk);
            assertThat(p).as("Person has been found for id").isNotNull();
            assertThat(p.getEmail()).as("Person has correct mail address").isEqualTo("person1@test.co.uk");
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            ex.printStackTrace();
        }
    }
    
    @Test
    public void test4Persistence() {
        try {
            em.getTransaction().begin();
            List<Person> listOfPersons = em.createNamedQuery("Person.getPersons").getResultList();

            assertThat(listOfPersons).as("Only two Pepole are in the list")
                    .hasSize(2)
                    .extracting(person -> person.getEmail())
                        .contains("person1@test.co.uk", "person2@test.co.uk")
                        .doesNotContain("fake@fake.co.uk");

            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            ex.printStackTrace();
        }
    }
    
}
