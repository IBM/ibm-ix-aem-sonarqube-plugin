<p>When writing tests with <a href="https://wcm.io/testing/aem-mock/">aem-mocks</a> it is unnecessary to mock Sling request and response objects as they can be retrieved from the mock context and the
    current resource can be easily changed using the provided method calls. If more complicated behaviour needs to be tested that requires mockito functionality,
    the provided request and response objects can be spied by Mockito instead</p>

| Additional Information |        |
|------------------------|--------|
| Severity               | Major  | 
| Estimated time to fix  | 15 min |


<h2>Noncompliant Code Example</h2>

```java
@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class TestClass {

    @Mock
    private MockSlingHttpServletRequest request;

    @Mock
    private MockSlingHttpServletResponse response;

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
        MockSlingHttpServletRequest request = aemContext.request();
        MockSlingHttpServletResponse response = aemContext.response();
        ...
    }
}
```

[![Back to overview](back.svg)](../../README.md)
