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

import uk.co.develop4.persistence.jpa.common.DomainIdentityGenerator;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import static uk.co.develop4.persistence.jpa.entities.AssertionConcern.assertArgumentNotEmpty;

/**
 *
 * @author williamtimpany
 */
@Entity
public class Address extends AbstractEntity<Long> {

    @Id
    @Column(name = "address_id")
    private Long id;

    @Column(name = "postcode")
    private String postCode;

    @Override
    public Long getId() {
        return this.id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Address[");
        sb.append(" id=").append(getId());
        sb.append(",postCode=").append(getPostCode());
        //sb.append(",updatedDate=").append(getUpdatedDate());
        //sb.append(",createdDate=").append(getCreatedDate());
        sb.append(" ]");
        return sb.toString();
    }

    /**
     * @return the postcode
     */
    public String getPostCode() {
        return postCode;
    }

    /**
     * @param postcode the postcode to set
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public static class Builder {

        private Long id;
        private String postCode;

        private Builder() {
        }

        public Builder id(final Long id) {
            this.id = id;
            return this;
        }

        public Builder postCode(final String postCode) {
            this.postCode = postCode;
            return this;
        }

        public Address build() {
            // --  Guards
            assertArgumentNotEmpty(postCode, "postCode: must be provided.");
            // -- Populate
            Address address = new Address();
            if (id == null) {
                id = DomainIdentityGenerator.LongGenerator.next();
            }
            address.setId(id);
            address.setPostCode(postCode);
            // -- return new object
            return address;
        }
    }

    public static Address.Builder builder() {
        return new Address.Builder();
    }

    private Address() {
        super();
    }

}
