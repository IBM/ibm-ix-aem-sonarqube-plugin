<p>
    Many Sling projects want to be able to create model objects - POJOs which are automatically mapped from Sling objects, typically resources, but also request
    objects. Often these POJOs also use OSGi services and other common AEM objects, that can then be easily injected in Sling Models.
    Although Java Use Provider (WCMUsePojo classes) provided through bundles are faster to initialize and execute than Sling Models for similar code, the Sling
    Models Use Provider provides the following advantages: </p>
<ul>
    <li>Entirely annotation driven - "pure" POJOs</li>
    <li>Easy to extend from other Sling Models</li>
    <li>Code is reusable</li>
    <li>Simple setup for unit testing</li>

| Additional Information |          |
|------------------------|----------|
| Severity               | Critical | 
| Estimated time to fix  | 15 min   |

</ul><h2>Noncompliant Code Example</h2>

```java
public class MyModel extends WCMUsePojo { }
```

<h2>Compliant Solution</h2>

```java
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class MyModel {  }
```

[![Back to overview](back.svg)](../../README.md)
