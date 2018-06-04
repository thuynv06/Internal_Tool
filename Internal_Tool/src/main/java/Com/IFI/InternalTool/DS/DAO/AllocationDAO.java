package Com.IFI.InternalTool.DS.DAO;

import java.sql.Date;
import java.util.List;


import Com.IFI.InternalTool.DS.Model.Allocation;
import Com.IFI.InternalTool.DS.Model.Project;

public interface AllocationDAO {

	List<Project> getAllAllocation(int page,int pageSize,String sortedColumn,Boolean desc);

	boolean saveAllocation(Allocation allocation);

	boolean deleteAlocation(long allocation_id);

	Allocation getAllocationById(long allocation_id);

	Date findMaxEndDate(long employee_id);
	
}
