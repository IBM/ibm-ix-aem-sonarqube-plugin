<p>When writing tests with <a href="https://wcm.io/testing/aem-mock/">aem-mocks</a> it is preferred not to mock resource handling objects, such as ResourceResolver or Session, as they are already provided
    in the test context. You can also specify the type of the ResourceResolver implementation you wish to use from the multiple ones provided by the
    aem-mocks library, depending on the level of functionality you need for the test environment. </p>

| Additional Information |                                   |
|------------------------|-----------------------------------|
| _Severity - deprecated_| Major                             | 
| Security impact        | :heavy_minus_sign:                |
| Reliability impact     | :heavy_minus_sign:                |
| Maintainability impact | $\color{orange}{\textsf{Medium}}$ |
| Estimated time to fix  | 15 min                            |
| Attribute              | Tested                            |

<h2>Noncompliant Code Example</h2>

```java
@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class TestClass {

    @Mock
    private ResourceResolver resourceResolver;

    @Mock
    private Session session;

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
        AemContext aemContext = new AemContextBuilder(ResourceResolverType.JCR_MOCK).build();
        ResourceResolver resourceResolver = aemContext.resourceResolver();
        Session session = resourceResolver.adaptTo(Session.class);
        ...
    }
}
```

[![Back to overview](back.svg)](../../README.md)
