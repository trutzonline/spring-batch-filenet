package com.springframework.batch.filenet;

import com.filenet.api.collection.IndependentObjectSet;
import com.filenet.api.core.IndependentObject;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.property.PropertyFilter;
import com.filenet.api.query.SearchSQL;
import com.filenet.api.query.SearchScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

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

    private ObjectStore objectStore;
    private String queryString;
    private Integer pageSize;
    private PropertyFilter propertyFilter;
    private Boolean continuable;

    public T read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return null;
    }

    private IndependentObjectSet search() {
        SearchScope searchScope = new SearchScope(objectStore);
        SearchSQL searchSQL = new SearchSQL(queryString);
        return searchScope.fetchObjects(searchSQL, pageSize, propertyFilter, continuable);
    }

}
