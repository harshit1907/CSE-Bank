package csebank_dbmodel.asu.edu;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class CreditDebitInterestScheduleTask extends TimerTask {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		CreditIntrestAndSavingIntrest creditIntrest=new CreditIntrestAndSavingIntrest();
		creditIntrest.generateCreditInterestTransaction();
		CreditIntrestAndSavingIntrest savingIntrest=new CreditIntrestAndSavingIntrest();
		savingIntrest.generateSavingInterestTransaction();
		
		
	}
	public static void main(String args[]) {
		Timer timer=new Timer(true);
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH,1);
		timer.schedule(new CreditDebitInterestScheduleTask(),calendar.getTime(), 1000*60*60*24*30);
		
	}

}
