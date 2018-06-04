 package Com.IFI.InternalTool.BS.Service;

import java.util.HashSet;
import java.util.List;

import Com.IFI.InternalTool.DS.Model.Allocation;

import Com.IFI.InternalTool.Payloads.AllocationResponse;
import Com.IFI.InternalTool.Payloads.PagedResponse;

public interface AllocationService {
 
	boolean createAllocation(Allocation allocation);
	
	public PagedResponse<AllocationResponse>  getAllocation(int page, int pageSize,Boolean desc);
	
	
}
