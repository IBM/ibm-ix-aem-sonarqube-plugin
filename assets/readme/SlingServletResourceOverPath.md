<p>Whenever possible prefer using @SlingServletResourceTypes over @SlingServletPaths. If you need to use paths, make sure that the path you use is allowed in
    Apache Sling Servlet/Script Resolver configuration.

Make use of constants for defining servlet properties in @SlingServletResourceTypes annotation to improve code readability. 

| Additional Information |        |
|------------------------|--------|
| Severity               | Info   | 
| Estimated time to fix  | 10 min |

</p>

<h2>Noncompliant Code Example</h2>

```java
@Component(service = Servlet.class)
@SlingServletPaths("/bin/custom-servlet")
public class SlingServlet extends SlingSafeMethodsServlet {}
```

<h2>Compliant Solution</h2>

```java
@Component(service = Servlet.class, immediate = true)
@SlingServletResourceTypes(resourceTypes = "resourceType", selectors = "selector")
public class SlingServlet extends SlingSafeMethodsServlet {}
```

[![Back to overview](back.svg)](../../README.md)