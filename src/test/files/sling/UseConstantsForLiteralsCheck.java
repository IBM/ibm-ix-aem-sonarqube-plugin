/*-
 * #%L
 * AEM Rules for SonarQube
 * %%
 * Modification copyright (C) 2020-present IBM Corporation
 * Copyright (C) 2015-2019 Wunderman Thompson Technology
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 *
 * Modifications:
 * Code changes to align with Java 11 coding standards and AEM 6.5 .
 * Adjusting and adding unit test
 */
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.osgi.service.component.annotations.Component;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;

public class Constants {

    public static final String UNIQUE_CONSTANT = "limited in occurrence to a given class, situation, or area:";
    public static final String JCR_MODIFIED = "jcr:lastModified"; // Noncompliant {{Use available constant JCR_LASTMODIFIED from interface com.day.cq.commons.jcr.JcrConstants instead of hardcoded value.}}
    public static final String EVENT_TYPE = "EventType"; // Noncompliant {{Use available constant EVENT_TYPE from class com.adobe.granite.workflow.event.WorkflowEvent instead of hardcoded value.}}
    public static final String NT_DAM_ASSET;

    static {
        NT_DAM_CONTENT = "dam:AssetContent"; // Noncompliant {{Use available constant NT_DAM_ASSETCONTENT from interface com.day.cq.dam.api.DamConstants instead of hardcoded value.}}
    }

    public static String getConstant() {
        return "nt:unstructured"; // Noncompliant {{Use available constant NT_UNSTRUCTURED from interface com.day.cq.commons.jcr.JcrConstants instead of hardcoded value.}}
    }

}

@Component(service = Servlet.class, property = { ServletResolverConstants.SLING_SERVLET_PATHS + "=/bin/path/to/servlet",
  ServletResolverConstants.SLING_SERVLET_METHODS + "=" + "POST" // Noncompliant {{Use available constant METHOD_POST from class org.apache.sling.api.servlets.HttpConstants instead of hardcoded value.}}
})
public class TestServlet extends SlingAllMethodsServlet {

    @Override
    protected void doPost(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws ServletException {

    }

}

@Component(service = Servlet.class, property = { ServletResolverConstants.SLING_SERVLET_PATHS + "=/bin/path/to/servlet",
  ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET })
public class TestServlet2 extends SlingSafeMethodsServlet {

    private static final String JSON_MIMETYPE = "application/json"; // Noncompliant {{Use available constant APPLICATION_JSON javax.ws.rs.core.MediaType instead of hardcoded value.}}

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException {

    }

}

@Component
@Properties({
  @Property(label = "service-vendor",
    name = "service.vendor", // Noncompliant {{Use available constant SERVICE_VENDOR from interface org.osgi.framework.Constants instead of hardcoded value.}}
    value = "test",
    propertyPrivate = true),
  @Property(label = "service-ranking",
    name = "service.ranking", // Noncompliant {{Use available constant SERVICE_RANKING from interface org.osgi.framework.Constants instead of hardcoded value.}}
    value = "1",
    propertyPrivate = true)
})
public class TestComponentProperties {


}




