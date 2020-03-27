package hu.bme.mit.train.system;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;
import hu.bme.mit.train.system.TrainSystem;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;


import java.sql.Time;
import java.time.LocalDateTime;

public class TrainSystemTest {

	TrainController controller;
	TrainSensor sensor;
	TrainUser user;
	
	@Before
	public void before() {
		TrainSystem system = new TrainSystem();
		controller = system.getController();
		sensor = system.getSensor();
		user = system.getUser();

		sensor.overrideSpeedLimit(50);
	}
	
	@Test
	public void OverridingJoystickPosition_IncreasesReferenceSpeed() {
		sensor.overrideSpeedLimit(10);

		Assert.assertEquals(0, controller.getReferenceSpeed());
		
		user.overrideJoystickPosition(5);

		controller.followSpeed();
		Assert.assertEquals(5, controller.getReferenceSpeed());
		controller.followSpeed();
		Assert.assertEquals(10, controller.getReferenceSpeed());
		controller.followSpeed();
		Assert.assertEquals(10, controller.getReferenceSpeed());
	}

	@Test
	public void OverridingJoystickPositionToNegative_SetsReferenceSpeedToZero() {
		user.overrideJoystickPosition(4);
		controller.followSpeed();
		user.overrideJoystickPosition(-5);
		controller.followSpeed();
		Assert.assertEquals(0, controller.getReferenceSpeed());
	}


	@Test
	public void SpeedLimitTest() {
		sensor.overrideSpeedLimit(10);
		user.overrideJoystickPosition(20);
		controller.followSpeed();
		Assert.assertEquals(10, controller.getReferenceSpeed());
	}

	@Test
	public void givenTable_whenGet_returnsSuccessfully() {

		sensor.overrideSpeedLimit(60);
		user.overrideJoystickPosition(60);
		controller.followSpeed();

		Table<LocalDateTime, Integer, Integer> Table
				= HashBasedTable.create();
		
		LocalDateTime t = LocalDateTime.now();
		
				
		Table.put(t, user.getJoystickPosition(),controller.getReferenceSpeed());


		int ref_speed = Table.get(t, user.getJoystickPosition());

		Assert.assertEquals(60, ref_speed);
	}

}
