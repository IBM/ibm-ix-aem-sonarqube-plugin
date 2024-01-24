/*
 * Copyright (C) 2020-present IBM Corporation
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package ix.ibm.sonar.java.utils;

public final class PackageConstants {

    public static final String SLING_MODEL_ANNOTATION = "org.apache.sling.models.annotations.Model";

    public static final String SLING_SERVLET_TYPE_SAFE = "org.apache.sling.api.servlets.SlingSafeMethodsServlet";
    public static final String SLING_SERVLET_TYPE_ALL = "org.apache.sling.api.servlets.SlingAllMethodsServlet";
    public static final String SLING_HTTP_SERVLET_RESPONSE = "org.apache.sling.api.SlingHttpServletResponse";
    public static final String SLING_HTTP_SERVLET_REQUEST = "org.apache.sling.api.SlingHttpServletRequest";
    public static final String SLING_ADAPTABLE = "org.apache.sling.api.adapter.Adaptable";
    public static final String SLING_VALUE_MAP = "org.apache.sling.api.resource.ValueMap";
    public static final String SLING_RESOURCE_RESOLVER = "org.apache.sling.api.resource.ResourceResolver";
    public static final String SLING_RESOURCE_RESOLVER_FACTORY = "org.apache.sling.api.resource.ResourceResolverFactory";

    public static final String SLING_SERVLET_RESOURCE_BIND_TYPE = "org.apache.sling.servlets.annotations.SlingServletResourceTypes";
    public static final String SLING_SERVLET_PATH_BIND_TYPE = "org.apache.sling.servlets.annotations.SlingServletPaths";

    public static final String FELIX_ANNOTATIONS = "org.apache.felix.scr.annotations";

    public static final String OSGI_OBJECT_CLASS_DEFINITION = "org.osgi.service.metatype.annotations.ObjectClassDefinition";
    public static final String OSGI_COMPONENT_ANNOTATION = "org.osgi.service.component.annotations.Component";

    public static final String JAVAX_HTTP_SERVLET_RESPONSE = "javax.servlet.http.HttpServletResponse";
    public static final String JAVAX_SERVLET_FILTER = "javax.servlet.Filter";
    public static final String JAVAX_SERVLET_EXCEPTION = "javax.servlet.ServletException";

    public static final String INJECT_ANNOTATION = "javax.inject.Inject";
    public static final String POST_CONSTRUCT_ANNOTATION = "javax.annotation.PostConstruct";

    public static final String JCR_NODE = "javax.jcr.Node";
    public static final String JCR_SESSION = "javax.jcr.Session";
    public static final String JCR_UTIL = "com.day.cq.commons.jcr.JcrUtil";
    public static final String JCR_UTILS = "org.apache.jackrabbit.commons.JcrUtils";

    public static final String WCM_USE_POJO = "com.adobe.cq.sightly.WCMUsePojo";

    public static final String PAGE_MANAGER = "com.day.cq.wcm.api.PageManager";
    public static final String ASSET = "com.day.cq.dam.api.Asset";
    public static final String EXTERNALIZER = "com.day.cq.commons.Externalizer";
    public static final String QUERY_BUILDER = "com.day.cq.search.QueryBuilder";

    public static final String IO_EXCEPTION = "java.io.IOException";
    public static final String EXCEPTION = "java.lang.Exception";

    public static final String AEM_CONTEXT_EXTENSION = "io.wcm.testing.mock.aem.junit5.AemContextExtension";
    public static final String EXTEND_WITH_ANNOTATION = "org.junit.jupiter.api.extension.ExtendWith";
    public static final String INJECT_MOCKS_ANNOTATION = "org.mockito.InjectMocks";
    public static final String MOCK_ANNOTATION = "org.mockito.Mock";
    public static final String MOCKITO_PACKAGE = "org.mockito.Mockito";

    private PackageConstants() {
    }

}
