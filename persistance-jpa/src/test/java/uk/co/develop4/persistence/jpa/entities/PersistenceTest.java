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
import java.util.logging.Level;
import java.util.logging.LogManager;
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

    private static final Logger LOGGER = Logger.getLogger(PersistenceTest.class.getName());

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static String testPersistenceUnit = "testPU";
    private static Map registry = new HashMap();

    @BeforeClass
    public static void initTestFixture() throws Exception {
        LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME).setLevel(Level.FINEST); 

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
        LOGGER.info("test 1 Persistence: ");
        try {
            em.getTransaction().begin();
            Person p = Person.builder()
                    .email("person1@test.co.uk")
                    .age(40)
                    .state(StateEnum.ACTIVE)
                    .address(Address.builder().postCode("EC2Y XXX").build())
                    .address(Address.builder().postCode("EC2Y YYY").build())
                    .address(Address.builder().postCode("EC2Y ZZZ").build())
                    .build();
            em.persist(p);
            assertThat(em.contains(p)).as("Person has been persisted", p.getEmail()).isNotNull();
            assertThat(p.getId()).as("Person id has been set", p.getEmail()).isNotNull();
            em.getTransaction().commit();
            registry.put("p1_pk", p.getId());
        } catch (Exception ex) {
            em.getTransaction().rollback();
            ex.printStackTrace();
        }
    }

    @Test
    public void test2Persistence() {
        LOGGER.info("test 2 Persistence: ");
        try {
            em.getTransaction().begin();
            Person p = Person.builder()
                    .email("person2@test.co.uk")
                    .age(30)
                    .state(StateEnum.RETIRED)
                    .address(Address.builder().postCode("EC2Y QQQ").build())
                    .build();
            em.persist(p);
            assertThat(em.contains(p)).as("Person has been persisted", p.getEmail()).isNotNull();
            assertThat(p.getId()).as("Person id has been set", p.getEmail()).isNotNull();
            em.getTransaction().commit();
            registry.put("p2_pk", p.getId());
        } catch (Exception ex) {
            em.getTransaction().rollback();
            ex.printStackTrace();
        }
    }

    @Test
    public void test3Persistence() {
        LOGGER.info("test 3 Persistence: ");
        try {
            Object p_pk = (UUID) registry.get("p1_pk");
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
    public void test4aPersistence() {
        LOGGER.info("test 4a Persistence: ");
        try {
            em.getTransaction().begin();
            
            List<Person> listOfPersons = em.createNamedQuery(Person.NQ_LIST_ALL_PERSONS)
                    .getResultList();
            listOfPersons.stream().forEach(System.out::println);

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
    
    @Test
    public void test4bPersistence() {
        LOGGER.info("test 4b Persistence: ");
        try {
            em.getTransaction().begin();
            
            List<Person> listOfPersons = em.createNamedQuery(Person.NQ_LIST_PERSONS_BY_POSTCODE)
                    .setParameter("postCode", "EC2Y XXX")
                    .getResultList();
            listOfPersons.stream().forEach(System.out::println);

            assertThat(listOfPersons).as("Only one Pepole are in the list")
                    .hasSize(1)
                    .extracting(person -> person.getEmail())
                    .contains("person1@test.co.uk")
                    .doesNotContain("fake@fake.co.uk");
            
            // -- remove an address from the addresses using the streams api
            listOfPersons.get(0).getAddresses().removeIf( a -> a.getPostCode().equals("EC2Y XXX") );
            listOfPersons.stream().forEach(System.out::println);

            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            ex.printStackTrace();
        }
    }

    @Test
    public void test5Persistence() {
        LOGGER.info("test 5 Persistence: ");
        try {
            em.getTransaction().begin();
            List<Person> x = (List<Person>)em.createNativeQuery("SELECT p.person_id, p.email, p.age, p.state, p.updatedDate, p.createdDate FROM Person p", Person.class).getResultList();
            x.stream().forEach(System.out::println);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            ex.printStackTrace();
        }
    }
    
    @Test
    public void test6Persistence() {
        LOGGER.info("test 5 Persistence: ");
        try {
            Object p_pk = (UUID) registry.get("p1_pk");
            em.getTransaction().begin();
            Person p = em.find(Person.class, p_pk);
            assertThat(p).as("Person has been found for id").isNotNull();
            assertThat(p.getAge()).as("Person has the correct age").isEqualTo(40);
            p.setAge(60);
            em.getTransaction().commit();
            
            em.getTransaction().begin();
            p = em.find(Person.class, p_pk);
            assertThat(p.getAge()).as("Person has the correct age").isEqualTo(60);
            em.getTransaction().commit();
            
            em.getTransaction().begin();
            List<Person> x = (List<Person>)em.createNativeQuery("SELECT p.person_id, p.email, p.age, p.state, p.updatedDate, p.createdDate FROM Person p", Person.class).getResultList();
            x.stream().forEach(System.out::println);
            em.getTransaction().commit();
            
        } catch (Exception ex) {
            em.getTransaction().rollback();
            ex.printStackTrace();
        }
    }

}
