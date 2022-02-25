<p>Sling APIs have the added benefit of being built for extension, which means it is often easier and safer to augment behaviour of applications built using
    Sling APIs rather than the less extensible JCR APIs.</p>

| Additional Information |        |
|------------------------|--------|
| Severity               | Info   | 
| Estimated time to fix  | 30 min |

<ul>
    <li>Accessing JCR nodes as Sling Resources and accessing their data via ValueMaps</li>
    <li>Providing security context via the ResourceResolver</li>
    <li>Creating and removing resources via ResourceResolver's create/move/copy/delete methods</li>
    <li>Updating properties via the ModifiableValueMap</li>
</ul><p>Avoid using JCR API calls from Session or Node objects, as well utility classes JcrUtil and JcrUtils (unless needed
    functionality is not provided in the Sling API)</p>
<h2>Noncompliant Code Example</h2>
<pre>
Node node = session.getNode(nodePath);
node.setProperty("property", propertyValue);
</pre><h2>Compliant Solution</h2>
<pre>
Resource resource = this.resourceResolver.getResource(resourcePath);
ModifiableValueMap properties = resource.adaptTo(ModifiableValueMap.class);
properties.put("property", propertyValue);
</pre>

[![Back to overview](back.svg)](../../README.md)
