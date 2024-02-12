
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

import com.day.cq.commons.Externalizer;

import org.junit.jupiter.api.Test;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

import org.mockito.Mock;
import org.mockito.Mockito;
import io.wcm.testing.mock.aem.MockExternalizer;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class ExampleTest {
    @Mock
    private RandomModel randomModel;

    @Mock
    private MockExternalizer mockExternalizer; // Noncompliant {{Avoid mocking OSGi component implementations, create a stub implementation or mock the interface instead}}

    @Mock
    private Externalizer externalizer; // Compliant

    private void test(){
        Externalizer externalizer1 = Mockito.mock(Externalizer.class); // Compliant
        MockExternalizer mockExternalizer = Mockito.mock(MockExternalizer.class); // Noncompliant {{Avoid mocking OSGi component implementations, create a stub implementation or mock the interface instead}}
    }
}

class ExampleTest2 {
    @Mock
    private RandomModel randomModel;

    @Mock
    private MockExternalizer mockExternalizer;

    @Mock
    private Externalizer externalizer;

    private void test(){
        Externalizer externalizer1 = Mockito.mock(Externalizer.class);
        MockExternalizer mockExternalizer = Mockito.mock(MockExternalizer.class);
    }
}

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class ExampleTest3 {
}

@ExtendWith(MockitoExtension.class)
class ExampleTest4 extends ExampleTest3 {
    @Mock
    private RandomModel randomModel;

    @Mock
    private MockExternalizer mockExternalizer; // Noncompliant {{Avoid mocking OSGi component implementations, create a stub implementation or mock the interface instead}}

    @Mock
    private Externalizer externalizer; // Compliant

    private void test(){
        Externalizer externalizer1 = Mockito.mock(Externalizer.class); // Compliant
        MockExternalizer mockExternalizer = Mockito.mock(MockExternalizer.class); // Noncompliant {{Avoid mocking OSGi component implementations, create a stub implementation or mock the interface instead}}
    }
}
