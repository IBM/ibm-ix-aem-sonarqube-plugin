<p>Instead of using the @Inject annotation for every object, it is recommended to use injector-specific annotations:</p>
<ul>
    <li> @ValueMapValue - Injects a ValueMap value. If via is not set, it will automatically take resource if the adaptable is the SlingHttpServletRequest</li>
    <li> @ChildResource - Injects a child resource by name. If via is not set, it will automatically take resource if the adaptable is the
        SlingHttpServletRequest. If name is not set the name is derived from the method/field name
    </li>
    <li> @RequestAttribute - Injects a request attribute by name. If name is not set the name is derived from the method/field name</li>
    <li> @OSGiService - Injects an OSGi service by type. The filter can be used give an OSGi service filter</li>
    <li> @Self - Injects the adaptable itself. If the field type does not match with the adaptable it is tried to adapt the adaptable to the requested type</li>
    <li> @SlingObject - Injects commonly used sling objects if the field matches with the class: request, response, resource resolver, current resource,
        SlingScriptHelper
    </li>
    <li>@ScriptVariable - Injects the script variable defined via Sling Bindings. If name is not set the name is derived from the method/field name</li>
    <li>@ContextAwareConfiguration - Injects the context-aware configuration object populated with data based on the path from which the Sling Model was adapted</li>
</ul>

| Additional Information |                                   |
|------------------------|-----------------------------------|
| _Severity - deprecated_| Major                             | 
| Security impact        | :heavy_minus_sign:                |
| Reliability impact     | $\color{orange}{\textsf{Medium}}$ |
| Maintainability impact | $\color{green}{\textsf{Low}}$     |
| Estimated time to fix  | 5 min                             |
| Attribute              | Efficient                         |

<h2>Noncompliant Code Example</h2>

```java
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CustomSlingModel {

    @Inject
    private Resource resource;

    @Inject
    private Page currentPage;

    @Inject
    private Externalizer externalizer;
    
}
```

<h2>Compliant Solution</h2>

```java
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CustomSlingModel {

    @SlingObject
    private Resource resource;

    @ScriptVariable
    private Page currentPage;

    @OSGiService
    private Externalizer externalizer;
    
}
```

[![Back to overview](back.svg)](../../README.md)
