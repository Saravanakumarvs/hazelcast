/*
 * Copyright (c) 2008-2015, Hazelcast, Inc. All Rights Reserved.
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

package com.hazelcast.map.impl.client;

import com.hazelcast.map.impl.MapPortableHook;
import com.hazelcast.map.impl.operation.MapOperation;
import com.hazelcast.nio.serialization.Data;
import com.hazelcast.spi.Operation;

import java.util.concurrent.TimeUnit;

public class MapSetRequest extends MapPutRequest {

    public MapSetRequest() {
    }

    public MapSetRequest(String name, Data key, Data value, long threadId) {
        super(name, key, value, threadId);
    }

    public MapSetRequest(String name, Data key, Data value, long threadId, long ttl) {
        super(name, key, value, threadId, ttl);
    }

    public int getClassId() {
        return MapPortableHook.SET;
    }

    protected Operation prepareOperation() {
        MapOperation op = getOperationProvider().createSetOperation(name, key, value, ttl);
        op.setThreadId(threadId);
        return op;
    }

    @Override
    public String getMethodName() {
        return "set";
    }

    @Override
    public Object[] getParameters() {
        if (ttl == -1) {
            return new Object[]{key, value};
        }
        return new Object[]{key, value, ttl, TimeUnit.MILLISECONDS};
    }
}
