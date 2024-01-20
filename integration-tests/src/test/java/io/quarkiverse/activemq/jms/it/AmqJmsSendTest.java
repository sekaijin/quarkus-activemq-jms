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

import static io.quarkiverse.activemq.jms.it.AmqJmsTestSupport.ENDPOINT_PATH;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkiverse.activemq.jms.it.artemis.BrokerTestResource;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import jakarta.jms.JMSConsumer;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;
import jakarta.ws.rs.core.Response.Status;

@QuarkusTest
@QuarkusTestResource(BrokerTestResource.class)
public class AmqJmsSendTest {

    /**
     * Tests that sending works in the {@link AmqJmsSend} application code
     * using the extension, by using the {@link AmqJmsEndpoint} server to
     * post a message and trigger the app sending it to the broker, where we
     * then receive the message from and compare the body with what we posted.
     *
     * @throws Exception
     *             if there is an unexpected problem
     */
    @Test
    public void testSend() throws Exception {
        String body = AmqJmsTestSupport.generateBody();

        Response response = RestAssured.with().body(body).post(ENDPOINT_PATH);
        Assertions.assertEquals(Status.NO_CONTENT.getStatusCode(), response.statusCode());

        try (JMSContext context = AmqJmsTestSupport.createContext()) {
            Queue destination = context.createQueue(AmqJmsSend.QUEUE_NAME);
            JMSConsumer consumer = context.createConsumer(destination);

            Assertions.assertEquals(body, consumer.receive(2000L).getBody(String.class), "Received body did not match that sent");
        }
    }
}
