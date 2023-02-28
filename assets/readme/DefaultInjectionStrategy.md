<p>Argument defaultInjectionStrategy should be explicitly set in the Sling model annotation</p>

<p>There are use cases where you may need to get a _Request_ object inside a Sling Model or you want to adapt your Sling Model using a _SlingHttpServletRequest_
    object. In both cases it is recommended to set optional injection strategy as default (_DefaultInjectionStrategy.OPTIONAL_), otherwise all the injected fields
    are assumed to be required by Sling.</p>

| Additional Information |       |
|------------------------|-------|
| Severity               | Minor | 
| Estimated time to fix  | 5 min |

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