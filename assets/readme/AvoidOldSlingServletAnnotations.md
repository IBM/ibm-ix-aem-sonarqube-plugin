<p>Use the new Sling servlet OSGi DS 1.4 (R7) component property type annotations that come with <i>org.apache.sling.servlets.annotations</i> - <i>@SlingServletResourceTypes</i> and <i>@SlingServletPaths</i>.
</p>

| Additional Information |        |
|------------------------|--------|
| Severity               | Major  | 
| Estimated time to fix  | 10 min |

<p>
Note - make sure you have the
<a href="https://github.com/apache/sling-org-apache-sling-servlets-annotations">Apache Sling Servlet Annotations</a>
Maven dependency added and that the maven-bundle-plugin dependency has been set to 4.1.0+ version for the new standard to be enabled.
More information available in the
<a href="https://sling.apache.org/documentation/the-sling-engine/servlets.html">documentation</a>
</p>
<h2>Noncompliant Code Example</h2>

```java
@Component(service = Servlet.class, immediate = true,
  property = { ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + "resourceType",
    ServletResolverConstants.SLING_SERVLET_SELECTORS + "=" + "selector" })
public class SlingServlet extends SlingSafeMethodsServlet {}
```
<h2>Compliant Solution</h2>

```java
@Component(service = Servlet.class, immediate = true)
@SlingServletResourceTypes(resourceTypes = "resourceType", selectors = "selector")
public class SlingServlet extends SlingSafeMethodsServlet {}
```

[![Back to overview](back.svg)](../../README.md)