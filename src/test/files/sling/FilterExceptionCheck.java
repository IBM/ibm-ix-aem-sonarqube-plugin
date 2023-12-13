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

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.servlets.annotations.SlingServletFilter;
import org.apache.sling.servlets.annotations.SlingServletFilterScope;
import org.osgi.service.component.annotations.Component;

@Component
@SlingServletFilter(scope = { SlingServletFilterScope.REQUEST }, suffix_pattern = "/suffix/foo", resourceTypes = {
  "foo/bar" }, pattern = "/content/.*", extensions = { "txt", "json" }, selectors = { "foo", "bar" }, methods = { "GET", "HEAD" })
public class FooBarFilter implements Filter {

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {  // Noncompliant {{Put all the custom code in a try-catch clause (except for the doFilter call)}}
        final SlingHttpServletResponse slingResponse = (SlingHttpServletResponse) response;
        slingResponse.addHeader("foobared", "true");
        chain.doFilter(request, slingResponse);
    }

    @Override
    public void destroy() {

    }

}

@Component
@SlingServletFilter(scope = { SlingServletFilterScope.REQUEST }, suffix_pattern = "/suffix/foo", resourceTypes = {
  "foo/bar" }, pattern = "/content/.*", extensions = { "txt", "json" }, selectors = { "foo", "bar" }, methods = { "GET", "HEAD" })
public class FooBarFilter2 implements Filter {

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        try {
            final HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.addHeader("my-custom-header", "header-value");
        } catch (final RuntimeException e) {
            logger.error("Exception in request filter", e);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}

@Component
@SlingServletFilter(scope = { SlingServletFilterScope.REQUEST }, suffix_pattern = "/suffix/foo", resourceTypes = {
  "foo/bar" }, pattern = "/content/.*", extensions = { "txt", "json" }, selectors = { "foo", "bar" }, methods = { "GET", "HEAD" })
public class FooBarFilter3 implements Filter {

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException { // Noncompliant {{Put all the custom code in a try-catch clause (except for the doFilter call)}}
        try {
            final HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.addHeader("my-custom-header", "header-value");
        } catch (final IOException e) { // Noncompliant {{Do not catch IOException or ServletException, it might cause unexpected behaviour}}
            logger.error("Exception in request filter", e);
        }
        int a = 5;
    }

    @Override
    public void destroy() {

    }

}

@Component
@SlingServletFilter(scope = { SlingServletFilterScope.REQUEST }, suffix_pattern = "/suffix/foo", resourceTypes = {
  "foo/bar" }, pattern = "/content/.*", extensions = { "txt", "json" }, selectors = { "foo", "bar" }, methods = { "GET", "HEAD" })
public class FooBarFilter4 implements Filter {

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) { // Noncompliant {{Put all the custom code in a try-catch clause (except for the doFilter call)}}
        try {
            final HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.addHeader("my-custom-header", "header-value");
        } catch (final IOException e) { // Noncompliant {{Do not catch IOException or ServletException, it might cause unexpected behaviour}}
            logger.error("Exception in request filter", e);
        } catch (final ServletException e) { // Noncompliant {{Do not catch IOException or ServletException, it might cause unexpected behaviour}}
            logger.error("Exception in request filter", e);
        } catch (final RuntimeException e) {
            logger.error("Exception in request filter", e);
        }
        int a = 5;
    }

    @Override
    public void destroy() {

    }

}

@Component
@SlingServletFilter(scope = { SlingServletFilterScope.REQUEST }, suffix_pattern = "/suffix/foo", resourceTypes = {
  "foo/bar" }, pattern = "/content/.*", extensions = { "txt", "json" }, selectors = { "foo", "bar" }, methods = { "GET", "HEAD" })
public class FooBarFilter5 implements Filter {

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        try {
            final HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.addHeader("my-custom-header", "header-value");
        } catch (final Exception e) { // Noncompliant {{Catch a non-generic exception}}
            logger.error("Exception in request filter", e);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}

@Component
@SlingServletFilter(scope = { SlingServletFilterScope.REQUEST }, suffix_pattern = "/suffix/foo", resourceTypes = {
        "foo/bar" }, pattern = "/content/.*", extensions = { "txt", "json" }, selectors = { "foo", "bar" }, methods = { "GET", "HEAD" })
public class FooBarFilter6 implements Filter {
    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) { // Noncompliant {{Put all the custom code in a try-catch clause (except for the doFilter call)}}
        try {
            final HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.addHeader("my-custom-header", "header-value");
        } catch (final IOException | ServletException e) { // Noncompliant {{Do not catch IOException or ServletException, it might cause unexpected behaviour}}
            logger.error("Exception in request filter", e);
        }
        int a = 5;
    }
}

@Component
@SlingServletFilter(scope = { SlingServletFilterScope.REQUEST }, suffix_pattern = "/suffix/foo", resourceTypes = {
        "foo/bar" }, pattern = "/content/.*", extensions = { "txt", "json" }, selectors = { "foo", "bar" }, methods = { "GET", "HEAD" })
public class FooBarFilter7 implements Filter {
    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) {
        try {
            final HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.addHeader("my-custom-header", "header-value");
        } catch (final RuntimeException | IOException e) { // Noncompliant {{Do not catch IOException or ServletException, it might cause unexpected behaviour}}
            logger.error("Exception in request filter", e);
        }
    }
}

@Component
@SlingServletFilter(scope = { SlingServletFilterScope.REQUEST }, suffix_pattern = "/suffix/foo", resourceTypes = {
        "foo/bar" }, pattern = "/content/.*", extensions = { "txt", "json" }, selectors = { "foo", "bar" }, methods = { "GET", "HEAD" })
public class FooBarFilter8 implements Filter {
    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) {
        try {
            final HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.addHeader("my-custom-header", "header-value");
        } catch (final RuntimeException | IllegalArgumentException e) {
            logger.error("Exception in request filter", e);
        }
    }
}

