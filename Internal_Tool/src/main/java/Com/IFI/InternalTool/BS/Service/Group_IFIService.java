package Com.IFI.InternalTool.BS.Service;

import java.util.List;

import Com.IFI.InternalTool.DS.Model.Group_IFI;
import Com.IFI.InternalTool.Payloads.PagedResponse;

public interface Group_IFIService {

	public Group_IFI createGroupIFI(final Group_IFI group);

	public Boolean deleteGroupById(final String group_id);

	public Group_IFI getGroupById(final String group_id);

	List<Group_IFI> findGroupNameLike(final String name, final int page, final int pageSize);

	List<Group_IFI> getGroups(final int page, final int pageSize);

}
