<p>
    It is best practice to catch and log any runtime exceptions your custom code might throw in the filter. That way, we ensure that the filter chain will
    continue, even if our code is failing. </p><p>
    Also, make sure to keep the throws declaration for <i>IOException</i> and <i>ServletException</i>. Do not try to catch these exceptions, as that might cause unexpected
    behaviour. </p>

| Additional Information |                                   |
|------------------------|-----------------------------------|
| _Severity - deprecated_| Major                             | 
| Security impact        | :heavy_minus_sign:                |
| Reliability impact     | $\color{orange}{\textsf{Medium}}$ |
| Maintainability impact | :heavy_minus_sign:                |
| Estimated time to fix  | 10 min                            |
| Attribute              | Conventional                      |

<h2>Noncompliant Code Example</h2>

```java
@Override
public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain filterChain) throws IOException, ServletException {
    final HttpServletResponse httpResponse = (HttpServletResponse) response;
    httpResponse.addHeader("my-custom-header", "header-value");
    filterChain.doFilter(request, response);
}
```

<h2>Compliant Solution</h2>

```java
@Override
public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain filterChain) throws IOException, ServletException {
    try {
        final HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.addHeader("my-custom-header", "header-value");
    } catch (final RuntimeException e) {
        logger.error("Exception in request filter", e);
    }
    filterChain.doFilter(request, response);
}
```

[![Back to overview](back.svg)](../../README.md)
