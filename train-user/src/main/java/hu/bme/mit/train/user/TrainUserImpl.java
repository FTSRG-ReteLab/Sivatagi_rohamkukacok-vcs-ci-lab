package hu.bme.mit.train.user;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainUser;

import java.util.Random;


public class TrainUserImpl implements TrainUser {

	private TrainController controller;
	private int joystickPosition;
	private String ID;

	public TrainUserImpl(TrainController controller) {
		this.controller = controller;
		String rand  = String.valueOf(new Random().nextInt(200));
		this.ID = 'U' + rand + 20200309;
	}

	@Override
	public boolean getAlarmFlag() {
		return false;
	}

	@Override
	public int getJoystickPosition() {
		return joystickPosition;
	}

	@Override
	public void overrideJoystickPosition(int joystickPosition) {
		this.joystickPosition = joystickPosition;
		controller.setJoystickPosition(joystickPosition);
	}

}
