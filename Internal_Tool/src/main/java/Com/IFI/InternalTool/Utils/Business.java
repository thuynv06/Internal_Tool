package Com.IFI.InternalTool.Utils;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
public class Business {
	
	public int getMonth(Date date) {
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		return localDate.getMonthValue();
	}
	
	
	public int getYear(Date date) {
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		return localDate.getYear();
	}
}
