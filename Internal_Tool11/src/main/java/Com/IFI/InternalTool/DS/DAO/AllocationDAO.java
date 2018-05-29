package Com.IFI.InternalTool.DS.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import Com.IFI.InternalTool.DS.Model.Allocation;
import Com.IFI.InternalTool.DS.Model.Employee;

@Repository
public interface AllocationDAO {
	boolean saveAllocation(Allocation allocation);
	boolean deleteAllocation(long allocation_id);
	Allocation getAllocationById(long allocation_id);
	
	
}
