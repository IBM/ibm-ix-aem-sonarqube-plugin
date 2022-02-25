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

import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;

@Component(service = Servlet.class, immediate = true,
  property = { ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + "resourceType",
    ServletResolverConstants.SLING_SERVLET_SELECTORS + "=" + "selector" })
public class Servlet1 extends SlingSafeMethodsServlet {} // Noncompliant {{Use new Sling Servlet annotations @SlingServletResourceTypes or @SlingServletPaths (the former is preferred).}}

@Component(service = Servlet.class, immediate = true)
@SlingServletResourceTypes(resourceTypes = { "resourceType" }, selectors = { "selector" })
public class Servlet2 extends SlingAllMethodsServlet {}

@Component(service = Servlet.class, immediate = true)
@SlingServletResourceTypes(resourceTypes = "resourceType", selectors = "selector")
public class Servlet3 extends SlingSafeMethodsServlet {}

@Component(service = Servlet.class)
@SlingServletPaths("/bin/custom-servlet")
public class Servlet4 extends SlingAllMethodsServlet {}