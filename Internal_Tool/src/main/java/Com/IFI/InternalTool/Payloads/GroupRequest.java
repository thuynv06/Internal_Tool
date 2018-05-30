package Com.IFI.InternalTool.Payloads;
import javax.validation.constraints.*;

public class GroupRequest {
	@NotBlank
	@Size(min = 6, max = 100)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
