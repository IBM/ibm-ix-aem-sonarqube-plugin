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

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.osgi.service.component.annotations.Reference;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import javax.servlet.Servlet;

import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;

import java.util.Map;

@Component(service = Servlet.class)
@SlingServletName(servletName = "User Report Servlet")
@SlingServletResourceTypes(resourceTypes = { UserReportServlet.SERVLET_RESOURCE_TYPE }, extensions = "json", methods = HttpConstants.METHOD_GET)
public class SlingServlet extends SlingAllMethodsServlet {

    @Override
    public void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws ServletException, IOException {
        try {
            final ResourceResolver requestResourceResolver = request.getResourceResolver();
        } catch (final RuntimeException e) {
            log.error("Error getting search results", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}

@Component(service = Servlet.class)
@SlingServletName(servletName = "User Report Servlet")
@SlingServletResourceTypes(resourceTypes = { UserReportServlet.SERVLET_RESOURCE_TYPE }, extensions = "json", methods = HttpConstants.METHOD_GET)
public class SlingServlet2 extends SlingAllMethodsServlet {

    @Override
    public void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws ServletException, IOException {
        final Resource resourceResolver = request.getResource();
        final ResourceResolver resolver = resource.getResourceResolver();
    }

}

@Component(service = Servlet.class)
@SlingServletName(servletName = "User Report Servlet")
@SlingServletResourceTypes(resourceTypes = { UserReportServlet.SERVLET_RESOURCE_TYPE }, extensions = "json", methods = HttpConstants.METHOD_GET)
public class SlingServlet3 extends SlingAllMethodsServlet {

    @Override
    public void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws ServletException, IOException {
        try {
            final Resource resourceResolver = request.getResource();
            final ResourceResolver resolver = resource.getResourceResolver();
        } catch (final RuntimeException e) {
            log.error("Error getting search results", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
class SlingModelClass {

    @Reference
    protected ResourceResolverFactory resourceResolverFactory;

    public void aMethod() {
        try (ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(Map.of(ResourceResolverFactory.SUBSERVICE, "serviceUser"))) {

        } catch (final LoginException ex) {
            System.out.error("Unable to log in service user", ex);
        }
    }

}

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
class SlingModelClass2 {

    @Reference
    protected ResourceResolverFactory resourceResolverFactory;

    public void aMethod() {
        try {
            ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(Map.of(ResourceResolverFactory.SUBSERVICE, "serviceUser")); // Noncompliant {{ResourceResolver should be closed using try-with-resources Java feature.}}
        } catch (final LoginException ex) {
            System.out.error("Unable to log in service user", ex);
        }
    }

}