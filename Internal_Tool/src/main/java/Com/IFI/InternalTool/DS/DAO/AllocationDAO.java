package Com.IFI.InternalTool.DS.DAO;


import java.time.LocalDate;
import java.util.List;
import Com.IFI.InternalTool.DS.Model.Allocation;
public interface AllocationDAO {

	List<Allocation> getAllocations(int page,int pageSize);

	//PagedResponse<AllocationResponse> getAllocation1(int page,int pageSize,Boolean desc);
	
	boolean saveAllocation(Allocation allocation);

	boolean deleteById(long allocation_id);

	Allocation findById(long allocation_id);

	LocalDate findMaxEndDate(long employee_id);
	
	List<Allocation> findAllocationByEmployeeID (final long employee_id,final int page,final int pageSize);
	
	List<Allocation> searchAllocationWithTime (final int year,final int month,final int page,final int pageSize);
	
	List<Allocation> findAllocationByProjectID (final long project_id,final int page,final int pageSize);
}
