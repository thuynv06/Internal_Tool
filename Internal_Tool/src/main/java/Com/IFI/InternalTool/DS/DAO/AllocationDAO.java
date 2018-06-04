package Com.IFI.InternalTool.DS.DAO;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;


import Com.IFI.InternalTool.DS.Model.Allocation;
import Com.IFI.InternalTool.Payloads.AllocationResponse;
import Com.IFI.InternalTool.Payloads.PagedResponse;

public interface AllocationDAO {

	List<Allocation> getAllAllocation(int page,int pageSize,Boolean desc);

	PagedResponse<AllocationResponse> getAllocation(int page,int pageSize,Boolean desc);
	
	boolean saveAllocation(Allocation allocation);

	boolean deleteAlocation(long allocation_id);

	Allocation getAllocationById(long allocation_id);

	Date findMaxEndDate(long employee_id);
	
}
