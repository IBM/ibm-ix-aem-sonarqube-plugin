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

import com.day.cq.commons.jcr.JcrUtil;

import javax.jcr.Node;
import javax.jcr.Session;

import org.apache.jackrabbit.commons.JcrUtils;

class JcrApiClass {

    public void method() {
        try {
            final ResourceResolver resourceResolver = this.resourceResolverFactory.getServiceResourceResolver(param);
            final Session session = resourceResolver.adaptTo(Session.class);

            final String path = PathConstants.PATH;

            Node parentNode = JcrUtil.createPath( // Noncompliant {{Using JCR Api is discouraged, Sling API is preferred}}
              path, JcrConstants.NT_UNSTRUCTURED, session);
            parentNode.addNode("newNode"); // Noncompliant {{Using JCR Api is discouraged, Sling API is preferred}}
            JcrUtils.getOrAddNode(parentNode, "nodeName"); // Noncompliant {{Using JCR Api is discouraged, Sling API is preferred}}
            if (session.nodeExists(node.getPath())) { // Noncompliant {{Using JCR Api is discouraged, Sling API is preferred}}
                return;
            }

            resourceResolver.commit();
        } catch (final RepositoryException | PersistenceException | LoginException e) {
            log.error(e.getMessage());
        }
    }

}

