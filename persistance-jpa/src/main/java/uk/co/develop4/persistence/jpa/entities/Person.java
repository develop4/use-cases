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

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import org.eclipse.persistence.annotations.UuidGenerator;

/**
 *
 * @author williamtimpany
 */
@Entity
@NamedQueries({
    @NamedQuery(name="Person.getPersons", query="select p from Person p")          
})
@UuidGenerator(name = "PERSON_ID_GEN")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "PERSON_ID_GEN")
    private UUID id;

    private String email;

    public Person() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Person)) {
            return false;
        }
        Person other = (Person) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("uk.co.develop4.persistance.jpa.entities.Person[");
        sb.append(" id=" + id);
        sb.append(",email=" + email);
        sb.append(" ]");
        return sb.toString();
    }

    public static class Builder {

        private String email;

        private Builder() {
        }

        public Builder email(final String value) {
            this.email = value;
            return this;
        }

        public Person build() {
            return new uk.co.develop4.persistence.jpa.entities.Person(email);
        }
    }

    public static Person.Builder builder() {
        return new Person.Builder();
    }

    private Person(final String email) {
        this.email = email;
    }

}
