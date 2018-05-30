package Com.IFI.InternalTool.BS.Service;

import java.util.List;

import Com.IFI.InternalTool.DS.Model.Group_IFI;
import Com.IFI.InternalTool.Payloads.GroupRequest;
import Com.IFI.InternalTool.Payloads.PagedResponse;

public interface Group_IFIService {

	//public void saveOrUpdate(Group_IFI group);
	public Group_IFI createGroupIFI(GroupRequest group);
	
	public void deleteGroupById(String id);
	
	public Group_IFI getGroupById(String id);
	
	public PagedResponse<Group_IFI>  getAllGroup();
	
	public PagedResponse<Group_IFI> findGroupByName(String name);
}
