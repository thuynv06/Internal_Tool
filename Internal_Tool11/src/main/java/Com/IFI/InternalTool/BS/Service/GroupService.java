package Com.IFI.InternalTool.BS.Service;

import Com.IFI.InternalTool.DS.Model.Group_IFI;
public interface GroupService {
	
	 public Group_IFI getGroupById(long id);
	 
	 public void saveOrUpdate(Group_IFI group);
	 
	 public void deleteGroup(long id);
}
