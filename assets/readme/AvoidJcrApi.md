<p>Sling APIs have the added benefit of being built for extension, which means it is often easier and safer to augment behaviour of applications built using
    Sling APIs rather than the less extensible JCR APIs.</p>

| Additional Information |                               |
|------------------------|-------------------------------|
| _Severity - deprecated_| Info                          | 
| Security impact        | :heavy_minus_sign:            |
| Reliability impact     | :heavy_minus_sign:            |
| Maintainability impact | $\color{green}{\textsf{Low}}$ |
| Estimated time to fix  | 30 min                        |
| Attribute              | Conventional                  |

<ul>
    <li>Accessing JCR nodes as Sling Resources and accessing their data via <i>ValueMaps</i></li>
    <li>Providing security context via the <i>ResourceResolver</i></li>
    <li>Creating and removing resources via <i>ResourceResolver's</i> create/move/copy/delete methods</li>
    <li>Updating properties via the <i>ModifiableValueMap</i></li>
</ul><p>Avoid using JCR API calls from Session or Node objects, as well utility classes <i>JcrUtil</i> and <i>JcrUtils</i> (unless needed
    functionality is not provided in the Sling API)</p>
<h2>Noncompliant Code Example</h2>

```java
Node node = session.getNode(nodePath);
node.setProperty("property", propertyValue);
```
<h2>Compliant Solution</h2>

```java
Resource resource = this.resourceResolver.getResource(resourcePath);
ModifiableValueMap properties = resource.adaptTo(ModifiableValueMap.class);
properties.put("property", propertyValue);
```

[![Back to overview](back.svg)](../../README.md)
