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
 * distributed under the License is distributed on an "AS IS" BASIS,f
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 *
 * Modifications:
 * Changes to examples to be have up-to-date Apache Sling and Java 11 coding standards.
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
            session = createSession();
        } finally {
            if (session != null && session.isLive()) {
                session.logout();
            }
        }
    }

    private Session createSession() {
        Session result = null;
        try {
            result = repository.loginService(null, null);
        } catch (final RepositoryException ex) {
            log.error("Unable to create session", ex);
        }
        return result;
    }

}

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
class SlingModelClass3 {

    @Reference
    private SlingRepository repository;

    public void three() {
        Session session = null;
        try {
            session = repository.loginService(null, null);
        } catch (final RepositoryException ex) {
            log.error("Unable to create session", ex);
        } finally {
            if (session != null && session.isLive()) {
                session.logout();
            }
        }
    }

}

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
class SlingModelClass4 {

    @Reference
    private SlingRepository repository;

    public void aMethod() {
        Session session = null; // Noncompliant {{A JCR session should always be closed in a finally-block.}}

        try {
            session = repository.loginService(null, null);
        } catch (final LoginException | RepositoryException ex) {
            log.error("Unable to open repository session", ex);
        }
    }

}

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
class SlingModelClass5 {

    @Reference
    private SlingRepository repository;

    public void aMethod() {
        Session session = null; // Noncompliant {{A JCR session should always be closed in a finally-block.}}

        try {
            session = repository.loginService(null, null);
        } catch (final RepositoryException e) {
            log.error("Unable to open repository session", ex);
        } finally {
            if (session != null && session.isLive()) {
            }
        }
    }

}

@Component(immediate = true)
class Component1 {

    @Reference
    private SlingRepository repository;

    public void six() {
        Session session = null; // Noncompliant {{A JCR session should always be closed in a finally-block.}}
        String plotTwist = "twist";
        plotTwist = "anotherTwist";
        plotTwist = getMeAnotherTwist();
        plotTwist = StringUtils.capitalize(plotTwist);
        try {
            session = repository.loginService(null, null);
        } catch (final RepositoryException e) {
            log.error("Unable to open repository session", ex);
        } finally {
            if (session != null && session.isLive()) {
                plotTwist.toString();
            }
        }
    }

    private String getMeAnotherTwist() {
        return "lastOne";
    }

}

@Component(immediate = true)
class Component2 {

    @Reference
    private SlingRepository repository;

    public void seven() {
        Session session = null; // Noncompliant {{A JCR session should always be closed in a finally-block.}}
        session = jump();
    }

    private Session createSession() {
        Session result = null;
        try {
            result = repository.loginService(null, null);
        } catch (final RepositoryException e) {
            log.error("Unable to open repository session", ex);
        }
        return result;
    }

    private Session jump() {
        Session result = null;
        result = createSession();
        return result;
    }

}

@Component(immediate = true)
class Component3 {

    @Reference
    private SlingRepository repository;

    private Session createSession() {
        Session result = null;
        try {
            result = repository.loginService(null, null);
        } catch (final RepositoryException e) {
            log.error("Unable to open repository session", ex);
        }
        return result;
    }

    private Session eight() {
        Session max = createSession();
        Session result = max;
        return result;
    }

}
