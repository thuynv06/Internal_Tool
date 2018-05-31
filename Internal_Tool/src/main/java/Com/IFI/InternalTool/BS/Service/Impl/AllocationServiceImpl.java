package Com.IFI.InternalTool.BS.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Com.IFI.InternalTool.BS.Service.AllocationService;
import Com.IFI.InternalTool.DS.DAO.AllocationDAO;
import Com.IFI.InternalTool.DS.Model.Allocation;
import Com.IFI.InternalTool.Payloads.PagedResponse;
import Com.IFI.InternalTool.Utils.Business;
@Service
public class AllocationServiceImpl implements AllocationService{
	
	@Autowired
	AllocationDAO allocationDAO;
	Business business = new Business();
	
	
	@Override
	public Allocation createAllocation(Allocation allocation) {
		
		allocation.setMonth(business.getMonth(allocation.getStart_date()));
		allocation.setYear(business.getYear(allocation.getStart_date()));
		return allocationDAO.save(allocation);
	}


	@Override
	public PagedResponse<Allocation> getAllAllocation() {
		return null;
		
	}
	
	
	
	
	
}
