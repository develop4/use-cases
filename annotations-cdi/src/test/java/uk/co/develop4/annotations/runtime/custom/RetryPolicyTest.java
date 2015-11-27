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
package uk.co.develop4.annotations.runtime.custom;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.MatcherAssert.assertThat;
import org.jboss.shrinkwrap.api.ArchivePaths;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *
 * @author williamtimpany
 */
@RunWith(Arquillian.class)
public class RetryPolicyTest {

    @Deployment
    public static Archive<?> createDeployment() {
        JavaArchive jar;
        jar = ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, RetryPolicyTest.class.getPackage())
                .addAsManifestResource("beans.xml");
        return jar;
    }
    
    @Inject
    RetryService rps;
            
    @Test 
    public void retryTest() {
        rps.reset(2);
        String result = null;
        try {
            result = rps.getRemoteValue();
        } catch (Exception ex) {
            assertThat(ex).isNull();
        }
        assertThat(result).isNotNull();
        assertThat(result).contains("Finished at last after");
    }
    
    @Test 
    public void retryTestFails() {
        rps.reset(4);
        String result = null;
        try {
            result = rps.getRemoteValue();
        } catch (Exception ex) {
            assertThat(ex.getMessage()).contains("Ooops the service has failed");
        }
        assertThat(result).isNull();
    }
}
