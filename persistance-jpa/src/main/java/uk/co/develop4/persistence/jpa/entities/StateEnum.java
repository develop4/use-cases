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

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Fancy Enumeration with built in converters to translate a single character database value back into
 * the enumeration.
 * 
 * @author william timpany
 */
public enum StateEnum {
    DRAFT(1,"Draft","D"),
    ACTIVE(2,"Active","A"),
    RETIRED(2,"Retired","R"),
    DELETED(9,"Deleted","X");
    
    private final int id;
    private final String value;
    private final String shortName;
    
    private static final Map<Integer, StateEnum> lookupById = new HashMap<Integer, StateEnum>();
    private static final Map<String, StateEnum> lookupByValue = new HashMap<String, StateEnum>();
    private static final Map<String, StateEnum> lookupByShortName = new HashMap<String, StateEnum>();

    static {
        for(StateEnum e : EnumSet.allOf(StateEnum.class)) {
            lookupById.put(e.getId(), e);
            lookupByValue.put(e.getValue(), e);
            lookupByShortName.put(e.getShortName(), e);
        }
    }

    StateEnum(int id, String value, String shortName) {
        this.id = id;
        this.value = value;
        this.shortName = shortName;
    }

    public static StateEnum fromId(int id) {
        if (lookupById.get(id) != null) {
            return lookupById.get(id);
        } else {
            throw new IllegalArgumentException();
        }
    }
    
    public static StateEnum fromValue(String value) {
        if (lookupByValue.get(value) != null) {
            return lookupByValue.get(value);
        } else {
            throw new IllegalArgumentException();
        }
    }
    
    public static StateEnum fromShortName(String shortName) {
        if (lookupByValue.get(shortName) != null) {
            return lookupByValue.get(shortName);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @return the shortName
     */
    public String getShortName() {
        return shortName;
    }
    
}
