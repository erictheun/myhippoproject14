package org.example.beans;
/*
 * Copyright 2014-2019 Hippo B.V. (http://www.onehippo.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoDocument;
import org.hippoecm.hst.content.beans.standard.HippoFacetNavigationBean;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import org.hippoecm.hst.core.linking.HstLinkCreator;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.util.ContentBeanUtils;
import org.onehippo.cms7.essentials.components.model.Authors;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@HippoEssentialsGenerated(internalName = "myproject:blogpost")
@Node(jcrType = "myproject:blogpost")
public class Blogpost extends HippoDocument implements Authors {

    public static final String TITLE = "myproject:title";
    public static final String INTRODUCTION = "myproject:introduction";
    public static final String CONTENT = "myproject:content";
    public static final String PUBLICATION_DATE = "myproject:publicationdate";
    public static final String CATEGORIES = "myproject:categories";
    public static final String AUTHOR = "myproject:author";
    public static final String AUTHOR_NAMES = "myproject:authornames";
    public static final String LINK = "myproject:link";
    public static final String AUTHORS = "myproject:authors";
    public static final String TAGS = "hippostd:tags";

    private static final Logger LOG = LoggerFactory.getLogger(Blogpost.class);

   @HippoEssentialsGenerated(internalName = "myproject:publicationdate")
    public Calendar getPublicationDate() {
        return getSingleProperty(PUBLICATION_DATE);
    }

    @HippoEssentialsGenerated(internalName = "myproject:authornames")
    public String[] getAuthorNames() {
        return getMultipleProperty(AUTHOR_NAMES);
    }

    public String getAuthor() {
        final String[] authorNames = getAuthorNames();
        if(authorNames !=null && authorNames.length > 0){
            return authorNames[0];
        }
        return null;
    }

    @HippoEssentialsGenerated(internalName = "myproject:title")
    public String getTitle() {
       LOG.info("this blogpost getUrl: {}", this.getUrl(this));
        return getSingleProperty(TITLE);
    }

    @HippoEssentialsGenerated(internalName = "myproject:content")
    public HippoHtml getContent() {
        return getHippoHtml(CONTENT);
    }

    @HippoEssentialsGenerated(internalName = "myproject:introduction")
    public String getIntroduction() {
        return getSingleProperty(INTRODUCTION);
    }

    @HippoEssentialsGenerated(internalName = "myproject:categories")
    public String[] getCategories() {
        return getMultipleProperty(CATEGORIES);
    }

    @Override
    @HippoEssentialsGenerated(internalName = "myproject:authors")
    public List<Author> getAuthors() {
        return getLinkedBeans(AUTHORS, Author.class);
    }

    public String getUrl(Blogpost blogpost){

        final String articleName = blogpost.getName();
        final String articlePath = blogpost.getPath();

        final HippoFacetNavigationBean facetNavigationBean;
         if (StringUtils.containsIgnoreCase(articlePath, "blog")) {
            final String channelBasePath = StringUtils.substringBefore(articlePath, "blog");
            facetNavigationBean = ContentBeanUtils.getFacetNavigationBean(channelBasePath, "blogFacets", articleName);
        } else {
            return StringUtils.EMPTY;
        }

        final HstRequestContext context = RequestContextProvider.get();
        final HstLinkCreator hstLinkCreator = context.getHstLinkCreator();
        return Optional.ofNullable(facetNavigationBean)
                .map(HippoFacetNavigationBean::getResultSet)
                .map(resultSetBean -> resultSetBean.getDocumentIterator(Blogpost.class))
                .map(Iterator::next)
                .map(HippoBean::getNode)
                .map(node -> hstLinkCreator.create(node, context, null, true, true))

                .map(hstLink -> hstLink.toUrlForm(context, false))
                .orElse(StringUtils.EMPTY);
    }
}