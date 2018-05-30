package Com.IFI.InternalTool.Utils;

import Com.IFI.InternalTool.DS.Model.Group_IFI;
import Com.IFI.InternalTool.Payloads.GroupResponse;


public class ModelMapper {
	
	 public static GroupResponse mapGroupIFIToGroupResponse(Group_IFI group) {
	        GroupResponse groupResponse = new GroupResponse();
	        
	        groupResponse.setGroup_id(group.getGroup_id());
	        groupResponse.setName(group.getName());
	        
	        return groupResponse;	
	    }

}
