/*
* Copyright 2020 the original author or authors.
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
package io.quarkiverse.activemq.jms.it;

import java.util.UUID;

import org.apache.activemq.ActiveMQConnectionFactory;

import jakarta.jms.JMSContext;
import jakarta.jms.JMSException;


public class AmqJmsTestSupport {

    /**
     *As was used in {@link AmqJmsEndpoint}
     */
    public static final String ENDPOINT_PATH = "/testamqjms";

    /**
     * As matches acceptor defined in src/test/resources/broker.xml,
     * and used in src/main/resources/application.properties
     */
    public static final String CONNECTION_URL = "tcp://localhost:61616";

    public static JMSContext createContext() throws JMSException {
//        JmsConnectionFactory jmsConnectionFactory = new JmsConnectionFactory(CONNECTION_URL);
        var jmsConnectionFactory = new ActiveMQConnectionFactory(CONNECTION_URL);

        return jmsConnectionFactory.createContext("", "", JMSContext.AUTO_ACKNOWLEDGE);
    }

    public static String generateBody() {
        return UUID.randomUUID().toString();
    }

}

