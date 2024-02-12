<p>When possible, prefer using object injection or utility/factory services instead of adapting the object.</p>
<p>The <i>adaptTo</i> method is not recommended in this case as it does not throw exceptions, it simply returns <i>null</i> in case of failure.
</p>

| Additional Information |                               |
|------------------------|-------------------------------|
| _Severity - deprecated_| Info                          | 
| Security impact        | :heavy_minus_sign:            |
| Reliability impact     | :heavy_minus_sign:            |
| Maintainability impact | $\color{green}{\textsf{Low}}$ |
| Estimated time to fix  | 20 min                        |
| Attribute              | Conventional                  |

<h2>Noncompliant Code Example</h2>

```java
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
class SlingModelClass {

    @SlingObject
    private ResourceResolver resourceResolver;

    @SlingObject
    private Resource resource;

    @PostConstruct
    public void init() {
        try {
            PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
            Asset asset = resource.adaptTo(Asset.class);
        } catch (RuntimeException ex) {
            logger.error("Exception in post construct", ex);
        }
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
