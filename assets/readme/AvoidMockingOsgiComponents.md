<p>When writing tests with <a href="https://wcm.io/testing/aem-mock/">aem-mocks</a> avoid mocking the implementation of a OSGi component, but
    rather create a stub implementation or mock the interface of the component instead if really needed.
</p>

| Additional Information |        |
|------------------------|--------|
| Severity               | Minor  | 
| Estimated time to fix  | 15 min |


<h2>Noncompliant Code Example</h2>

```java
@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class TestClass {

    @Mock
    private DummyServiceImpl dummyServiceImpl;

    @Test
    void testDummmy() {
        ...
    }
}
```
<h2>Compliant Solution</h2>

```java
@ExtendWith(AemContextExtension.class)
class TestClass {

    @Test
    void testDummmy() {
        AemContext aemContext = new AemContextBuilder().build();
        aemContext.registerService(DummyService.class, new DummyServiceMock());
        ...
    }
}
```

<p>or</p>

```java
@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class TestClass {

    @Mock
    private DummyService dummyService;

    @Test
    void testDummmy() {
        ...
    }
}
```

[![Back to overview](back.svg)](../../README.md)
