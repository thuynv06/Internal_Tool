package Com.IFI.InternalTool.BS.Service;

import Com.IFI.InternalTool.DS.Model.Group_IFI;
import Com.IFI.InternalTool.Payloads.GroupRequest;

public interface Group_IFIService {
	public Group_IFI getGroupById(long id);

	//public void saveOrUpdate(Group_IFI group);
	public Group_IFI createGroupIFI(GroupRequest group);
	
	public void deleteGroup(long id);
}
