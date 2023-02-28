<p>Sling APIs have the added benefit of being built for extension, which means it is often easier and safer to augment behaviour of applications built using
    Sling APIs rather than the less extensible JCR APIs.</p>

| Additional Information |        |
|------------------------|--------|
| Severity               | Info   | 
| Estimated time to fix  | 30 min |

<ul>
    <li>Accessing JCR nodes as Sling Resources and accessing their data via _ValueMaps_</li>
    <li>Providing security context via the _ResourceResolver_</li>
    <li>Creating and removing resources via _ResourceResolver's_ create/move/copy/delete methods</li>
    <li>Updating properties via the _ModifiableValueMap_</li>
</ul><p>Avoid using JCR API calls from Session or Node objects, as well utility classes _JcrUtil_ and _JcrUtils_ (unless needed
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
