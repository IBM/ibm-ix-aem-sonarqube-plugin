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
import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletResponse;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;

@Component(service = Servlet.class)
@SlingServletName(servletName = "User Report Servlet")
@SlingServletResourceTypes(resourceTypes = { UserReportServlet.SERVLET_RESOURCE_TYPE }, extensions = "json", methods = HttpConstants.METHOD_GET)
public class TestServlet extends SlingAllMethodsServlet {

    private static final Logger logger = LoggerFactory.getLogger(TestServlet.class);

    @Override
    public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        try {
            final List<ResourceItem> resultItems = this.searchArticles(
              request.getResourceResolver(), this.getLocale(request), request.getParameter("query"));
            final Writer writer = response.getWriter();
            final Gson gson = new GsonBuilder().create();
            gson.toJson(resultItems, writer);
        } catch (RuntimeException ex) {
            logger.error("Could not import offer data.", ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        try {
            final List<ResourceItem> resultItems = this.searchArticles(
              request.getResourceResolver(), this.getLocale(request), request.getParameter("query"));
            final Writer writer = response.getWriter();
            final Gson gson = new GsonBuilder().create();
            gson.toJson(resultItems, writer);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (RuntimeException ex) {
            logger.error("Could not import offer data.", ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}

@Component(service = Servlet.class)
@SlingServletName(servletName = "User Report Servlet")
@SlingServletResourceTypes(resourceTypes = { UserReportServlet.SERVLET_RESOURCE_TYPE }, extensions = "json", methods = HttpConstants.METHOD_GET)
public class TestServlet2 extends SlingAllMethodsServlet {

    private static final Logger logger = LoggerFactory.getLogger(TestServlet.class);

    @Override
    public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        try {
            final List<ResourceItem> resultItems = this.searchArticles(
              request.getResourceResolver(), this.getLocale(request), request.getParameter("query"));
            final Writer writer = response.getWriter();
            final Gson gson = new GsonBuilder().create();
            gson.toJson(resultItems, writer);
        } catch (RuntimeException ex) { // Noncompliant {{Set response status to HTTP code 500}}
            logger.error("Could not import offer data.", ex);
        }
    }

    private Locale getLocale(final SlingHttpServletRequest request) {
        return Optional
                 .ofNullable(request.getResource().getParent())
                 .map(parent -> parent.adaptTo(Page.class))
                 .map(Page::getLanguage)
                 .orElse(request.getLocale());
    }

    @Override
    public void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        try {
            final List<ResourceItem> resultItems = this.searchArticles(
              request.getResourceResolver(), this.getLocale(request), request.getParameter("query"));
            final Writer writer = response.getWriter();
            final Gson gson = new GsonBuilder().create();
            gson.toJson(resultItems, writer);
        } catch (RuntimeException ex) { // Noncompliant {{Set response status to HTTP code 500}}
            logger.error("Could not import offer data.", ex);
        }
    }

    private List<ResourceItem> searchArticles(final ResourceResolver resourceResolver, final Locale locale, final String query) {
        final Session session = resourceResolver.adaptTo(Session.class);
        final PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        final SearchResult searchResult = this.pageSearchService.localizedFulltextSearch(session, locale, query, 0, 5);
        return this.populateResourceList(searchResult, pageManager);
    }

}

@Component(service = Servlet.class)
@SlingServletName(servletName = "User Report Servlet")
@SlingServletResourceTypes(resourceTypes = { UserReportServlet.SERVLET_RESOURCE_TYPE }, extensions = "json", methods = HttpConstants.METHOD_GET)
public class TestServlet3 extends SlingSafeMethodsServlet {

    private static final Logger logger = LoggerFactory.getLogger(TestServlet.class);

    @Override
    public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        try {
            final List<ResourceItem> resultItems = this.searchArticles(
              request.getResourceResolver(), this.getLocale(request), request.getParameter("query"));
            final Writer writer = response.getWriter();
            final Gson gson = new GsonBuilder().create();
            gson.toJson(resultItems, writer);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception ex) {  // Noncompliant {{Catch a non-generic exception}}
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            logger.error("Could not import offer data.", ex);
        }
    }

    @Override
    public void doHead(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        try {
            final List<ResourceItem> resultItems = this.searchArticles(
              request.getResourceResolver(), this.getLocale(request), request.getParameter("query"));
            final Writer writer = response.getWriter();
            final Gson gson = new GsonBuilder().create();
            gson.toJson(resultItems, writer);
        } catch (RuntimeException ex) { // Noncompliant {{Set response status to HTTP code 500}}
            logger.error("Could not import offer data.", ex);
        }
    }

}

@Component(service = Servlet.class)
@SlingServletName(servletName = "User Report Servlet")
@SlingServletResourceTypes(resourceTypes = { UserReportServlet.SERVLET_RESOURCE_TYPE }, extensions = "json", methods = HttpConstants.METHOD_GET)
public class TestServlet4 extends SlingAllMethodsServlet {

    private static final Logger logger = LoggerFactory.getLogger(TestServlet.class);

    @Override
    public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException { // Noncompliant {{Wrap all the code in a try-catch clause}}
        final List<ResourceItem> resultItems = this.searchArticles(
          request.getResourceResolver(), this.getLocale(request), request.getParameter("query"));
        final Writer writer = response.getWriter();
        final Gson gson = new GsonBuilder().create();
        gson.toJson(resultItems, writer);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    public void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException { // Noncompliant {{Wrap all the code in a try-catch clause}}
        final List<ResourceItem> resultItems = this.searchArticles(
          request.getResourceResolver(), this.getLocale(request), request.getParameter("query"));
        final Writer writer = response.getWriter();
        final Gson gson = new GsonBuilder().create();
        gson.toJson(resultItems, writer);
    }

}

@Component(service = Servlet.class)
@SlingServletName(servletName = "User Report Servlet")
@SlingServletResourceTypes(resourceTypes = { UserReportServlet.SERVLET_RESOURCE_TYPE }, extensions = "json", methods = HttpConstants.METHOD_GET)
public class TestServlet5 extends SlingAllMethodsServlet {

    private static final Logger logger = LoggerFactory.getLogger(TestServlet.class);

    @Override
    public void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        try {
            final List<ResourceItem> resultItems = this.searchArticles(request.getResourceResolver(), this.getLocale(request), request.getParameter("query"));
            final Writer writer = response.getWriter();
            final Gson gson = new GsonBuilder().create();
            gson.toJson(resultItems, writer);
        } catch (final RuntimeException ex) {
            log.debug("Error occured", ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}

@Component(service = Servlet.class)
@SlingServletName(servletName = "User Report Servlet")
@SlingServletResourceTypes(resourceTypes = { UserReportServlet.SERVLET_RESOURCE_TYPE }, extensions = "json", methods = HttpConstants.METHOD_GET)
public class TestServlet6 extends SlingAllMethodsServlet {

    private static final Logger logger = LoggerFactory.getLogger(TestServlet.class);

    @Override
    public void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        try {
            final List<ResourceItem> resultItems = this.searchArticles(request.getResourceResolver(), this.getLocale(request), request.getParameter("query"));
            final Writer writer = response.getWriter();
            final Gson gson = new GsonBuilder().create();
            gson.toJson(resultItems, writer);
        } catch (final RuntimeException ex) {
            log.debug("Error occured" + e.getStackTrace() + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error occured!");
        }
    }

}

@Component(service = Servlet.class)
@SlingServletName(servletName = "User Report Servlet")
@SlingServletResourceTypes(resourceTypes = { UserReportServlet.SERVLET_RESOURCE_TYPE }, extensions = "json", methods = HttpConstants.METHOD_GET)
public class DummyServlet7 extends SlingAllMethodsServlet {

    private static final Logger logger = LoggerFactory.getLogger(TestServlet.class);

    @Override
    public void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        try {
            final List<ResourceItem> resultItems = this.searchArticles(request.getResourceResolver(), this.getLocale(request), request.getParameter("query"));
            final Writer writer = response.getWriter();
            final Gson gson = new GsonBuilder().create();
            gson.toJson(resultItems, writer);
        } catch (final IOException e) {
            log.error("Error getting search results", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}

@Component(service = Servlet.class)
@SlingServletName(servletName = "User Report Servlet")
@SlingServletResourceTypes(resourceTypes = { UserReportServlet.SERVLET_RESOURCE_TYPE }, extensions = "json", methods = HttpConstants.METHOD_GET)
public class DummyServlet8 extends SlingAllMethodsServlet {

    private static final Logger logger = LoggerFactory.getLogger(TestServlet.class);

    @Override
    public void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        try {
            final List<ResourceItem> resultItems = this.searchArticles(request.getResourceResolver(), this.getLocale(request), request.getParameter("query"));
            final Writer writer = response.getWriter();
            final Gson gson = new GsonBuilder().create();
            gson.toJson(resultItems, writer);
        } catch (final RuntimeException | IOException e) {
            log.error("Error getting search results", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}

@Component(service = Servlet.class)
@SlingServletName(servletName = "User Report Servlet")
@SlingServletResourceTypes(resourceTypes = { UserReportServlet.SERVLET_RESOURCE_TYPE }, extensions = "json", methods = HttpConstants.METHOD_GET)
public class DummyServlet9 extends SlingAllMethodsServlet {

    private static final Logger logger = LoggerFactory.getLogger(TestServlet.class);

    @Override
    public void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        try {
            final List<ResourceItem> resultItems = this.searchArticles(request.getResourceResolver(), this.getLocale(request), request.getParameter("query"));
            final Writer writer = response.getWriter();
            final Gson gson = new GsonBuilder().create();
            gson.toJson(resultItems, writer);
        } catch (final RuntimeException | Exception e) { // Noncompliant {{Catch a non-generic exception}}
            log.error("Error getting search results", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
