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

import org.apache.sling.models.annotations.Model;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.day.cq.wcm.api.PageManager;
import com.day.cq.dam.api.Asset;
import com.day.cq.commons.Externalizer;
import com.day.cq.search.QueryBuilder;
import com.adobe.cq.sightly.WCMUsePojo;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
class SlingModelClass {

    @OSGiService
    private PageManagerFactory pageManagerFactory;

    @SlingObject
    private Resource resource;

    @Reference
    private Externalizer externalizer;

    @Reference
    private QueryBuilder queryBuilder;

    public void aMethod() {
        PageManager pageManager = pageManagerFactory.getPageManager(resourceResolver);
        Asset asset = DamUtil.resolveToAsset(resource);
        ValueMap valueMap = this.resource.getValueMap();
        this.externalizer.publishLink(resourceResolver, pagePath);
        this.queryBuilder.createQuery(predicates, session);
    }

}

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
class SlingModelClass2 {

    @SlingObject
    private ResourceResolver resourceResolver;

    @SlingObject
    private Resource resource;

    public void aMethod() {
        PageManager pageManager = resourceResolver.adaptTo(PageManager.class); // Noncompliant {{Inject PageManagerFactory and use it to get PageManger instead of adapting it from resource resolver}}
        Asset asset = resource.adaptTo(Asset.class); // Noncompliant {{Use DamUtil.resolveToAsset method to retrieve the Asset instead of adapting it from resource}}
        ValueMap valueMap = this.resource.adaptTo(ValueMap.class);  // Noncompliant {{Use getValueMap method on a resource to retrieve the ValueMap instead of adapting it}}
        Externalizer externalizer = resourceResolver.adaptTo(Externalizer.class); // Noncompliant {{Inject the Externalizer object instead of adapting it from resource resolver}}
        QueryBuilder queryBuilder = resourceResolver.adaptTo(QueryBuilder.class);  // Noncompliant {{Inject the Query builder object instead of adapting it from resource resolver}}
    }

}
