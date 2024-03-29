<p>When writing tests with aem-mocks it is preferred to use the provided AemContext to inject any dependencies into classes which are needed for testing rather than to use InjectMocks from Mockito.
This way you can also properly utilize all the out-of-the-box registered classes in the context (e.g. ResourceResolverFactory, ResourceResolver, Externalizer, XSSApi) 
</p>

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
    private Externalizer externalizer;

    @Mock
    private CustomService customService;

    @InjectMocks
    private LinkServiceImpl linkServiceImpl = new LinkServiceImpl();

    @Test
    void testDummmy() {
        ...
    }
}
```
<h2>Compliant Solution</h2>

```java
@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class TestClass {

    @Mock
    private CustomService customService;

    private LinkServiceImpl linkServiceImpl = new LinkServiceImpl();
    
    @Test
    void testDummmy() {
        AemContext aemContext = new AemContextBuilder().build();
        aemContext.registerService(CustomService.class, customService);
        aemContext.registerInjectActivateService(linkServiceImpl);
        ...
    }
}
```

<p>
Or an alternative approach could be to have a stubbed version of the object that needs to be mocked (in this case it is the "CustomService" class) with
a dummy implementation that returns static values or performs just rudimentary logic when returning a response.
The upside of such an approach is that you can reuse the stub in any future tests instead of duplicating the same when-then logic in various places.
</p>

```java
@ExtendWith(AemContextExtension.class)
class TestClass {

    private CustomService customService = new CustomServiceMock();

    private LinkServiceImpl linkServiceImpl = new LinkServiceImpl();
    
    @Test
    void testDummmy() {
        AemContext aemContext = new AemContextBuilder().build();
        aemContext.registerService(CustomService.class, customService);
        aemContext.registerInjectActivateService(linkServiceImpl);
        ...
    }
}
```

[![Back to overview](back.svg)](../../README.md)
