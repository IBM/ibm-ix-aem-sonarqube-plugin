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

import org.apache.sling.models.annotations.Model;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;

@Model(adaptables = Resource.class) // Noncompliant {{Missing defaultInjectionStrategy parameter}}
class SlingModel1 {

    public void aMethod() {

    }

}

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.REQUIRED)
class SlingModel2 {

    public void aMethod() {

    }

}

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
class SlingModel3 {

    public void aMethod() {

    }

}

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
class SlingModel4 {

    public void aMethod() {

    }

}

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.REQUIRED) // Noncompliant {{It is recommended to use OPTIONAL injection strategy when working with or adapting Request objects in a Sling model}}
class SlingModel5 {

    public void aMethod() {

    }

}

@Model(adaptables = SlingHttpServletRequest.class) // Noncompliant {{Missing defaultInjectionStrategy parameter}}
class SlingModel6 {

    public void aMethod() {

    }

}

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.REQUIRED) // Noncompliant {{It is recommended to use OPTIONAL injection strategy when working with or adapting Request objects in a Sling model}}
class SlingModel7 {

    @SlingObject
    private SlingHttpServletRequest request;

    public void aMethod() {

    }

}
