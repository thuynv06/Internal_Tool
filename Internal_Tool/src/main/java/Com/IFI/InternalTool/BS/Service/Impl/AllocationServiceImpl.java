package Com.IFI.InternalTool.BS.Service.Impl;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Com.IFI.InternalTool.BS.Service.AllocationService;
import Com.IFI.InternalTool.DS.DAO.AllocationDAO;
import Com.IFI.InternalTool.DS.Model.Allocation;
import Com.IFI.InternalTool.Payloads.AllocationResponse;
import Com.IFI.InternalTool.Payloads.PagedResponse;
import Com.IFI.InternalTool.Utils.Business;

@Service
public class AllocationServiceImpl implements AllocationService {

	@Autowired
	AllocationDAO allocationDAO;

	private static final Logger logger = LoggerFactory.getLogger(AllocationServiceImpl.class);

	@Override
	public boolean createAllocation(Allocation allocation) {
		// end_date must be > start_date
		if (allocation.getEnd_date().before(allocation.getStart_date()) ) {
			return false;
		}
		
		// get maxEndDate Allocation in History
		Date maxEndDate = allocationDAO.findMaxEndDate(allocation.getEmployee_id());
		logger.info(maxEndDate + "max");
		// check start_date with maxEndDate
		if ( allocation.getStart_date().before(maxEndDate)
				|| allocation.getStart_date().equals(maxEndDate)) {
			return false;
		}
		// get month , get year 
		int month = allocation.getStart_date().toLocalDate().getMonthValue();
		int year = allocation.getStart_date().toLocalDate().getYear();
		
		// get distance Time between start_date vs end_date not set Weekends;
		int distanceTime= Business.getDistanceTime(allocation.getStart_date().toLocalDate(),allocation.getEnd_date().toLocalDate());
		logger.info( "distance Time: " + distanceTime);
		
		// get number days of month // get nums days weekend of month
		int numDaysOfMonth = allocation.getStart_date().toLocalDate().lengthOfMonth();
		int numDaysWeekOfMonth = Business.numberWeekendOfMonth(month, year);

		// set allocation_plan
		double allocation_plan = Business.getAllocation_Plan(numDaysOfMonth, numDaysWeekOfMonth,distanceTime);
		logger.info( "Allocation_Plan: " + allocation_plan);		
		allocation.setAllocation_plan(allocation_plan);
		
		logger.info( "expert" + allocation_plan);
		
		allocation.setMonth(allocation.getStart_date().toLocalDate().getMonthValue());
		allocation.setYear(allocation.getStart_date().toLocalDate().getYear());
		if (allocationDAO.saveAllocation(allocation)) {
			return true;
		} else {
			return false;
		}

	}

	
	@Override
	public PagedResponse<AllocationResponse> getAllocation(int page, int pageSize, Boolean desc) {
		
		return allocationDAO.getAllocation(page, pageSize, desc);
	}

}
