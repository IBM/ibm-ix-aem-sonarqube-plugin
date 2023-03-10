<p>
    Always return the appropriate HTTP in a servlet response. The common ones are 200 - OK (default), 500 - Internal server error and 400 - Bad request. </p><p>
    Best practice is to wrap all the code in servlet method in a try-catch block and return 500 status code with logging any runtime exception. </p><p>
    Sling Servlet would return 500 HTTP code by default anyway, however it would also expose the complete stacktrace and Java/Sling version to the caller. 
    This is sensitive information, which we only want to log internally and maintain maximum security.</p>

| Additional Information |        |
|------------------------|--------|
| Severity               | Minor  | 
| Estimated time to fix  | 15 min |

<h2>Noncompliant Code Example</h2>

```java
@Component(service = Servlet.class)
@SlingServletName(servletName = "Custom servlet name")
@SlingServletResourceTypes(
  resourceTypes = {
    UserReportServlet.SERVLET_RESOURCE_TYPE
  },
  extensions = "json",
  methods = HttpConstants.METHOD_GET
)
public class CustomServlet extends SlingAllMethodsServlet {

    private static final Logger logger = LoggerFactory.getLogger(ClubOfferImportServlet.class);

    @Override
    public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        //Servlet logic
    }

}
```

<h2>Compliant Solution</h2>

```java
@Component(service = Servlet.class)
@SlingServletName(servletName = "Custom servlet name")
@SlingServletResourceTypes(
  resourceTypes = {
    UserReportServlet.SERVLET_RESOURCE_TYPE
  },
  extensions = "json",
  methods = HttpConstants.METHOD_GET
)
public class CustomServlet extends SlingAllMethodsServlet {

    private static final Logger logger = LoggerFactory.getLogger(ClubOfferImportServlet.class);

    @Override
    public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        try {
            //Servlet logic
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (RuntimeException ex) {
            logger.error("Could not import offer data.", ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
```

[![Back to overview](back.svg)](../../README.md)