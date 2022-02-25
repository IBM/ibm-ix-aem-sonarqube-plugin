<p>Use available AEM or JAVA constants instead of inline literals.

| Additional Information |       |
|------------------------|-------|
| Severity               | Minor | 
| Estimated time to fix  | 5 min |

</p>
<h2>Noncompliant Code Example </h2>
<pre>
@Component(service = Servlet.class, property = {
  ServletResolverConstants.SLING_SERVLET_PATHS + "=/bin/path/to/servlet",
  ServletResolverConstants.SLING_SERVLET_METHODS + "=" + "POST"
})
public class ComponentServlet extends SlingAllMethodsServlet {
}

</pre><h2>Compliant Solution</h2>
<pre>
@Component(service = Servlet.class, property = {
  ServletResolverConstants.SLING_SERVLET_PATHS + "=/bin/path/to/servlet",
  ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET
  })
public class ComponentServlet extends SlingAllMethodsServlet {
}
</pre>

[![Back to overview](back.svg)](../../README.md)