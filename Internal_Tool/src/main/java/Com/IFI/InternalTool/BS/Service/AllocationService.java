package Com.IFI.InternalTool.BS.Service;

import Com.IFI.InternalTool.DS.Model.Allocation;
import Com.IFI.InternalTool.DS.Model.Group_IFI;
import Com.IFI.InternalTool.Payloads.PagedResponse;

public interface AllocationService {
 
	public Allocation createAllocation(Allocation allocation);
	
	public PagedResponse<Allocation>  getAllAllocation();
	
	
}
