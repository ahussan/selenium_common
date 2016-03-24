/**
 * This class contains utility methods for dealing with dates and times.
 * 
 * @author mswanton
 */
package common;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateTimeUtilities {

	/**
	 * Gets current date.
	 * 
	 * @return current date in MM/DD/YYYY format
	 */
	public static String getCurrentDate(){
		Date date = new Date();
		
		SimpleDateFormat MM_DD_YYYY_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
		String currentDate = MM_DD_YYYY_FORMAT.format(date);
		
		return currentDate;
	}

	/**
	 * This method advances (if given a positive number) or rolls back
	 * (if given a negative number) the given date by the given number of days.
	 * 
	 * Some error checking should probably happen to make data errors more 
	 * informative.
	 * 
	 * @param date date to be changed
	 * @return date after being advanced/rolled back given number of days
	 */
	public static String changeDate(String date, int numDays){
		String[] dateParts = date.split("/");
		int month = Integer.parseInt(dateParts[0]);
		int day = Integer.parseInt(dateParts[1]);
		int year = Integer.parseInt(dateParts[2]);
		int newDay = day + numDays;
		int newMonth = month;
		int newYear = year;
		int[] newDateParts = {newMonth, newDay, newYear};
		newDateParts = shiftDay(newDateParts);
		Log.debug("New Date Parts: " + Arrays.toString(newDateParts));
		String newDate = newDateParts[0] + "/" + newDateParts[1] + "/" + newDateParts[2];
		return newDate;
	}
	
	private static int[] shiftDay(int[] dateParts){
		int newMonth = dateParts[0];
		int newDay = dateParts[1];
		int newYear = dateParts[2];
		
		if(newDay > numDaysInMonth(newMonth)){
			Log.debug("Too High: " + newDay);
			newDay = newDay - numDaysInMonth(newMonth);
			if(newMonth == 12){
				newMonth = 1;
				newYear ++;
			}
			else{
				newMonth ++;
			}    		
		}
		else if(newDay < 1){
			Log.debug("Too Low: " + newDay);
			if(newMonth == 1){
				newMonth = 12;
				newYear --;
			}
			else{
				newMonth --;
			}
			newDay = numDaysInMonth(newMonth) + newDay;
		}
		else{
			Log.debug("Just Right: " + newDay);
			int[]date = {newMonth, newDay, newYear};
			return date;
		}
		
		int[]date = {newMonth, newDay, newYear};
		return shiftDay(date);		
	}
	
	public static int numDaysInMonth(int month){
		switch(month){
		case 2:
			return 28;
		case 4: case 6: case 9: case 11:
			return 30;
		default:
			return 31;
		}
	}

	/**
	 * Get the current time in hh:mm am/pm format.
	 * 
	 * @return the current time
	 */
	public static String getCurrentTime(){
		SimpleDateFormat HH_MM_AMPM = new SimpleDateFormat("hh:mm a");
		Calendar current = new GregorianCalendar();
		return HH_MM_AMPM.format(current.getTime());
	}

	/**
	 * This method advances the provided time by the specified number of hours
	 * and minutes.
	 * 
	 * NOTE: There is a problem with advancing over the 12:00pm hour,
	 * see http://stackoverflow.com/questions/14286600/java-calendar-issues-setting-12pm
	 * 
	 * @param time
	 * @param deltaHour
	 * @param deltaMin
	 * @return
	 */
	public static String changeTime(String time, int deltaHour, int deltaMin){
		SimpleDateFormat HH_MM_AMPM = new SimpleDateFormat("hh:mm a");
		String[] timeParts = time.split("\\W+");
		String amPM = timeParts[2];
		int hour;
		if(amPM.equalsIgnoreCase("pm")){
			hour = Integer.parseInt(timeParts[0]) + 12;
		}
		else{
			hour = Integer.parseInt(timeParts[0]);
		}
		int minute = Integer.parseInt(timeParts[1]);
		
		Calendar timeCal = new GregorianCalendar(2015, 1, 1, hour, minute);
		timeCal.add(Calendar.HOUR_OF_DAY, deltaHour);
		timeCal.add(Calendar.MINUTE, deltaMin);
		
		return HH_MM_AMPM.format(timeCal.getTime());
		
	}

}
