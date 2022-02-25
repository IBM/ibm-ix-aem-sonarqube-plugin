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
 * Code changes to align with Java 11 coding standards.
 * Additional test cases.
 */

import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.event.EventHandler;
import org.osgi.service.event.Event;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.felix.scr.annotations.sling.SlingFilter;
import org.apache.felix.scr.annotations.sling.SlingFilterScope;

import javax.jcr.Session;

import com.day.cq.wcm.api.PageManager;
import com.day.cq.tagging.TagManager;

@Component(immediate = true, service = SlingModelClass.class)
@Designate(ocd = SlingModelClass.class)
class SlingModelClassImpl {

    private Session session; // Noncompliant {{Usage of javax.jcr.Session as a field is not thread safe.}}

    public void doit() {
        // init
    }

}

@Component(immediate = true, service = SlingModelClass.class)
@Designate(ocd = SlingModelClass.class)
class SlingModelClassImpl2 {

    private PageManager pageManager; // Noncompliant {{Usage of com.day.cq.wcm.api.PageManager as a field is not thread safe.}}

    public void doit() {
        // init
    }

}

@Component(immediate = true, service = SlingModelClass.class)
@Designate(ocd = SlingModelClass.class)
class SlingModelClassImpl3 {

    private TagManager tagManager; // Noncompliant {{Usage of com.day.cq.tagging.TagManager as a field is not thread safe.}}

    public void doit() {
        // init
    }

}

@Component(immediate = true, service = SlingModelClass.class)
@Designate(ocd = SlingModelClass.class)
class SlingModelClassImpl4 {

    private ResourceResolver resourceResolver; // Noncompliant {{Usage of org.apache.sling.api.resource.ResourceResolver as a field is not thread safe.}}

    public void doit() {
        // init
    }

}

class EventHandlerClassImpl implements EventHandler {

    private Session session; // Noncompliant {{Usage of javax.jcr.Session as a field is not thread safe.}}

    @Override
    public void handleEvent(Event event) {
        // init
    }

}

@SlingFilter(scope = SlingFilterScope.COMPONENT, order = Integer.MAX_VALUE, metatype = true, label = "label", description = "description")
class FilterClassImpl implements Filter {

    private PageManager pageManager; // Noncompliant {{Usage of com.day.cq.wcm.api.PageManager as a field is not thread safe.}}

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // init
    }

    @Override
    public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain) throws IOException, ServletException {
        // filter
    }

    @Override
    public void destroy() {
        // nothing to do
    }

}

@Component(service = Servlet.class, immediate = true)
@SlingServletResourceTypes(resourceTypes = "resourceType", selectors = "selector")
class SlingServletClassImpl extends SlingSafeMethodsServlet {

    private Session session; // Noncompliant {{Usage of javax.jcr.Session as a field is not thread safe.}}

    @Override
    protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response) {

    }

}


