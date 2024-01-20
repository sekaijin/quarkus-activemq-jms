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

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSConsumer;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSException;
import jakarta.jms.Queue;

@ApplicationScoped
public class AmqJmsReceive {

	static final String QUEUE_NAME = "test-amq-jms-receive";

	@Inject
	ConnectionFactory connectionFactory;

	public String receiveMessageBody() throws JMSException {
		try (JMSContext context = connectionFactory.createContext("", "", JMSContext.AUTO_ACKNOWLEDGE)) {
			Queue destination = context.createQueue(QUEUE_NAME);
			JMSConsumer consumer = context.createConsumer(destination);

			return consumer.receive(2000L).getBody(String.class);
		}
	}
}
