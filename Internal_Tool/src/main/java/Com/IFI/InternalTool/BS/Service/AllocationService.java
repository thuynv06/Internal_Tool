package Com.IFI.InternalTool.BS.Service;

import java.sql.Date;
import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;

import Com.IFI.InternalTool.DS.Model.Allocation;
import Com.IFI.InternalTool.DS.Model.AllocationDetail;
import Com.IFI.InternalTool.Security.CurrentUser;
import Com.IFI.InternalTool.Security.UserPrincipal;

public interface AllocationService {

	// find Allocation By Id
	Allocation findById(final long allocation_id);

	@PreAuthorize("hasRole('LEADER_A') OR hasRole('LEADER_B') OR hasRole('LEADER_C') OR hasRole('ADMIN')")
	boolean createAllocation(final long currentUserID, final Allocation allocation);

	public Boolean updateAllocation(final long currentUserID,Allocation allocation);
	
	@PreAuthorize("hasRole('LEADER_A') OR hasRole('LEADER_B') OR hasRole('LEADER_C') OR hasRole('ADMIN')")
	boolean deleteByID(final Long allocation_id);

	List<Allocation> getAllocations(final long employee_id, final int page, final int pageSize);

	// get allocated of manager
	@PreAuthorize("hasRole('LEADER_A') OR hasRole('LEADER_B') OR hasRole('LEADER_C') OR hasRole('ADMIN')")
	List<Allocation> getAllocatedofManager(final long employee_id, final int page, final int pageSize);

	Long NumRecordsAllocatedofManager(final long employee_id);

	// search allocation with month or year
	List<Allocation> SearchAllocationWithTime(final int year, final int month, final int page, final int pageSize);

	// find AllocationByEmPloyeeID
	List<Allocation> findAllocationByEmployeeID(final long employee_id, final int page, final int pageSize);

	Long NumRecordsAllocationByEmployeeID(final long employee_id);

	// find AllocationByProjectID
	List<Allocation> findAllocationByProjectID(final long project_id, final int page, final int pageSize);

	Long NumRecordsAllocationByProjectID(final long employee_id);

	// save AllocationDetail
	boolean saveAllocationDetail(final AllocationDetail allocationDetail);

	// tim kiem allocation tu ngay den ngay
	List<Allocation> findAllocationFromDateToDate(Date fromDate, Date toDate, int page, int pageSize);

	public Long NumRecordsllocationFromDateToDate(Date fromDate, Date toDate);
}
