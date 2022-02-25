<p>
    It is not safe to keep session based object as a field in <a href="https://sling.apache.org/apidocs/sling11/index.html">Servlets</a>,
    <a href="https://sling.apache.org/apidocs/sling11/index.html">Filters</a> or <a href="https://sling.apache.org/documentation/bundles/apache-sling-eventing-and-job-handling.html">EventHandlers</a> .
    As well as any Declarative Services component. Rule checks for the occurrence of any instance or static fields of following types:
    <ul>
        <li>org.apache.sling.api.resource.ResourceResolver</li>
        <li>javax.jcr.Session</li>
        <li>com.day.cq.wcm.api.PageManager</li>
    </ul>
</p>

| Additional Information |          |
|------------------------|----------|
| Severity               | Critical | 
| Estimated time to fix  | 15 min   |


<h2>Noncompliant Code Example</h2>
<pre>
@Component(service = Servlet.class, immediate = true)
@SlingServletResourceTypes(resourceTypes = "resourceType", selectors = "selector")
public class ComponentsServlet extends SlingSafeMethodsServlet {

    public static final ResourceResolver staticResolver;

    private ResourceResolver resolver;

    public ResourceResolver publicResolver;

    private PageManager pageManager;

    private Session session;
}

@Component(immediate = true, service = SlingComponentClass.class)
@Designate(ocd = SlingComponentClass.class)
public class ComponentsFilterImpl implements Filter {

    private PageManager pageManager;
}
</pre>

[![Back to overview](back.svg)](../../README.md)