package Com.IFI.InternalTool.BS.Service;

import java.util.List;

import Com.IFI.InternalTool.DS.Model.Group_IFI;
import Com.IFI.InternalTool.Payloads.PagedResponse;

public interface Group_IFIService {
	// create group
	public void saveGroupIFI(final Group_IFI group);

	// delete group by ID
	public Boolean deleteGroupById(final String group_id);

	// getGroupByID
	public Group_IFI getGroupById(final String group_id);

	// findGroupNameLike
	List<Group_IFI> findGroupNameLike(final String name, final int page, final int pageSize);

	// get list groups and paginations
	List<Group_IFI> getGroups(final int page, final int pageSize);

}
