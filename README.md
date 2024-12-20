[<img alt="IBM iX logo" height="155" src="assets/ibmix-logo.png" width="430"/>](https://www.ibm.com/services/ibmix)

# IBM iX Sonar rules for AEM development

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=ibmix-aem-sonar-rules&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=ibmix-aem-sonar-rules)

## Contents
This repository contains free-to-use custom Sonar rules designed specifically for AEM development based on internal IBM iX company guidelines.
[Adobe Experience Manager (AEM)](https://www.adobe.io/apis/experiencecloud/aem.html), is a comprehensive content management solution for building websites, mobile apps and forms.
The rules can be easily added to [SonarQube](https://www.sonarqube.org/) and used on your AEM project to help improve the overall code quality. Currently, only Java specific rules are in scope of this project. 


## Installation
### Manual
1. Download the latest _jar_ file from the releases section
2. Place it into your SonarQube installation directory under `/extensions/plugins`
3. Restart the SonarQube instance

### Sonar Marketplace 
(available only in the SonarQube Community version)
1. Using the SonarQube Administration panel open the Marketplace section
2. Under the plugins subsection, search for `IBM iX AEM Sonar rules` and press install
3. Restart the SonarQube instance

## Compatiblity
The newest version of this plugin (v1.4.x) only supports SonarQube versions starting from v9.9. If you are using older versions of SonarQube, v1.2 of this plugin can be used for SonarQube v8.9 or newer, although with some known issues that were fixed in the latest version.

Note: since this plugin is written in Java 11, you must make sure not to run the Sonar scanner using an older Java version (usually inside the CI/CD pipeline). This does not impact the code itself that is being analysed (which can use any Java version). This is only relevant if you are using SonarQube v8.9 as all newer versions no longer support running scanner with Java 8. 

## How to use
Rules provided by this plugin should work out-of-the-box.
In order to use them, you should add the rules to the quality profile that is used for analysis on your project.

## Rules
Here you can find the complete list of rules available in this plugin.
More detailed descriptions of the rules can be found when opening the rules in the SonarQube interface.
All the rules can be found in the `IBM iX AEM rules` repository when using the SonarQube interface.

1. **[AvoidDeprecatedAdministrative](assets/readme/AvoidDeprecatedAdministrative.md)** - _Avoid administrative access_  
   - Use Sling Repository login service instead 
  
2. **[AvoidFelixAnnotations](assets/readme/AvoidFelixAnnotations.md)** - _Avoid Felix annotations_
   - Use OSGi annotations instead of deprecated Felix annotations.
   
3. **[AvoidJcrApi](assets/readme/AvoidJcrApi.md)** - _Avoid JCR Api_
   - Prefer to use Sling APIs instead of JCR ones when possible
   
4. **[AvoidOldSlingServletAnnotations](assets/readme/AvoidOldSlingServletAnnotations.md)** - _Avoid old Sling Servlet annotations_
   - Prefer usage of the newer Sling servlet OSGi DS 1.4 (R7) component property type annotations

5. **[AvoidWcmUsePojoClass](assets/readme/AvoidWcmUsePojoClass.md)** - _Avoid `WCMUsePojo` class_
   - Prefer using Sling models in favor of WCMUsePojo 
   
6. **[CloseJcrSession](assets/readme/CloseJcrSession.md)** - _Close `Session` class_
    - Make sure to always close manually opened Sessions    

7. **[DefaultInjectionStrategy](assets/readme/DefaultInjectionStrategy.md)** - _Explicitly set `defaultInjectionStrategy` parameter in Sling models_
   - Argument `defaultInjectionStrategy` should be explicitly set in the Sling model annotation, and in certain cases set to `DefaultInjectionStrategy.OPTIONAL`

8. **[FilterException](assets/readme/FilterException.md)** - _Log exceptions in Sling filters_
   - Catch and log any runtime exceptions your custom code might throw in the filter. That way, we ensure that the filter chain will continue, even if your code is failing.

9. **[PostConstructException](assets/readme/PostConstructException.md)** - _Log exceptions in `@PostConstruct`_
   - Wrap all the code in the `@PostConstruct` method via try-catch in order to catch any runtime exceptions that could occur while adapting the resource/request.

10. **[PreferInjectionOverAdaption](assets/readme/PreferInjectionOverAdaption.md)** - _Prefer object injection or utility/factory services over adaption when possible_
    - When possible, prefer using object injection or utility/factory services instead of adapting the object.
    
11. **[SeparateConfigurationClass](assets/readme/SeparateConfigurationClass.md)** - _Separate OSGi configuration class from the implementation classes_
    - Put OSGi configuration in a separate class and then use `@Designate(ocd = CustomServiceConfig.class)` to load it into an implementation class.

12. **[SlingServletException](assets/readme/SlingServletException.md)** - _Log exceptions in Sling servlets and set response codes_
    - Wrap all the code from a servlet method in a try-catch block and return status code 500 - Internal server error and log any runtime exception.
    - Always return the appropriate HTTP status code in a servlet response.

13. **[SlingServletResourceOverPath](assets/readme/SlingServletResourceOverPath.md)** - _Prefer binding Sling Servlets with resource type instead of using a path_
    - Whenever possible prefer using `@SlingServletResourceTypes` annotations over `@SlingServletPaths`.

14. **[ThreadSafeObjects](assets/readme/ThreadSafeObjects.md)** - _Avoid member variables which are not thread safe
    - Make sure to never use classes, which are not thread safe, as class member variables.

15. **[TryWithResourcesResourceResolver](assets/readme/TryWithResourcesResourceResolver.md)** - _Use `try-with-resources` when instantiating `ResourceResolver`_  
    - When instantiating a `ResourceResolver` from the `ResourceResolverFactory` always use the `try-with-resources` feature.

16. **[UseConstantsForHttpCodes](assets/readme/UseConstantsForHttpCodes.md)** - _Use constants for Http response codes_
    - Use `HttpServletResponse` class constants for Http response codes instead of using hardcoded numbers
    
17. **[UseConstantsForLiterals](assets/readme/UseConstantsForLiterals.md)** - _Use provided constants for String literals_
    - Use available constants over String literals or self-declared constants.    

18. **[UseInjectorSpecificAnnotations](assets/readme/UseInjectorSpecificAnnotations.md)** - _Use injector specific annotations_
    - Instead of using the `@Inject` annotation for every object, it is recommended to use injector-specific annotations

19. **[AvoidInjectMocksUsage](assets/readme/AvoidInjectMocksUsage.md)** - _Avoid using Mockito `InjectMocks` in tests_
    - Register services into the AEM context instead of using Mockito `InjectMocks`.

20. **[AvoidMockingOsgiComponents](assets/readme/AvoidMockingOsgiComponents.md)** - _Avoid mocking OSGi component implementations in tests_
    - Avoid mocking OSGi component implementations, create a stub implementation or mock the interface instead

21. **[AvoidMockingRequestAndResponse](assets/readme/AvoidMockingRequestAndResponse.md)** - _Avoid mocking Sling request and response objects in tests_
    - Instead of mocking Sling request and response objects, use the ones available from the mocked context.

22. **[AvoidMockingResourceHandlingObjects](assets/readme/AvoidMockingResourceHandlingObjects.md)** - _Avoid mocking resource handling objects in tests_
    - Avoid mocking `ResourceResolver` or `Session` objects as they already come provided in the context

23. **[AvoidMockingSlingModels](assets/readme/AvoidMockingSlingModels.md)** - _Avoid mocking Sling models in tests_
    - Adapt Sling models from a context resource/request instead of mocking them




## License
Copyright 2020-present IBM Corporation

This plugin is licensed under the Apache License, Version 2.0

A part of this plugin contains code from another open source project &ndash; [AEM-Rules-for-SonarQube](https://github.com/wttech/AEM-Rules-for-SonarQube)  
Files that contains reused parts retain the license from the original authors
