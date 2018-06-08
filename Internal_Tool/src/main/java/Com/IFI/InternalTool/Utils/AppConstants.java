package Com.IFI.InternalTool.Utils;

import java.time.LocalDate;

public class AppConstants {
	public static final String DEFAULT_PAGE_NUMBER = "1";
	public static final String DEFAULT_PAGE_SIZE = "50";
	public static final int MAX_PAGE_SIZE = 50;
	public static final String SUCCESS_CODE = "SUCCESS_CODE";
	public static final String FAILED_CODE = "FAILED_CODE";
	public static final String STATUS_OK = "STATUS_OK";
	public static final String STATUS_KO = "STATUS_KO";
	static int year = LocalDate.now().getYear();
	public static final String YEAR=String.valueOf(year);
}
