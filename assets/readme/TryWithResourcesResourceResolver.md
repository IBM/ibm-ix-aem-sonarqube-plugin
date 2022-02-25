<p>The <a href="https://sling.apache.org/apidocs/sling10/org/apache/sling/api/resource/ResourceResolver.html">ResourceResolver</a>
    should be initialised in <a href="https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html">try-with-resources</a>
    which ensures that it will be automatically closed because of the <a href="https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/AutoCloseable.html">Autocloseable</a> interface.

   Therefore, you should always initialise ResourceResolver in 'try-with-resources' block.
    
| Additional Information |          |
|------------------------|----------|
| Severity               | Critical |
| Estimated time to fix  | 15 min   |

</p>
<h2>Noncompliant Code Example</h2>
<pre>
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
class SlingModelClass2 {

  @Reference
  protected ResourceResolverFactory resourceResolverFactory;

  public void aMethod() {
    try {
      ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(Map.of(ResourceResolverFactory.SUBSERVICE, "serviceUser"));
    } catch (final LoginException ex) {
      System.out.error("Unable to log in service user", ex);
    } finally {
      if (resourceResolver != null) {
        resourceResolver.close();
      }
    }
  }
}
</pre>

<h2>Compliant Solution</h2>
<pre>
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
class SlingModelClass {

  @Reference
  protected ResourceResolverFactory resourceResolverFactory;

  public void aMethod() {
    try (ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(
      Map.of(ResourceResolverFactory.SUBSERVICE, "serviceUser"))) {

      } catch (final LoginException ex) {
        System.out.error("Unable to log in service user", ex);
      }
    }
  }
}
</pre>

[![Back to overview](back.svg)](../../README.md)