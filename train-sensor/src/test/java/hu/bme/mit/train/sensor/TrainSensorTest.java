package hu.bme.mit.train.sensor;


import org.junit.Before;
import org.junit.Test;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;


import static org.mockito.Mockito.*; 

public class TrainSensorTest {

	TrainSensor sensor;
	TrainUser user;
	TrainController controller;

	@Before
	public void before() {
		
		controller = mock(TrainController.class);
		user = mock(TrainUser.class);
		sensor = new TrainSensorImpl(controller, user);
		
		
	}
	
	@Test
    public void overrideSpeedLimit_RelativeMarginAlertUser() {
    	
    	when(controller.getReferenceSpeed()).thenReturn(150);
    	sensor.overrideSpeedLimit(50);

    	verify(user, times(1)).setAlarmState(true);
    		
    }
	
	@Test
    public void overrideSpeedLimit_NotAlertUser() {
    	
    	when(controller.getReferenceSpeed()).thenReturn(150);
    	sensor.overrideSpeedLimit(100);

    	verify(user, times(0)).setAlarmState(true);
    		
    }
	
	@Test
    public void overrideSpeedLimit_AbsoluteMarginLowAlertUser() {
    	
    	when(controller.getReferenceSpeed()).thenReturn(10);
    	sensor.overrideSpeedLimit(-10);

    	verify(user, times(1)).setAlarmState(true);
    		
    }
	
	@Test
    public void overrideSpeedLimit_AblsoluteMarginUpAlertUser() {
    	
    	when(controller.getReferenceSpeed()).thenReturn(400);
    	sensor.overrideSpeedLimit(600);

    	verify(user, times(1)).setAlarmState(true);
    		
    }
	
	@Test
    public void overrideSpeedLimit_AbsoluteMarginZeroNotAlertUser() {
    	
    	when(controller.getReferenceSpeed()).thenReturn(0);
    	sensor.overrideSpeedLimit(0);

    	verify(user, times(0)).setAlarmState(true);
    		
    }
	
	
	
}
