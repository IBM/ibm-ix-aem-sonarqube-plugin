<p>Put the OSGi configuration in a separate class and then use _@Designate(ocd = CustomServiceConfig.class)_ to load it into an implementation class.
    One additional benefit is that the configuration can be placed in a separate package and thus easily excluded from Sonar checks.</p>

| Additional Information |        |
|------------------------|--------|
| Severity               | Minor  | 
| Estimated time to fix  | 20 min |

<h2>Noncompliant Code Example</h2>
```java
@Component(
    service = ServiceClas.class,
    immediate = true,
    configurationPolicy = ConfigurationPolicy.OPTIONAL)
@Designate(ocd = ServiceClassImpl.Config.class)
class ServiceClassImpl implements ServiceClass {
    
    private String attribute;
    
    @Activate
    public activate(final ServiceClassImpl.Config config) {
       this.attribute = config.getAttribute();
    }
    
    @Override
    public void doSomething() {
        //Custom logic
    }
    
    @ObjectClassDefinition(name = "Service Class Configuration")
    protected @interface Config {
    @AttributeDefinition(
        name = "attribute",
        description = "Attribute description")
    String getAttribute() default StringUtils.EMPTY;
  }

}
```

[![Back to overview](back.svg)](../../README.md)