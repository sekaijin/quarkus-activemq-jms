/*
* Copyright 2023 the original author or authors.
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
package io.quarkiverse.activemq.jms.it.artemis;

import java.util.Collections;
import java.util.Map;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.store.PersistenceAdapter;
import org.apache.activemq.store.memory.MemoryPersistenceAdapter;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

public class BrokerTestResource implements QuarkusTestResourceLifecycleManager {

    private BrokerService embeddedBroker;

    @Override
    public Map<String, String> start() {
        try {
        	embeddedBroker = new BrokerService();
        	PersistenceAdapter p = new MemoryPersistenceAdapter();
        	embeddedBroker.setPersistenceAdapter(p);
            //broker.setPersistent(false);

        	// configure the broker
        	embeddedBroker.addConnector("tcp://localhost:61616");

        	embeddedBroker.start();
        } catch (Exception e) {
            throw new RuntimeException("Problem starting embedded ActiveMQ broker", e);
        }

        return Collections.emptyMap();
    }

    @Override
    public void stop() {
        if (embeddedBroker == null) {
            return;
        }

        try {
            embeddedBroker.stop();
        } catch (Exception e) {
            throw new RuntimeException("Problem stopping embedded ActiveMQ broker", e);
        }
    }
}