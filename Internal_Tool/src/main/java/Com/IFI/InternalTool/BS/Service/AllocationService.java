 package Com.IFI.InternalTool.BS.Service;

import Com.IFI.InternalTool.DS.Model.Allocation;
import Com.IFI.InternalTool.DS.Model.Group_IFI;
import Com.IFI.InternalTool.Payloads.PagedResponse;

public interface AllocationService {
 
	boolean createAllocation(Allocation allocation);
	
	public PagedResponse<Allocation>  getAllAllocation();
	
	
}
