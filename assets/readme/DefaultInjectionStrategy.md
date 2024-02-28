<p>Argument defaultInjectionStrategy should be explicitly set in the Sling model annotation</p>

<p>There are use cases where you may need to get a <i>Request</i> object inside a Sling Model or you want to adapt your Sling Model using a <i>SlingHttpServletRequest</i>
    object. In both cases it is recommended to set optional injection strategy as default (<i>DefaultInjectionStrategy.OPTIONAL</i>), otherwise all the injected fields
    are assumed to be required by Sling.</p>

| Additional Information |                               |
|------------------------|-------------------------------|
| _Severity - deprecated_| Minor                         | 
| Security impact        | :heavy_minus_sign:            |
| Reliability impact     | :heavy_minus_sign:            |
| Maintainability impact | $\color{green}{\textsf{Low}}$ |
| Estimated time to fix  | 5 min                         |
| Attribute              | Conventional                  |

<h2>Noncompliant Code Example</h2>

```java
@Model(adaptables = SlingHttpServletRequest.class)
public class MyModel {}
```

<h2>Compliant Solution</h2>

```java
@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class MyModel {}
```

[![Back to overview](back.svg)](../../README.md)
