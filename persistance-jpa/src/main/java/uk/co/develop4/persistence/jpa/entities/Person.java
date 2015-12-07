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

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import static uk.co.develop4.persistence.jpa.entities.AssertionConcern.assertArgumentNotEmpty;

/**
 *
 * @author william timpany
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Person.getPersons", query = "select p from Person p")
})
public class Person extends BaseEntity {

    private String email;
    
    //@Column(columnDefinition = "CHAR(1)")
    private StateEnum state;

    private int age;
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public StateEnum getState() {
        return state;
    }

    public void setState(StateEnum state) {
        this.state = state;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("uk.co.develop4.persistance.jpa.entities.Person[");
        sb.append(" id=").append(getId());
        sb.append(",email=").append(getEmail());
        sb.append(",age=").append(getAge());
        sb.append(",state=").append(getState());
        sb.append(",updatedDate=").append(getUpdatedDate());
        sb.append(",createdDate=").append(getCreatedDate());
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

    public static class Builder {

        private String email;
        private StateEnum state;
        private int age;
        private UUID id = UUID.randomUUID();

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
            person.setId(id);
            person.setAge(age);
            person.setEmail(email);
            person.setState(state);
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
