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
 * New rule to avoid loginAdministrative because of it is deprecated and security reasons.
 */

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.jcr.api.SlingRepository;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.Component;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
class SlingModelClass {

    @Reference
    private SlingRepository repository;

    public void aMethod() {
        Session session = null;
        try {
            session = repository.loginService(null, null);
        } catch (final LoginException | RepositoryException ex) {
            log.error("Unable to open repository session", ex);
        } finally {
            if (session != null && session.isLive()) {
                session.logout();
            }
        }

    }

}

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
class SlingModelClass2 {

    @Reference
    private SlingRepository repository;

    public void one() {
        Session session = null;
        try {
            session = createAdminSession();
        } finally {
            if (session != null && session.isLive()) {
                session.logout();
            }
        }
    }

    private Session createAdminSession() {
        Session result = null;
        try {
            result = repository.loginAdministrative(null); // Noncompliant {{Avoid using loginAdministrative method - (Deprecated)}}
        } catch (final RepositoryException ex) {
            log.error("Unable to create administrative session", ex);
        }
        return result;
    }

}

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
class SlingModelClass3 {

    @Reference
    protected ResourceResolverFactory resourceResolverFactory;

    public void aMethod() {
        try {
            final ResourceResolver resourceResolver = resourceResolverFactory.getAdministrativeResourceResolver(null); // Noncompliant {{Avoid using getAdministrativeResourceResolver method - (Deprecated)}}
        }  catch (final LoginException ex) {
            log.error("Unable to log in service user", ex);
        }
    }

}