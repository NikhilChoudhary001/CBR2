package com.example.telstra;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import javax.jms.JMSException;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.example.helpers.StringConstants;
import com.example.models.PropertiesCBR;

@RunWith(SpringRunner.class)
public class RouterTest {

	@InjectMocks
	Router router;
	
	@MockBean
	CamelContext camelctx ;
	
	@MockBean
	ConsumerTemplate consumer;
	
	@MockBean
	ProducerTemplate producer;	
	InputStream input;
	
	@Autowired
	Properties properties ;
	
	ActiveMQTextMessage activeMQTextMessage;
	
	@Before
	public void before() throws JMSException {	
		
		try {
		input  = new FileInputStream("./src/test/java/config.properties"); 			
		properties.load(input);
	  
	    PropertiesCBR.setEmsbb(properties.getProperty(StringConstants.EMS_BB));		
		PropertiesCBR.setLegacyNBN(properties.getProperty(StringConstants.LEGACY_NBN));
		PropertiesCBR.setNbnPlus(properties.getProperty(StringConstants.NBN_PLUS));
		PropertiesCBR.setCutoffpercentage(Integer.parseInt(properties.getProperty(StringConstants.CUTOFFPERCENTAGE)));
		PropertiesCBR.setLegacyqueuepercentage(Integer.parseInt(properties.getProperty(StringConstants.LEGACYQUEUEPERCENTAGE)));
		PropertiesCBR.setActivemqurl(properties.getProperty(StringConstants.ACTIVEMQURL));		
		PropertiesCBR.setConfigloadingtime(Integer.parseInt(properties.getProperty(StringConstants.CONFIGLOADINGTIME)));
					
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void execTest() {		
					
		when(camelctx.createConsumerTemplate()).thenReturn(consumer);
		when(camelctx.createProducerTemplate()).thenReturn(producer);
				
		String message = null;
		when(consumer.receiveBody("jms:queue:"+PropertiesCBR.getEmsbb(), String.class)).thenReturn(message);
	
		assertEquals("tcp://0.0.0.0:61616",PropertiesCBR.getActivemqurl());
		assertEquals("EMSBB-CBR",PropertiesCBR.getEmsbb());
		assertEquals("LegacyNBNQueue",PropertiesCBR.getLegacyNBN());
		assertEquals("B2BGW-NBNPlus",PropertiesCBR.getNbnPlus());
		assertEquals(100,PropertiesCBR.getCutoffpercentage());
		assertEquals(40,PropertiesCBR.getLegacyqueuepercentage());
		assertEquals(1,PropertiesCBR.getConfigloadingtime());

	}
	
	@Test
	public void initTest() {
		assertEquals(1,1);
		
		
	}
}
