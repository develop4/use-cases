package uk.co.develop4.annotations.utilities;
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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AnnotationPrinter {

	@SuppressWarnings({ "rawtypes"})
	public static void printAnnotations(Class myClass) {
		
		System.out.format(" --> %s\n", myClass);
		
		// get all class annotations
		for (Annotation annotation : myClass.getAnnotations()) {
			System.out.format(" --> with %s \n", annotation);
		}
		// the same for all methods of the class
		for (Method method : myClass.getDeclaredMethods()) {
			for (Annotation annotation : method.getAnnotations()) {
				System.out.format(" --> method %s with %s \n", method.getName(), annotation);
			}

		}
		// the same for all fields of the class
		for (Field field : myClass.getDeclaredFields()) {
			for (Annotation annotation : field.getAnnotations()) {
				System.out.format(" --> field %s with %s \n", field.getName(), annotation);
			}

		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void assertExists(Class myClass, Class myAnnotation) throws Exception {
		// Checks if an annotation is present
		if (!myClass.isAnnotationPresent(myAnnotation.getClass())) {
			throw new Exception("Annotation : " + myAnnotation.getClass() + " not found.");
		}
	}

}
