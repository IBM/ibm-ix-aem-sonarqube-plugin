<p>Avoid <a href="https://sling.apache.org/apidocs/sling10/org/apache/sling/jcr/api/SlingRepository.html#loginAdministrative-java.lang.String-">Sling
    Repository</a> _loginAdministrative_ and
    <a href="https://sling.apache.org/apidocs/sling11/org/apache/sling/api/resource/ResourceResolverFactory.html#getAdministrativeResourceResolver-java.util.Map-">ResourceResolverFactory</a>
    _getAdministrativeResourceResolver_ methods. They are deprecated and a security risk.
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
    private SlingRepository repository;

    public void aMethod() {
        Session session = null;
        try {
            session = repository.loginAdministrative(null);
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
