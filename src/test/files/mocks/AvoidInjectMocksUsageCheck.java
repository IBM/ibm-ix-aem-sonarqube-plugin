
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
import org.junit.jupiter.api.extension.ExtendWith;

import com.day.cq.commons.Externalizer;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextBuilder;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;


import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class TestClass1 {

    @Mock
    private Externalizer externalizer;

    @Mock
    private CustomService customService;

    @InjectMocks // Noncompliant {{Avoid using Mockito InjectMocks, register services into the AEM context instead}}
    private LinkService linkService;
    @Test
    void testDummmy() {
    }

}


@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class TestClass2 {

    private AemContext aemContext;

    @Mock
    private CustomService customService;

    @BeforeAll
    static void setup() {
        AemContext aemContext = new AemContextBuilder().build();
        aemContext.registerService(CustomService.class, customService);
        aemContext.registerInjectActivateService(linkService);
    }

    @Test
    void testDummmy() {
    }

}


@ExtendWith(AemContextExtension.class)
class TestClass3 {

    private AemContext aemContext;

    private CustomService customService = new CustomServiceMock();

    @BeforeAll
    static void setup() {
        AemContext aemContext = new AemContextBuilder().build();
        aemContext.registerService(CustomService.class, customService);
        aemContext.registerInjectActivateService(linkService);
    }

    @Test
    void testDummmy() {
    }
}

@ExtendWith(MockitoExtension.class)
class TestClass4 {

    @Mock
    private Externalizer externalizer;

    @Mock
    private CustomService customService;

    @InjectMocks
    private LinkService linkService;
    @Test
    void testDummmy() {
    }

}
