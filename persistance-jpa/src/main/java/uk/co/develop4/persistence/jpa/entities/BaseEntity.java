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
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import org.eclipse.persistence.annotations.UuidGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Basic entity that provides:
 * - UUID as Primary Key
 * - long as Verion Number for optomistic locking
 * @author william timpany
 */
@MappedSuperclass  
@UuidGenerator(name = "UUID_GEN")
public abstract class BaseEntity extends AssertionConcern implements Serializable {

    final Logger LOGGER = LoggerFactory.getLogger(BaseEntity.class);

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(generator = "UUID_GEN")
    private UUID id;
    
    @Version
    @Column(name="OPTLOCK", columnDefinition = "INTEGER DEFAULT 0")
    private long version;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    protected BaseEntity() {
        super();
    }

    public UUID getId() {
        return id;
    }
    
    protected UUID setId(UUID id) {
        return id = id;
    }
    
    @PrePersist
    private void onPrePersist() {
        setCreatedDate(new Date());
        setUpdatedDate(new Date());
        LOGGER.info("Update timestamp onPrePersist: {}, {}", createdDate, updatedDate);
    }
    
    @PreUpdate
    private void onPreUpdate() {
        setUpdatedDate(new Date());
        LOGGER.info("Update timestamp onPreUpdate: {}", updatedDate);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BaseEntity)) {
            return false;
        }
        BaseEntity other = (BaseEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uk.co.develop4.persistence.jpa.entities.BaseEntity[ id=" + id + " ]";
    }

    /**
     * @return the version
     */
    public long getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(long version) {
        this.version = version;
    }

    /**
     * @return the updatedDate
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

}
