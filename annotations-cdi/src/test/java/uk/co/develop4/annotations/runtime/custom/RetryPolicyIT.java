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

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.assertj.core.api.Assertions.assertThat;
import org.jboss.shrinkwrap.api.spec.WebArchive;

/**
 *
 * @author williamtimpany
 */
@RunWith(Arquillian.class)
public class RetryPolicyIT {

    @Inject
    RetryService rps;

    @Deployment
    public static Archive<?> createDeployment() {
        WebArchive war;
        war = ShrinkWrap.create(WebArchive.class)
                .addPackages(true, RetryPolicyIT.class.getPackage())
                .addAsWebInfResource("web.xml", "web.xml")
                .addAsWebInfResource("beans.xml", "beans.xml");
        return war;
    }

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