
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

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class TestClass1 {

    @Mock
    private SlingHttpServletRequest request; // Noncompliant {{Avoid mocking SlingHttpServletRequest, use the one provided from the mocked context instead}}

    @Mock
    private SlingHttpServletResponse response; // Noncompliant {{Avoid mocking SlingHttpServletResponse, use the one provided from the mocked context instead}}

    @Mock
    private MockSlingHttpServletRequest request2; // Noncompliant {{Avoid mocking SlingHttpServletRequest, use the one provided from the mocked context instead}}

    @Mock
    private MockSlingHttpServletResponse response2; // Noncompliant {{Avoid mocking SlingHttpServletResponse, use the one provided from the mocked context instead}}
}


@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class TestClass2 {

    private SlingHttpServletRequest request;

    private SlingHttpServletResponse response;

    private MockSlingHttpServletRequest request2;

    private MockSlingHttpServletResponse response2;
}

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class TestClass3 {

    void test1() {
        SlingHttpServletRequest request = Mockito.mock(SlingHttpServletRequest.class); // Noncompliant {{Avoid mocking SlingHttpServletRequest, use the one provided from the mocked context instead}}

        SlingHttpServletResponse response = Mockito.mock(SlingHttpServletResponse.class); // Noncompliant {{Avoid mocking SlingHttpServletResponse, use the one provided from the mocked context instead}}

        MockSlingHttpServletRequest request2 = Mockito.mock(MockSlingHttpServletRequest.class); // Noncompliant {{Avoid mocking SlingHttpServletRequest, use the one provided from the mocked context instead}}

        MockSlingHttpServletResponse response2 = Mockito.mock(MockSlingHttpServletResponse.class); // Noncompliant {{Avoid mocking SlingHttpServletResponse, use the one provided from the mocked context instead}}
    }
}

@ExtendWith(MockitoExtension.class)
class TestClass4 {

    @Mock
    private SlingHttpServletRequest request;

    @Mock
    private SlingHttpServletResponse response;

    void test1() {
        SlingHttpServletRequest request = Mockito.mock(SlingHttpServletRequest.class);
        SlingHttpServletResponse response = Mockito.mock(SlingHttpServletResponse.class);
    }
}

@ExtendWith(AemContextExtension.class)
class TestClass5 {
}

@ExtendWith(MockitoExtension.class)
class TestClass6 extends TestClass5 {

    @Mock
    private SlingHttpServletRequest request; // Noncompliant {{Avoid mocking SlingHttpServletRequest, use the one provided from the mocked context instead}}

    @Mock
    private SlingHttpServletResponse response; // Noncompliant {{Avoid mocking SlingHttpServletResponse, use the one provided from the mocked context instead}}

    @Mock
    private MockSlingHttpServletRequest request2; // Noncompliant {{Avoid mocking SlingHttpServletRequest, use the one provided from the mocked context instead}}

    @Mock
    private MockSlingHttpServletResponse response2; // Noncompliant {{Avoid mocking SlingHttpServletResponse, use the one provided from the mocked context instead}}

    void test1() {
        SlingHttpServletRequest request = Mockito.mock(SlingHttpServletRequest.class); // Noncompliant {{Avoid mocking SlingHttpServletRequest, use the one provided from the mocked context instead}}
        SlingHttpServletResponse response = Mockito.mock(SlingHttpServletResponse.class); // Noncompliant {{Avoid mocking SlingHttpServletResponse, use the one provided from the mocked context instead}}
    }
}
