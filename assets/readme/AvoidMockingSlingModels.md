<p>When writing tests with <a href="https://wcm.io/testing/aem-mock/">aem-mocks</a> it is not a good practice to mock Sling models, but rather to adapt them from
    a resource or request (depending on the adaptable type) as it would usually happen in normal runtime. By doing it that
    way, you will also have the dependency injection automatically handled based on the services and content
    already present in the test context.</p>

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
    private ExampleSlingModel exampleSlingModel;

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
        // Register required services and load content
        ExampleSlingModel exampleSlingModel = aemContext.currentResource().adaptTo(ExampleSlingModel.class);
        ...
    }
}
```


<p>
Since the adaptTo method doesn't throw an exception in case an issue occurs, but rather just returns null, it can be sometimes hard to troubleshoot why the Sling model
is not being properly adapted in the test. A good replacement for this is using the ModelFactory class to perform the adaption, which will in case of failure provide
a detailed response (for example if a particular required dependency is missing and cannot be injected).
</p>

<p>
The following code snippet shows how to use ModelFactory for adaption (replace the request object with a resource in case it uses a different adaptable type)
</p>

```java
aemContext.getService(ModelFactory.class).createModel(aemContext.request(), CheckoutProgressModel.class);
```


[![Back to overview](back.svg)](../../README.md)
