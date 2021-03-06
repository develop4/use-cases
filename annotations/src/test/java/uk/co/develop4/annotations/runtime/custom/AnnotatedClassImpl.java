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

import uk.co.develop4.annotations.runtime.custom.CustomClassAnnotation;

@CustomClassAnnotation(name="William J. Timpany", date="22-07-1969")
public class AnnotatedClassImpl {

	@CustomAnnotatedField(name="annotated field")
	private String myAnnotatedField;
	
	@CustomMethodAnnotation(name="custom annotated method")
	public void myAnnotatedMethod() {
		
	}
}
