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

import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ShippingInformationModel {

    @ScriptVariable
    private Page currentPage;

    @OSGiService
    private GoeHybrisService goeHybrisService;

    @ValueMapValue
    private String articleCode;

    private Article article;

    @PostConstruct
    void init() {
        try {
            article = goeHybrisService.getArticleDetails(
              articleCode, InheritedProperties.getHybrisStore(currentPage), currentPage.getLanguage(false));
        } catch (RuntimeException ex) {
            logger.error("Exception in post construct", ex);
        }
    }

    private List<ArticleItem> searchArticles() {
        final Session session = this.resourceResolver.adaptTo(Session.class);
        final SearchResult result = this.pageSearchService.localizedFulltextSearch(
          session, this.currentPage.getLanguage(), this.query, this.start, this.limit);
        return this.populateArticleList(result);
    }

}

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ShippingInformationModel2 {

    @ScriptVariable
    private Page currentPage;

    @OSGiService
    private GoeHybrisService goeHybrisService;

    @ValueMapValue
    private String articleCode;

    private Article article;

    private List<ArticleItem> searchArticles() {
        final Session session = this.resourceResolver.adaptTo(Session.class);
        final SearchResult result = this.pageSearchService.localizedFulltextSearch(
          session, this.currentPage.getLanguage(), this.query, this.start, this.limit);
        return this.populateArticleList(result);
    }

    @PostConstruct
    void init() { // Noncompliant {{Wrap all the code in a try-catch clause}}
        article = goeHybrisService.getArticleDetails(articleCode, InheritedProperties.getHybrisStore(currentPage), currentPage.getLanguage(false));
    }

}

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ShippingInformationModel3 {

    @ScriptVariable
    private Page currentPage;

    @OSGiService
    private GoeHybrisService goeHybrisService;

    @ValueMapValue
    private String articleCode;

    private Article article;

    @PostConstruct
    void init() { // Noncompliant {{Wrap all the code in a try-catch clause}}
        try {
            article = goeHybrisService.getArticleDetails(
              articleCode, InheritedProperties.getHybrisStore(currentPage), currentPage.getLanguage(false));
        } catch (RuntimeException ex) {
            logger.error("Exception in post construct", ex);
        }
        final Session session = this.resourceResolver.adaptTo(Session.class);
    }

}

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ShippingInformationModel4 {

    @ScriptVariable
    private Page currentPage;

    @OSGiService
    private GoeHybrisService goeHybrisService;

    @ValueMapValue
    private String articleCode;

    private Article article;

    @PostConstruct
    void init() { // Noncompliant {{Wrap all the code in a try-catch clause}}
        final Session session = this.resourceResolver.adaptTo(Session.class);
        try {
            article = goeHybrisService.getArticleDetails(
              articleCode, InheritedProperties.getHybrisStore(currentPage), currentPage.getLanguage(false));
        } catch (RuntimeException ex) {
            logger.error("Exception in post construct", ex);
        }
    }

}

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ShippingInformationModel5 {

    @ScriptVariable
    private Page currentPage;

    @OSGiService
    private GoeHybrisService goeHybrisService;

    @ValueMapValue
    private String articleCode;

    private Article article;

    @PostConstruct
    void init() {
        try {
            article = goeHybrisService.getArticleDetails(
              articleCode, InheritedProperties.getHybrisStore(currentPage), currentPage.getLanguage(false));
        } catch (Exception ex) { // Noncompliant {{Catch a non-generic exception}}
            logger.error("Exception in post construct", ex);
        }
    }

}