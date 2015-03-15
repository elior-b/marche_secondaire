package bean;

import java.io.Serializable;
import java.util.Date;

import javax.ejb.*;

@Remote
public interface MyTimer {
	public void startTimer(Serializable info, int idOffre, long duration);

	public void stopTimer(Serializable info);

	public void logMessage(MyTimer timer);
}