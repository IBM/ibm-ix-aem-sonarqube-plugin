/*
 * Copyright (C) 2020-present IBM Corporation
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "CustomSchedulerConfiguration", description = "Custom scheduler configuration")
public @interface CustomSchedulerConfiguration {

    @AttributeDefinition(name = "Scheduler name", description = "Name of the scheduler", type = AttributeType.STRING) public String schedulerName() default "Custom scheduler configuration";

}

@Component(service = TestService.class, immediate = true)
public class TestService {

    public String getName() {
        return "testService";
    }

}

@Component(service = TestService2.class, immediate = true)
public class TestService2 {

    @ObjectClassDefinition(name = "CustomSchedulerConfiguration2", description = "Custom scheduler configuration 2")
    public @interface CustomSchedulerConfiguration2 { // Noncompliant {{Move the OSGi configuration to a separate class}}

        @AttributeDefinition(name = "Scheduler name", description = "Name of the scheduler", type = AttributeType.STRING) public String schedulerName() default "Custom scheduler configuration 2";

    }

    public String getName() {
        return "testService2";
    }

}