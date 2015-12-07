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
package uk.co.develop4.persistence.jpa.common;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author williamtimpany
 */
public class DomainIdentityGenerator {
   

    public static class LongGenerator {
        private static final Logger LOGGER = LoggerFactory.getLogger(LongGenerator.class);

        private static AtomicLong longId = new AtomicLong(0);

        public static Long next() {
            Long l = longId.incrementAndGet();
            LOGGER.debug("Generated Long PK: {}", l);
            return l;
        }
    }

    public static class UUIDGenerator {
        private static final Logger LOGGER = LoggerFactory.getLogger(UUIDGenerator.class);

        public static UUID next() {
            UUID u = UUID.randomUUID();
            LOGGER.debug("Generated UUID PK: {}", u);
            return u;
        }
    }

}
