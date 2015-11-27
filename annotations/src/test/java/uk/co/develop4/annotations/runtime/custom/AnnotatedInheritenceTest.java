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

import java.lang.annotation.Annotation;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author william timpany
 */
public class AnnotatedInheritenceTest {
    
    @Test
    public void inheritenceSuperClassTest() {
        AnnotatedSuperClassExample myClass = new AnnotatedSuperClassExample();
        assertThat(myClass.dummyMethod())
                .isEqualTo("test");
        assertThat(AnnotatedSuperClassExample.class.isAnnotationPresent( MyInheritedAnnotation.class ))
                .isEqualTo(true);
    }
    
    @Test
    public void inheritenceSubClassTest() {
        AnnotatedSubClassExample myClass = new AnnotatedSubClassExample();
        assertThat(myClass.dummyMethod())
                .isEqualTo("test");
        assertThat(AnnotatedSubClassExample.class.isAnnotationPresent( MyInheritedAnnotation.class ))
                .isEqualTo(true);
        assertThat(((MyInheritedAnnotation)AnnotatedSubClassExample.class.getAnnotation(MyInheritedAnnotation.class)).name())
                .isEqualTo("Inherited from AnnotatedSuperClassExample");
    }
    
}
