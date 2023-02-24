<p>Use available AEM or JAVA constants instead of inline literals.

| Additional Information |       |
|------------------------|-------|
| Severity               | Minor | 
| Estimated time to fix  | 5 min |

</p>
<h2>Noncompliant Code Example </h2>

```java
@Component(service = Servlet.class, property = {
  ServletResolverConstants.SLING_SERVLET_PATHS + "=/bin/path/to/servlet",
  ServletResolverConstants.SLING_SERVLET_METHODS + "=" + "POST"
})
public class ComponentServlet extends SlingAllMethodsServlet {
    
}
```

<h2>Compliant Solution</h2>

```java
@Component(service = Servlet.class, property = {
  ServletResolverConstants.SLING_SERVLET_PATHS + "=/bin/path/to/servlet",
  ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET
  })
public class ComponentServlet extends SlingAllMethodsServlet {
    
}
```

[![Back to overview](back.svg)](../../README.md)