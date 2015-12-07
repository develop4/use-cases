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
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import static uk.co.develop4.persistence.jpa.entities.AssertionConcern.assertArgumentNotEmpty;
import static uk.co.develop4.persistence.jpa.entities.AssertionConcern.assertArgumentNotNull;

/**
 *
 * @author william timpany
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Person.ListAllPersons", query = "select p from Person p"),
    @NamedQuery(name = "Person.ListPersonsByPostCode", query = "select p from Person p Join p.addresses e where e.postCode = :postCode")
})
public class Person extends AbstractEntity<UUID> {

    public static final String NQ_LIST_ALL_PERSONS = "Person.ListAllPersons";
    public static final String NQ_LIST_PERSONS_BY_POSTCODE = "Person.ListPersonsByPostCode";

    @Id
    @Column(name = "person_id")
    private UUID id;

    @Column(name = "email")
    private String email;

    @Column(name = "state")
    private StateEnum state;

    @Column(name = "age")
    private Integer age;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "person_address",
            joinColumns = {
                @JoinColumn(name = "person_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                @JoinColumn(name = "address_id", nullable = false, updatable = false)})
    private Set<Address> addresses = new HashSet<Address>();

    @Override
    public UUID getId() {
        return this.id;
    }

    private void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public StateEnum getState() {
        return this.state;
    }

    public void setState(StateEnum state) {
        this.state = state;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Person[");
        sb.append(" id=").append(getId());
        sb.append(",email=").append(getEmail());
        sb.append(",age=").append(getAge());
        sb.append(",state=").append(getState());
        sb.append(",updatedDate=").append(getUpdatedDate());
        sb.append(",createdDate=").append(getCreatedDate());
        if (!addresses.isEmpty()) {
            sb.append(", [ addresses=").append(addresses.toString());
            sb.append(" ]");
        }
        sb.append(" ]");
        return sb.toString();
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * @return the addresses
     */
    public Set<Address> getAddresses() {
        return addresses;
    }

    /**
     * @param addresses the addresses to set
     */
    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public static class Builder {

        private String email;
        private StateEnum state;
        private Integer age;
        private UUID id;
        private Set<Address> addresses = new HashSet<Address>();

        private Builder() {
        }

        public Builder id(final UUID id) {
            this.id = id;
            return this;
        }

        public Builder state(final StateEnum state) {
            this.state = state;
            return this;
        }
        
        public Builder address(final Address address) {
            this.addresses.add(address);
            return this;
        }

        public Builder age(final int age) {
            this.age = age;
            return this;
        }

        public Builder email(final String value) {
            this.email = value;
            return this;
        }

        public Person build() {
            // --  Guards
            assertArgumentNotEmpty(email, "email: must be provided.");
            assertArgumentNotNull(state, "state: must be provided.");
            // -- Populate
            Person person = new Person();
            if (id == null) {
                id = DomainIdentityGenerator.UUIDGenerator.next();
            }
            person.setId(id);
            person.setAge(age);
            person.setEmail(email);
            person.setState(state);
            if (!addresses.isEmpty()) {
                person.setAddresses(addresses);
            }
            // -- return new object
            return person;
        }
    }

    public static Person.Builder builder() {
        return new Person.Builder();
    }

    private Person() {
        super();
    }

}
