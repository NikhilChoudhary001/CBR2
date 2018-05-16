package com.example.telstra;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Properties;
import javax.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import com.example.helpers.StringConstants;
import com.example.models.PropertiesCBR;

@Component
public class Router {

    private InputStream input;	
 	
	private static final Logger logger = Logger.getLogger(Router.class);		
	
	private static Router route=new Router();
	Properties prop = new Properties() ;

	public void exec() {
		CamelContext camelctx = new DefaultCamelContext();
		try {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(PropertiesCBR.getActivemqurl());
			camelctx.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
			ConsumerTemplate consumer = camelctx.createConsumerTemplate();
			ProducerTemplate ptemplate=camelctx.createProducerTemplate();
		
			int i=1;
		
			Calendar endTime=Calendar.getInstance();
			endTime.add(Calendar.MINUTE,PropertiesCBR.getConfigloadingtime());
			Calendar tempTime;		
			logger.info(endTime);
			
			
			while(true) {
				tempTime=Calendar.getInstance();
				if(tempTime.getTimeInMillis()>=endTime.getTimeInMillis())
				{
					route.init();
					logger.info(Calendar.getInstance().getTime());
				
					logger.info(tempTime);
					logger.info(endTime);				
					endTime=Calendar.getInstance();
					endTime.add(Calendar.MINUTE,PropertiesCBR.getConfigloadingtime());
				}
				logger.info("cutoff%"+PropertiesCBR.getLegacyqueuepercentage());
				if(i>PropertiesCBR.getCutoffpercentage()) {
					i=1;
				}
				String result = consumer.receiveBody("jms:queue:"+PropertiesCBR.getEmsbb(), String.class);
				
				if(i<=PropertiesCBR.getLegacyqueuepercentage()) {
				ptemplate.sendBody("jms:queue:"+PropertiesCBR.getLegacyNBN(), result);
		        logger.info(i+"-LegacyNBNQueue-"+PropertiesCBR.getLegacyNBN());
		        i++;
		        } 
				else {
					ptemplate.sendBody("jms:queue:"+PropertiesCBR.getNbnPlus(), result);
					logger.info(i+"-B2BGW-NBNPlus-"+PropertiesCBR.getNbnPlus());
					i++;
				}
			}	        
		}
		catch (Exception e)
		{
	         e.printStackTrace();
			logger.error(" Error while processing the request queue "+ e);
			
		}
		
		finally {
			try {
				System.out.println("error");
				camelctx.stop();
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}	
	}
	public void init() {
			
		try {
			
		    input  = new FileInputStream("./myvolume/config.properties"); 			
			prop.load(input);
		  
		    PropertiesCBR.setEmsbb(prop.getProperty(StringConstants.EMS_BB));		
			PropertiesCBR.setLegacyNBN(prop.getProperty(StringConstants.LEGACY_NBN));
			PropertiesCBR.setNbnPlus(prop.getProperty(StringConstants.NBN_PLUS));
			PropertiesCBR.setCutoffpercentage(Integer.parseInt(prop.getProperty(StringConstants.CUTOFFPERCENTAGE)));
			PropertiesCBR.setLegacyqueuepercentage(Integer.parseInt(prop.getProperty(StringConstants.LEGACYQUEUEPERCENTAGE)));
			PropertiesCBR.setActivemqurl(prop.getProperty(StringConstants.ACTIVEMQURL));		
			PropertiesCBR.setConfigloadingtime(Integer.parseInt(prop.getProperty(StringConstants.CONFIGLOADINGTIME)));
						
			input.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
}
