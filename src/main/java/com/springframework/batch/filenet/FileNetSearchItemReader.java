package com.springframework.batch.filenet;

import com.filenet.api.collection.IndependentObjectSet;
import com.filenet.api.collection.PageIterator;
import com.filenet.api.core.IndependentObject;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.property.PropertyFilter;
import com.filenet.api.query.SearchSQL;
import com.filenet.api.query.SearchScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.util.Assert;

import java.util.Iterator;

/**
 * Copyright 2017 Christian Trutz
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class FileNetSearchItemReader<T extends IndependentObject> implements ItemReader<T> {

    private final ObjectStore objectStore;
    private final String queryString;
    private final Integer pageSize;
    private final PropertyFilter propertyFilter;
    private final Boolean continuable;

    private Iterator<T> iterator = null;

    /**
     * @param objectStore FileNet P8 Content Manager object store used
     * @param queryString for example <code>select this from document</code>
     */
    public FileNetSearchItemReader(ObjectStore objectStore, String queryString) {
        this(objectStore, queryString, 1500, new PropertyFilter(), true);
    }

    /**
     * @param objectStore    FileNet P8 Content Manager object store used
     * @param queryString    for example <code>select this from document</code>
     * @param pageSize       the page size of the query, how many results provides the query at once, typically a query has many pages of the same page size (except the last page)
     * @param propertyFilter the FileNet property filter used for include and exclude FileNet properties into/from result set
     * @param continuable    if <code>true</code>, provides not only the first page
     */
    public FileNetSearchItemReader(ObjectStore objectStore, String queryString, Integer pageSize, PropertyFilter propertyFilter, Boolean continuable) {
        this.objectStore = objectStore;
        this.queryString = queryString;
        this.pageSize = pageSize;
        this.propertyFilter = propertyFilter;
        this.continuable = continuable;
    }

    public T read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (iterator == null) {
            iterator = search();
        }
        if (iterator.hasNext()) {
            return iterator.next();
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private Iterator<T> search() {
        SearchScope searchScope = new SearchScope(objectStore);
        SearchSQL searchSQL = new SearchSQL(queryString);
        return (Iterator<T>) searchScope.fetchObjects(searchSQL, pageSize, propertyFilter, continuable).iterator();
    }

}
