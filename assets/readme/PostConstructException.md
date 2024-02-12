<p>
    It is recommended to wrap all the code in the @PostConstruct method inside a try-catch block in order to catch any runtime exceptions that could occur while adapting
    the resource/request. </p>

| Additional Information |                                   |
|------------------------|-----------------------------------|
| _Severity - deprecated_| Minor                             | 
| Security impact        | $\color{green}{\textsf{Low}}$     |
| Reliability impact     | $\color{orange}{\textsf{Medium}}$ |
| Maintainability impact | :heavy_minus_sign:                |
| Estimated time to fix  | 5 min                             |
| Attribute              | Conventional                      |

<p>We do this to properly log any runtime exception that might occur in a component, for example a null-pointer exception. Also, using this strategy we avoid
    showing unfriendly exceptions and their stacktrace to our end users.
</p>
<p>
    Creating your own exception classes is still the best practice in the business layer (OSGi services). However, in the presentation layer, it is enough to
    catch and log any runtime and non-generic checked exceptions.
</p>

<h2>Noncompliant Code Example</h2>

```java
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
class SlingModelClass {

    @OSGiService
    private PageManagerFactory pageManagerFactory;

    @SlingObject
    private Resource resource;

    @PostConstruct
    public void init() {
        PageManager pageManager = pageManagerFactory.getPageManager(resourceResolver);
        Asset asset = DamUtil.resolveToAsset(resource);
    }
    
}
```

<h2>Compliant Solution</h2>

```java
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
class SlingModelClass {

    @OSGiService
    private PageManagerFactory pageManagerFactory;

    @SlingObject
    private Resource resource;

    @PostConstruct
    public void init() {
        try {
            PageManager pageManager = pageManagerFactory.getPageManager(resourceResolver);
            Asset asset = DamUtil.resolveToAsset(resource);
        } catch (RuntimeException ex) {
            logger.error("Exception in post construct", ex);
        }
    }
    
}
```

[![Back to overview](back.svg)](../../README.md)
