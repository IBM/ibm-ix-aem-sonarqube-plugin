<p>Use HttpServletResponse class constants for Http response codes instead of using hardcoded numbers. 

| Additional Information |       |
|------------------------|-------|
| Severity               | Minor | 
| Estimated time to fix  | 5 min |
</p>

<h2>Noncompliant Code Example </h2>
<pre>
try {
    response.setStatus(200);
} catch (final Exception ex) {
    log.error("An error occurred", ex)
    response.setStatus(500);
}
</pre><h2>Compliant Solution</h2>
<pre>
try {
    response.setStatus(HttpServletResponse.SC_OK);
} catch (final Exception ex) {
    log.error("An error occurred", ex)
    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
}
</pre>

[![Back to overview](back.svg)](../../README.md)