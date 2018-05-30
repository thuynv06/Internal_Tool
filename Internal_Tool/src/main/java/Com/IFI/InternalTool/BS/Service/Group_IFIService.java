package Com.IFI.InternalTool.BS.Service;

import java.util.List;

import Com.IFI.InternalTool.DS.Model.Group_IFI;
import Com.IFI.InternalTool.Payloads.GroupRequest;
import Com.IFI.InternalTool.Payloads.GroupResponse;
import Com.IFI.InternalTool.Payloads.PagedResponse;

public interface Group_IFIService {

	//public void saveOrUpdate(Group_IFI group);
	public Group_IFI createGroupIFI(GroupRequest group);
	
	//public void deleteGroup(String id);
	
	public PagedResponse<Group_IFI>  getAllGroup();
}
