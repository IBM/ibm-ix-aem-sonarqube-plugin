<p>Use <i>HttpServletResponse</i> class constants for Http response codes instead of using hardcoded numbers. 

| Additional Information |                               |
|------------------------|-------------------------------|
| _Severity - deprecated_| Minor                         | 
| Security impact        | :heavy_minus_sign:            |
| Reliability impact     | :heavy_minus_sign:            |
| Maintainability impact | $\color{green}{\textsf{Low}}$ |
| Estimated time to fix  | 5 min                         |
| Attribute              | Conventional                  |

<h2>Noncompliant Code Example </h2>

```java
try {
    response.setStatus(200);
} catch (final Exception ex) {
    log.error("An error occurred", ex)
    response.setStatus(500);
}
```
<h2>Compliant Solution</h2>

```java
try {
    response.setStatus(HttpServletResponse.SC_OK);
} catch (final Exception ex) {
    log.error("An error occurred", ex)
    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
}
```

[![Back to overview](back.svg)](../../README.md)
