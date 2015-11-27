package uk.co.develop4.annotations.runtime.custom;

/*
 * (C) Copyright 2015 Develop4 Technologies (http://develop4.co.uk/) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;



public class AnnotatedClassTest {

    @Test 
    public void checkMethods() {
        AnnotatedClassExample myClass = new AnnotatedClassExample();
        myClass.setMyAnnotatedField("testdata");
        assertThat(myClass.getMyAnnotatedField()).isEqualTo("testdata");
    }
    @Test
    public void annotationsClass() {
        assertThat(AnnotatedClassExample.class.isAnnotationPresent(MyClassAnnotation.class)).isEqualTo(true);
        assertThat(((MyClassAnnotation) AnnotatedClassExample.class.getAnnotation(MyClassAnnotation.class)).name()).isEqualTo("William J. Timpany");
        assertThat(((MyClassAnnotation) AnnotatedClassExample.class.getAnnotation(MyClassAnnotation.class)).date()).isEqualTo("22-07-1969");
    }

    @Test
    public void annotationsMethod() throws NoSuchMethodException {
        assertThat(AnnotatedClassExample.class.getDeclaredMethod("getMyAnnotatedField").isAnnotationPresent(MyMethodAnnotation.class)).isEqualTo(true);
        assertThat(((MyMethodAnnotation) AnnotatedClassExample.class.getDeclaredMethod("getMyAnnotatedField").getAnnotation(MyMethodAnnotation.class)).name()).isEqualTo("custom annotated method");
    }

    @Test
    public void annotationsField() throws NoSuchFieldException{
        assertThat(AnnotatedClassExample.class.getDeclaredField("myAnnotatedField").isAnnotationPresent(MyFieldAnnotation.class)).isEqualTo(true);
        assertThat(((MyFieldAnnotation) AnnotatedClassExample.class.getDeclaredField("myAnnotatedField").getAnnotation(MyFieldAnnotation.class)).name()).isEqualTo("custom annotated field");
    }
}
