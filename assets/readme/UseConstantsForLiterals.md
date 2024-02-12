<p>Use available AEM or JAVA constants instead of inline literals.

| Additional Information |                               |
|------------------------|-------------------------------|
| _Severity - deprecated_| Minor                         | 
| Security impact        | :heavy_minus_sign:            |
| Reliability impact     | :heavy_minus_sign:            |
| Maintainability impact | $\color{green}{\textsf{Low}}$ |
| Estimated time to fix  | 5 min                         |
| Attribute              | Conventional                  |

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
