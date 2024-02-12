
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

import org.mockito.Mock;
import org.mockito.Mockito;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.jcr.Session;
import org.apache.sling.api.resource.ResourceResolver;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class TestClass1 {

    @Mock
    private ResourceResolver resourceResolver; // Noncompliant {{Avoid mocking ResourceResolver, use the one provided in the context. If you need extended functionality, look into different mock resource resolver implementations}}

    @Mock
    private Session session; // Noncompliant {{Avoid mocking the Session object, adapt it from the ResourceResolver instead (look into different mock resource resolver implementations)}}

}


@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class TestClass2 {

    private ResourceResolver resourceResolver;

    private Session session;
}


@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class TestClass3 {

    private ResourceResolver resourceResolver = Mockito.mock(ResourceResolver.class); // Noncompliant {{Avoid mocking ResourceResolver, use the one provided in the context. If you need extended functionality, look into different mock resource resolver implementations}}

    private Session session = Mockito.mock(Session.class); // Noncompliant {{Avoid mocking the Session object, adapt it from the ResourceResolver instead (look into different mock resource resolver implementations)}}
}


@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class TestClass4 {

    void test1() {
        private ResourceResolver resourceResolver = Mockito.mock(ResourceResolver.class); // Noncompliant {{Avoid mocking ResourceResolver, use the one provided in the context. If you need extended functionality, look into different mock resource resolver implementations}}

        private Session session = Mockito.mock(Session.class); // Noncompliant {{Avoid mocking the Session object, adapt it from the ResourceResolver instead (look into different mock resource resolver implementations)}}
    }
}

@ExtendWith(AemContextExtension.class)
class TestClass5 {

    private ResourceResolver resourceResolver;

    private Session session;
}

class TestClass6 {

    void test1() {
        private ResourceResolver resourceResolver = Mockito.mock(ResourceResolver.class);

        private Session session = Mockito.mock(Session.class);
    }
}

@ExtendWith(AemContextExtension.class)
class TestClass7 {
}

class TestClass8 extends TestClass7 {

    private ResourceResolver resourceResolver = Mockito.mock(ResourceResolver.class); // Noncompliant {{Avoid mocking ResourceResolver, use the one provided in the context. If you need extended functionality, look into different mock resource resolver implementations}}

    private Session session = Mockito.mock(Session.class); // Noncompliant {{Avoid mocking the Session object, adapt it from the ResourceResolver instead (look into different mock resource resolver implementations)}}
}

