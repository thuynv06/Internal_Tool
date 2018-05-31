package Com.IFI.InternalTool.BS.Service;


import Com.IFI.InternalTool.DS.Model.Group_IFI;
import Com.IFI.InternalTool.Payloads.GroupRequest;
import Com.IFI.InternalTool.Payloads.PagedResponse;

public interface Group_IFIService {


	public Group_IFI createGroupIFI(GroupRequest group);
	
	public void deleteGroupById(String group_id);
	
	public Group_IFI getGroupById(String group_id);
	
	public PagedResponse<Group_IFI>  getAllGroup();
	
	public PagedResponse<Group_IFI> findGroupByName(String name);
	
	public PagedResponse<Group_IFI> findGroupsLikeName(String name);
}
