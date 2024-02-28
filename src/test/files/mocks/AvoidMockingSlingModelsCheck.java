
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

import org.junit.jupiter.api.Test;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

import org.mockito.Mock;
import org.mockito.Mockito;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import lombok.Getter;


import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;


@Model(adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SlingModel {

    @Getter
    @ValueMapValue
    @Default(values = StringUtils.EMPTY)
    private String text;

}

@Model(adaptables = SlingHttpServletRequest.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SlingModel2 {

    @Getter
    @ValueMapValue
    @Default(values = StringUtils.EMPTY)
    private String text;

}

public class SlingModel3 extends SlingModel2 {


}

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class ExampleTest {

    @Mock
    private SlingModel slingModel; // Noncompliant {{Avoid mocking Sling models, adapt it from a context resource/request instead}}

    @Mock
    private SlingModel2 slingModel2; // Noncompliant {{Avoid mocking Sling models, adapt it from a context resource/request instead}}

}

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class ExampleTest2 {
    private void test() {
        SlingModel slingModel1 = Mockito.mock(SlingModel.class); // Noncompliant {{Avoid mocking Sling models, adapt it from a context resource/request instead}}
        SlingModel slingModelProper1 = resource.adaptTo(SlingModel.class); // Compliant

        SlingModel2 slingModel2 = Mockito.mock(SlingModel2.class); // Noncompliant {{Avoid mocking Sling models, adapt it from a context resource/request instead}}
        SlingModel2 slingModelProper2 = resource.adaptTo(SlingModel2.class); // Compliant

        Mockito.mock(SlingModel2.class); // Noncompliant {{Avoid mocking Sling models, adapt it from a context resource/request instead}}

        SlingModel2 slingModel2 = Mockito.mock(SlingModel3.class); // Noncompliant {{Avoid mocking Sling models, adapt it from a context resource/request instead}}

    }
}

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class ExampleTest3 {

    SlingModel slingModel;

    SlingModel2 slingModel2;

}

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class ExampleTest4 {

    SlingModel slingModel;

    SlingModel2 slingModel2;

}

class ExampleTest5 extends ExampleTest4 {

    private void test() {
        SlingModel slingModel1 = Mockito.mock(SlingModel.class); // Noncompliant {{Avoid mocking Sling models, adapt it from a context resource/request instead}}
        SlingModel slingModelProper1 = resource.adaptTo(SlingModel.class); // Compliant

        SlingModel2 slingModel2 = Mockito.mock(SlingModel2.class); // Noncompliant {{Avoid mocking Sling models, adapt it from a context resource/request instead}}
        SlingModel2 slingModelProper2 = resource.adaptTo(SlingModel2.class); // Compliant

        Mockito.mock(SlingModel2.class); // Noncompliant {{Avoid mocking Sling models, adapt it from a context resource/request instead}}

        SlingModel2 slingModel2 = Mockito.mock(SlingModel3.class); // Noncompliant {{Avoid mocking Sling models, adapt it from a context resource/request instead}}

    }
}
