<p>A JCR <a href="https://www.adobe.io/experience-manager/reference-materials/spec/jsr170/javadocs/jcr-2.0/javax/jcr/Session.html">Session</a>
    when received via the <a href="https://sling.apache.org/apidocs/sling10/org/apache/sling/jcr/api/SlingRepository.html">SlingRepository</a>
    should always be closed.
</p>

| Additional Information |          |
|------------------------|----------|
| Severity               | Critical | 
| Estimated time to fix  | 15 min   |

<h2>Noncompliant Code Example</h2>
<pre>
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
</pre><h2>Compliant Solution</h2>
<pre>
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
</pre>

[![Back to overview](back.svg)](../../README.md)