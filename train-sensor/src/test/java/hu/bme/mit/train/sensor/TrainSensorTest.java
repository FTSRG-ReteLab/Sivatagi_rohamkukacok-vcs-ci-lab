package hu.bme.mit.train.sensor;

import hu.bme.mit.train.interfaces.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;
// NOTE please set it back as you work on LAB3

public class TrainSensorTest {
    TrainSensor ts;
    TrainUser MockUser;
    TrainController MockController;

    @Before
    public void before() {
        MockController = mock(TrainController.class);
        MockUser = mock(TrainUser.class);
        ts = new TrainSensorImpl(MockController, MockUser);
    }

    @Test
    public void NegativeSpeedLimitTest() {
        ts.overrideSpeedLimit(-50);
        verify(MockUser, times(1)).setAlarmFlag(true);
        verify(MockUser, times(0)).setAlarmFlag(false);
    }

    @Test
    public void TooLargeSpeedLimitTest() {
        ts.overrideSpeedLimit(600);
        verify(MockUser, times(1)).setAlarmFlag(true);
        verify(MockUser, times(0)).setAlarmFlag(false);
    }

    @Test
    public void TooSlowSpeedLimitTest() {
        when(MockController.getReferenceSpeed()).thenReturn(150);

        ts.overrideSpeedLimit(50);

        verify(MockController, times(1)).getReferenceSpeed();
        verify(MockUser, times(1)).setAlarmFlag(true);
        verify(MockUser, times(0)).setAlarmFlag(false);
    }

    @Test
    public void NormalSpeedLimitTest() {
        when(MockController.getReferenceSpeed()).thenReturn(150);

        ts.overrideSpeedLimit(100);
        int sl = ts.getSpeedLimit();

        Assert.assertEquals(100, sl);
        verify(MockController, times(1)).getReferenceSpeed();
    }
}
