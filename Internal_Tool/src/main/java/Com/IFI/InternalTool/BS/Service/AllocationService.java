package Com.IFI.InternalTool.BS.Service;
import java.util.List;

import Com.IFI.InternalTool.DS.Model.Allocation;
import Com.IFI.InternalTool.DS.Model.AllocationDetail;


public interface AllocationService {

	boolean createAllocation(final Allocation allocation);
	
	boolean deleteByID(final Long allocation_id);

	List<Allocation> getAllocations(final int page,final int pageSize);
	
	Allocation findById(final long allocation_id);
	
	List<Allocation> SearchAllocationWithTime(final int year, final int month, final int page,
			final int pageSize);
	
	List<Allocation> findAllocationByEmployeeID(final long employee_id, final int page,
			final int pageSize);
	
	List<Allocation> findAllocationByProjectID(final long employee_id, final int page,
			final int pageSize);
	
	boolean saveAllocationDetail(final AllocationDetail allocationDetail);
	//public PagedResponse<AllocationResponse> getAllocations1(final int page,final int pageSize,final Boolean desc);

}
