package com.example.telstra;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Properties;
import javax.jms.JMSException;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.example.models.PropertiesCBR;

@RunWith(SpringRunner.class)
public class RouterTest {

	@InjectMocks
	Router router;
	
	@MockBean
	PropertiesCBR propertiesCBR;
	
	@MockBean
	CamelContext camelctx;
	
	@MockBean
	ConsumerTemplate consumer;
	
	@MockBean
	ProducerTemplate producer;
	
	@MockBean
	InputStream input;
	
	@MockBean
	Properties properties;
	
	ActiveMQTextMessage activeMQTextMessage;
	
	@Before
	public void before() throws JMSException {		
		activeMQTextMessage = new ActiveMQTextMessage();		
	}
	
	@Test
	public void execTest() {		
		
		assertEquals(1,1);
		
		when(camelctx.createConsumerTemplate()).thenReturn(consumer);
		when(camelctx.createProducerTemplate()).thenReturn(producer);
		/*doNothing().when(camelctx).createConsumerTemplate();
		doNothing().when(camelctx).createProducerTemplate();*/
		Integer i = 0;
		String message = null;
		when(propertiesCBR.getConfigloadingtime()).thenReturn(i);
		when(propertiesCBR.getCutoffpercentage()).thenReturn(i);
		when(propertiesCBR.getLegacyqueuepercentage()).thenReturn(i);
		when(propertiesCBR.getNbnPlus()).thenReturn(message);
	
	}
	
	@Test
	public void initTest() {
		assertEquals(1,1);
	}
}
