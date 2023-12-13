<p>
When using a service <a href="https://sling.apache.org/apidocs/sling10/org/apache/sling/api/resource/ResourceResolver.html">ResourceResolver</a> (initialized via the <i>resourceResolverFactory.getServiceResourceResolver</i> method), you have to make sure it gets closed. Best practice is to use the <a href="https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html">try-with-resources</a> feature of Java 8+ that will close the resource resolver automatically since it implements the <a href="https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/AutoCloseable.html">Autocloseable</a> interface.

Please do not confuse service resource resolver (which uses a service user session), with request resource resolver (<i>request.getResourceResolver</i>, which uses the logged in user session), as the second one must not be closed manually (handed by Sling).

</p>

| Additional Information |          |
|------------------------|----------|
| Severity               | Critical |
| Estimated time to fix  | 15 min   |

<h2>Noncompliant Code Example</h2>

```java
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
class SlingModelClass {

    @Reference
    protected ResourceResolverFactory resourceResolverFactory;

    public void aMethod() {
        try (
          ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(Map.of(ResourceResolverFactory.SUBSERVICE, "serviceUser"))) {
        } catch (final LoginException ex) {
            log.error("Unable to log in service user", ex);
        }
    }

}
```

<h2>Compliant Solution</h2>

```java
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
class SlingModelClass {

    @Reference
    protected ResourceResolverFactory resourceResolverFactory;

    public void aMethod() {
        try (ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(Map.of(ResourceResolverFactory.SUBSERVICE, "serviceUser"))) {
            //Custom logic
        } catch (final LoginException ex) {
            log.error("Unable to log in service user", ex);
        }
    }

}
```

[![Back to overview](back.svg)](../../README.md)
