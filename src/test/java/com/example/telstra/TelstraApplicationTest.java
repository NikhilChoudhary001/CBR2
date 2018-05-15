package com.example.telstra;

import static org.mockito.Mockito.doNothing;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.example.telstra.TelstraApplication;

@RunWith(SpringRunner.class)

public class TelstraApplicationTest {

	@InjectMocks
	TelstraApplication telstraApplication;
	
	@MockBean
	Router router;
		
	@Test
	public void mainTest() {
	
		
		doNothing().when(router).init();
		doNothing().when(router).exec();
	}
	

}
