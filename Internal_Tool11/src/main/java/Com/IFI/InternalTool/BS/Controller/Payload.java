package Com.IFI.InternalTool.BS.Controller;

public class Payload {
	private String code;
	private String status;
	private String message;
	private Object data;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Payload() {
		super();
	}
	public Payload(String code, String status, String description, Object data) {
		super();
		this.code = code;
		this.status = status;
		this.message = description;
		this.data = data;
	}
	
	public void  setPayload(String code, String status, String Description,Object data) {
		this.code=code;
		this.data= data;	
		this.status=status;
		this.message= Description;
			
	}
	
}