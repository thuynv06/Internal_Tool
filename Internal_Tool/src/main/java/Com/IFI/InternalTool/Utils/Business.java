package Com.IFI.InternalTool.Utils;

import java.sql.Date;
import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;

import Com.IFI.InternalTool.DS.DAO.AllocationDetailDAO;
import Com.IFI.InternalTool.DS.DAO.Impl.AllocationDetailDAOImpl;

public class Business {
	private static int num;
	
	
	public static void numberWeekendOfMonth1(int month, int year) {

		Month m = Month.of(month);

		IntStream.rangeClosed(1, YearMonth.of(year, m).lengthOfMonth()).mapToObj(day -> LocalDate.of(year, m, day))
				.filter(date -> date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY)
				.forEach(date -> System.out.print(date.getDayOfMonth() + " "));
	}

	public static int numberWeekendOfMonth(int month, int year) {
		num=0;
		Month m = Month.of(month);
		IntStream.rangeClosed(1, YearMonth.of(year, m).lengthOfMonth()).mapToObj(day -> LocalDate.of(year, m, day))
				.filter(date -> date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY)
				.forEach(date -> num++);
		
		return num;
	}
	
	public static double getAllocation_Plan(int numDaysOfMonth,int numDaysWeekOfMonth,int distanceTime) {
		System.out.println(distanceTime);
		int y= numDaysOfMonth - numDaysWeekOfMonth;
		System.out.println(y);
		double result= ( (double) distanceTime / y ) * 100;
		System.out.println(result);
		 DecimalFormat formatter = new DecimalFormat("#0.00");
		return Double.valueOf(formatter.format(result));
	}
	
	public static int getDistanceTime(LocalDate start_date, LocalDate end_date) {
		int DistanceTime=0;
		while (start_date.isBefore(end_date)) {		
	        if ((start_date.getDayOfWeek() != DayOfWeek.SATURDAY &&
	              start_date.getDayOfWeek() != DayOfWeek.SUNDAY)) {
	            DistanceTime++;
	        }
	        start_date=start_date.plusDays(1);
	        
	    }	
		return DistanceTime;
	}
}
