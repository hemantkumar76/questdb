/*
 * Copyright (c) 2014. Vlad Ilyushchenko
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nfsdb.journal.factory;

import com.nfsdb.journal.JournalBulkWriter;
import com.nfsdb.journal.JournalWriter;
import com.nfsdb.journal.exceptions.JournalException;

public interface JournalWriterFactory {

    <T> JournalWriter<T> writer(Class<T> clazz) throws JournalException;

    <T> JournalWriter<T> writer(Class<T> clazz, String location) throws JournalException;

    <T> JournalWriter<T> writer(Class<T> clazz, String location, int recordHint) throws JournalException;

    <T> JournalBulkWriter<T> bulkWriter(Class<T> clazz) throws JournalException;

    <T> JournalBulkWriter<T> bulkWriter(Class<T> clazz, String location) throws JournalException;

    <T> JournalBulkWriter<T> bulkWriter(Class<T> clazz, String location, int recordHint) throws JournalException;

    JournalConfiguration getConfiguration();

}
