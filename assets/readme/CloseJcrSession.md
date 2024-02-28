<p>When a JCR <a href="https://www.adobe.io/experience-manager/reference-materials/spec/jsr170/javadocs/jcr-2.0/javax/jcr/Session.html">Session</a>
    is received via the <a href="https://sling.apache.org/apidocs/sling10/org/apache/sling/jcr/api/SlingRepository.html">SlingRepository</a>, it
    should always be closed.
</p>

| Additional Information |                              |
|------------------------|------------------------------|
| _Severity - deprecated_| Critical                     | 
| Security impact        | :heavy_minus_sign:           |
| Reliability impact     | $\color{red}{\textsf{High}}$ |
| Maintainability impact | :heavy_minus_sign:           |
| Estimated time to fix  | 15 min                       |
| Attribute              | Complete                     |

<h2>Noncompliant Code Example</h2>

```java
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
class SlingModelClass {

    @Reference
    private SlingRepository repository;

    public void aMethod() {
        Session session = null;

        try {
            session = repository.loginService(null, null);
        } catch (final LoginException | RepositoryException ex) {
            log.error("Unable to open repository session", ex);
        }
    }
    
}
```

<h2>Compliant Solution</h2>

```java
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
class SlingModelClass {

    @Reference
    private SlingRepository repository;

    public void aMethod() {
        Session session = null;
        try {
            session = repository.loginService(null, null);
        } catch (final LoginException | RepositoryException ex) {
            log.error("Unable to open repository session", ex);
        } finally {
            if (session != null && session.isLive()) {
                session.logout();
            }
        }
    }
    
}
```

[![Back to overview](back.svg)](../../README.md)
