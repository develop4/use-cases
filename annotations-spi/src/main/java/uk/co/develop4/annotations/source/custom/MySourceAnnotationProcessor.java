package uk.co.develop4.annotations.source.custom;
/*
 * (C) Copyright 2015 Develop4 Technologies Ltd
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

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * This processor will print out any classes, methods, etc where the object has been annotated with
 * the @CustomSourceAnnotation annotation.  
 * 
 * @author william timpany
 *
 */
@SupportedAnnotationTypes({ "uk.co.develop4.annotations.source.custom.MySourceAnnotation" })
public class MySourceAnnotationProcessor extends AbstractProcessor {

	/*
	 * When the annotations supported are detected, process then then return true to swallow the annotation
	 * to prevent processing by any other processor.
	 * (non-Javadoc)
	 * @see javax.annotation.processing.AbstractProcessor#process(java.util.Set, javax.annotation.processing.RoundEnvironment)
	 */
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
		Messager messager = processingEnv.getMessager();

		for (TypeElement te : annotations) {
			for (Element e : env.getElementsAnnotatedWith(te)) {
				messager.printMessage(Diagnostic.Kind.NOTE, "--> MySourceAnnotationProcessor detected MySourceAnnotation During Compile: " + e.toString());
			}
		}
		return true;
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latestSupported();
	}

}
