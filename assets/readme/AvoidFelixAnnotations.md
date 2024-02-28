<p>Felix annotations are deprecated, thus OSGi annotations (<i>org.osgi.service.component.annotations.*<i>) must be used instead.</p>

| Additional Information |                              |
|------------------------|------------------------------|
| _Severity - deprecated_| Critical                     | 
| Security impact        | :heavy_minus_sign:           |
| Reliability impact     | :heavy_minus_sign:           |
| Maintainability impact | $\color{red}{\textsf{High}}$ |
| Estimated time to fix  | 15 min                       |
| Attribute              | Conventional                 |

<h2>Noncompliant Code Example</h2>

```java
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

@Component(name = "Test Service", immediate = true, description = "Description")
@Service(TestService.class)
public class TestService {

    public String getName() {
        return "testService";
    }
    
}
```

<h2>Compliant Solution</h2>

```java
import org.osgi.service.component.annotations.Component;

@Component(name = "Test Service", service = TestService.class, immediate = true)
public class TestService {

    public String getName() {
        return "testService";
    }
    
}
```

[![Back to overview](back.svg)](../../README.md)
