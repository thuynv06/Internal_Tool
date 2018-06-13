package Com.IFI.InternalTool.DS.Model.SearchModel;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class OvertimeSearch implements Serializable{
	private String emp_name;
	private String pro_name;
	@JsonFormat(pattern="MM/dd/yyyy hh:mm a")
	private Date from_hour;
	@JsonFormat(pattern="MM/dd/yyyy hh:mm a")
	private Date to_hour;
	
	public String getEmp_name() {
		return emp_name;
	}
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	public String getPro_name() {
		return pro_name;
	}
	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}
	
	
	public Date getFrom_hour() {
		return from_hour;
	}
	public void setFrom_hour(Date from_hour) {
		this.from_hour = from_hour;
	}
	public Date getTo_hour() {
		return to_hour;
	}
	public void setTo_hour(Date to_hour) {
		this.to_hour = to_hour;
	}
	
	public OvertimeSearch(String emp_name, String pro_name, Date from_hour, Date to_hour) {
		super();
		this.emp_name = emp_name;
		this.pro_name = pro_name;
		this.from_hour = from_hour;
		this.to_hour = to_hour;
	}
	public OvertimeSearch() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}

