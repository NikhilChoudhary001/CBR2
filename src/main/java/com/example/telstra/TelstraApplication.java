package com.example.telstra;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.jms.ConnectionFactory;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.deser.std.DateDeserializers.CalendarDeserializer;

import javax.management.*;
import javax.management.remote.*;

 
@SpringBootApplication

public class TelstraApplication {	
	 
	 private static final Logger logger = Logger.getLogger(TelstraApplication.class);
	private String emsbb;
	private String legacyNBN;
	private String nbnPlus;
	private int cutoffpercentage;
	private int legacyqueuepercentage;
	private String activemqurl;
	private int configloadingtime;
	private InputStream input;
	
	private static TelstraApplication ta=new TelstraApplication();	
	Properties prop = new Properties();
	
	public static void main(String[] args) {		
		
		SpringApplication.run(TelstraApplication.class, args);
		ta.init();
		ta.exec();

	}
	
	public void exec() {
		CamelContext camelctx = new DefaultCamelContext();
		try {
			
			
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ta.activemqurl);
			camelctx.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
			ConsumerTemplate consumer = camelctx.createConsumerTemplate();
			ProducerTemplate ptemplate=camelctx.createProducerTemplate();
		
			int i=1;
		
			Calendar endTime=Calendar.getInstance();
			endTime.add(Calendar.MINUTE,ta.configloadingtime);
			Calendar tempTime;		
			logger.info(endTime);
			
			
			while(true) {
				tempTime=Calendar.getInstance();
				if(tempTime.getTimeInMillis()>=endTime.getTimeInMillis())
				{
					ta.init();
					logger.info(Calendar.getInstance().getTime());
				
					logger.info(tempTime);
					logger.info(endTime);				
					endTime=Calendar.getInstance();
					endTime.add(Calendar.MINUTE,ta.configloadingtime);
				}
				logger.info("cutoff%"+ta.legacyqueuepercentage);
				if(i>ta.cutoffpercentage) {
					i=1;
				}
				String result = consumer.receiveBody("jms:queue:"+ta.emsbb, String.class);
				
				if(i<=ta.legacyqueuepercentage) {
				ptemplate.sendBody("jms:queue:"+ta.legacyNBN, result);
		        logger.info(i+"-LegacyNBNQueue-"+ta.legacyNBN);
		        i++;
		        } 
				else {
					ptemplate.sendBody("jms:queue:"+ta.nbnPlus, result);
					logger.info(i+"-B2BGW-NBNPlus-"+ta.nbnPlus);
					i++;
				}
			}	        
		}
		catch (Exception e)
		{
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
	public  void init() { 
			
		try {
			
		    input  = new FileInputStream("./myvolume/config.properties"); 			
			 
			prop.load(input);
			
			emsbb=prop.getProperty("Queue.EMSBB");
			legacyNBN=prop.getProperty("Queue.LegacyNBN");
			nbnPlus=prop.getProperty("Queue.NBNPlus");
			cutoffpercentage=Integer.parseInt(prop.getProperty("cutoffpercentage"));
			legacyqueuepercentage=Integer.parseInt(prop.getProperty("legacyqueuepercentage"));
			activemqurl=prop.getProperty("activemqurl");
			configloadingtime=Integer.parseInt(prop.getProperty("configloadingtime"));
			
			input.close(); 
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
			
	}
	

 
}