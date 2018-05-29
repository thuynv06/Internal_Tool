package Com.IFI.InternalTool.BS.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Com.IFI.InternalTool.BS.Service.AllocationService;
import Com.IFI.InternalTool.DS.DAO.AllocationDAO;
import Com.IFI.InternalTool.DS.Model.Allocation;

@Service("AllocationService")
public class AllocationServiceImpl implements AllocationService{
	
	@Autowired
	AllocationDAO allocationDAO;
	
	@Override
	public boolean saveAllocation(Allocation allocation) {
		 return allocationDAO.saveAllocation(allocation);
	}
	
	@Override
	public boolean deleteAllocation(long allocation_id) {
		return allocationDAO.deleteAllocation(allocation_id);
	}
	
	@Override
	public Allocation getAllocationById(long allocation_id) {
		return allocationDAO.getAllocationById(allocation_id);
	}
	
}
