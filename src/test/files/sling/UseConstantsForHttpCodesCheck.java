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

import javax.servlet.Servlet;
import org.apache.sling.api.SlingHttpServletResponse;

import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;

@Component(service = Servlet.class, immediate = true)
@SlingServletResourceTypes(resourceTypes = "resourceType", selectors = "selector")
public class CustomServlet extends SlingAllMethodsServlet {

    @Reference
    private transient EmailService emailService;

    @Override
    protected void doPost(final SlingHttpServletRequest request, final SlingHttpServletResponse response) {
        final List<String> failureList = this.emailService.sendEmail(templatePath, emailParams, TARGET_EMAIL_ADDRESS);

        if (failureList.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setStatus(200); // Noncompliant {{Use HttpServletResponse class constants for Http response codes}}
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setStatus(500); // Noncompliant {{Use HttpServletResponse class constants for Http response codes}}
        }

    }

}

@Component(service = Servlet.class, immediate = true)
@SlingServletResourceTypes(resourceTypes = "resourceType", selectors = "selector")
public class CustomServlet2 extends SlingAllMethodsServlet {

    private static final int NUMBER_OF_COMPONENTS_PER_JOB = 100;

    @Reference
    private transient EmailService emailService;

    @Override
    protected void doPost(final SlingHttpServletRequest request, final SlingHttpServletResponse response) {
        final List<String> failureList = this.emailService.sendEmail(templatePath, emailParams, TARGET_EMAIL_ADDRESS);

        if (failureList.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }

}