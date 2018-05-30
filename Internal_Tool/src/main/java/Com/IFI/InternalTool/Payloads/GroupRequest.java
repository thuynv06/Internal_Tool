package Com.IFI.InternalTool.Payloads;
import javax.validation.constraints.*;

public class GroupRequest {
	
	@NotBlank
	@Size(min = 1, max = 5)
	private String group_id;
	
	
	@NotBlank
	@Size(min = 6, max = 100)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	
	

}
