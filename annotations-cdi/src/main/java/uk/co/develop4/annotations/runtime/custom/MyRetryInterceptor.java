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

import java.io.Serializable;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 *
 * @author williamtimpany
 */
@Interceptor
@MyRetryInterceptorBinding
public class MyRetryInterceptor {

    @AroundInvoke
    public Object intercept(final InvocationContext ic) throws Exception {
        Object result = null;
        int retryThreshold;
        MyRetryInterceptorBinding retryPolicy = ic.getMethod().getAnnotation(MyRetryInterceptorBinding.class);
        if (retryPolicy != null) {
            retryThreshold = retryPolicy.retryThreshold();
        } else {
            throw new IllegalStateException(String.format("Method '%s' from class '%s' should be annotated with @MyRetryInterceptorBinding",
                    ic.getMethod().getName(), 
                    ic.getTarget().getClass().getName()));
        }
        boolean failed = true;
        int retryCount = 0;

        do {
            try {
                result = ic.proceed();
                failed = false;
            } catch (Exception e) {
                retryCount++;
                if (retryCount > retryThreshold) {
                    throw e;
                }
            }

        } while (failed && retryCount <= retryThreshold);

        return result;
    }
}
