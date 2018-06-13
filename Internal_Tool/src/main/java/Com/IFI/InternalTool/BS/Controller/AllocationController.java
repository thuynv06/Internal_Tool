package Com.IFI.InternalTool.BS.Controller;

import java.sql.Date;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import Com.IFI.InternalTool.BS.Service.Impl.AllocationServiceImpl;
import Com.IFI.InternalTool.DS.Model.Allocation;
import Com.IFI.InternalTool.Payloads.Payload;
import Com.IFI.InternalTool.Security.CurrentUser;
import Com.IFI.InternalTool.Security.UserPrincipal;
import Com.IFI.InternalTool.Utils.AppConstants;
import Com.IFI.InternalTool.Utils.Business;

@RestController
@RequestMapping("/api/allocations")
public class AllocationController {

	@Autowired
	private AllocationServiceImpl allocationService;

	private static final Logger logger = LoggerFactory.getLogger(AllocationController .class);

	Payload message = new Payload();
	Object data = "";

	// get Allocation With Employee ID
	@GetMapping(("/allocations"))
	public @ResponseBody Payload getAllocations(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {

		// boolean hasUserRole = currentUser.getAuthorities().stream()
		// .anyMatch(r -> r.getAuthority().equals("ROLE_EMPLOYEE"));
		// logger.info(hasUserRole + " role");

		try {
			data = allocationService.getAllocations(currentUser.getId(), page, pageSize);
			Long count = allocationService.NumRecordsAllocationByEmployeeID(currentUser.getId());
			message.setPages(Business.getTotalsPages(count, pageSize));

		} catch (Exception e) {
			logger.error("ERROR: Get connection error", e);
			message.setPayLoad("FAILED", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
					false);
			return message;
		}
		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Get Allocations Successfull",
				true);
		return message;
	}

	// get Allocated OF Manager
	@GetMapping("/allocated")
	public @ResponseBody Payload getAllocatedofManager(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
		try {
			data = allocationService.getAllocatedofManager(currentUser.getId(), page, pageSize);
			Long count = allocationService.NumRecordsAllocatedofManager(currentUser.getId());
			message.setPages(Business.getTotalsPages(count, pageSize));

		} catch (Exception e) {
			logger.error("ERROR: Get connection error", e);
			message.setPayLoad("FAILED", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
					false);
			return message;
		}
		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE,
				"Get Allocated By Manager Successfull", true);
		return message;
	}

	// create Allocation
	@PostMapping("/create")
	public @ResponseBody Payload createAllocation(@CurrentUser UserPrincipal currentUser,
			@Valid @RequestBody Allocation allocation) {
		logger.info("Create allocation ... ");

		try {
			if (allocationService.createAllocation(currentUser.getId(), allocation)) {
				message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE,
						"Create Allocation By ID Successfull", true);
			} else {
				message.setPayLoad("false", AppConstants.STATUS_KO, AppConstants.FAILED_CODE,
						"ERROR: end_date < start_date or start_date is smaller than max end_date is history", false);
			}

		} catch (Exception e) {
			logger.error("ERROR: Get connection error", e.getMessage());
			message.setPayLoad("false", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR:" + e.getMessage(),
					false);
			return message;
		}

		return message;

	}

	// Find Allocation By Id
	@GetMapping("/{allocation_id}")
	public @ResponseBody Payload findAllocationById(@PathVariable Long allocation_id) {
		logger.info("Find Allocation By Id ... ");

		try {
			data = allocationService.findById(allocation_id);
		} catch (Exception e) {
			logger.error("ERROR: Get connection error", e.getMessage());
			message.setPayLoad("FAILED", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR:" + e.getMessage(),
					false);
			return message;
		}
		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Find Allocation By ID Successfull",
				true);
		return message;

	}

	// delete Allocation - Only Leader or Admin has roles
	@DeleteMapping(path = "deleteById/{allocation_id}")
	public @ResponseBody Payload DeleteAllocationById(@PathVariable Long allocation_id) {
		logger.info("Delete Allocation By Id ... ");

		if (allocationService.deleteByID(allocation_id)) {
			message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE,
					"Delete Allocation By ID Successfull", true);
			return message;
		} else {
			logger.error("ERROR: Get connection error");
			message.setPayLoad("FAILED", AppConstants.STATUS_KO, AppConstants.FAILED_CODE,
					"ERROR: Some things wrong error", false);
			return message;
		}
	}

	@GetMapping("/searchAllcationWithTime")
	public @ResponseBody Payload searchAllocationWithMonth(@RequestParam(value = "year") int year,
			@RequestParam("month") int month,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
		logger.info("search Allcation With Month ... ");
		logger.info(year + " ");
		try {
			data = allocationService.SearchAllocationWithTime(year, month, page, pageSize);
		} catch (Exception e) {
			logger.error("ERROR: Get connection error", e.getMessage());
			message.setPayLoad("false", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
					false);
			return message;
		}
		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE,
				"search Allcation With Month Successfull", true);
		return message;
	}

	@GetMapping("/findAllocationByEmployeeID")
	public @ResponseBody Payload findAllocationByEmployeeID(@RequestParam("employee_id") int employee_id,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
		logger.info("search Allcation With Employee ID ... ");
		try {
			data = allocationService.findAllocationByEmployeeID(employee_id, page, pageSize);
			Long count = allocationService.NumRecordsAllocationByEmployeeID(employee_id);
			message.setPages(Business.getTotalsPages(count, pageSize));

		} catch (Exception e) {
			logger.error("ERROR: Get connection error", e.getMessage());
			message.setPayLoad("false", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR:" + e.getMessage(),
					false);
			return message;
		}
		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE,
				"search Allcation With Employee ID Successfull", true);
		return message;
	}

	@GetMapping("/findAllocationByProjectID")
	public @ResponseBody Payload findAllocationByProjectID(@RequestParam("projecct_id") int project_id,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
		logger.info("find Allcation By Project ID ... ");
		try {
			data = allocationService.findAllocationByProjectID(project_id, page, pageSize);
			Long count = allocationService.NumRecordsAllocationByProjectID(project_id);
			message.setPages(Business.getTotalsPages(count, pageSize));

		} catch (Exception e) {
			logger.error("ERROR: Get connection error", e.getMessage());
			message.setPayLoad("false", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
					false);
			return message;
		}
		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE,
				"find Allcation By Project ID  ID Successfull", true);
		return message;
	}

	@GetMapping("/findAllocationFromDateToDate")
	public @ResponseBody Payload findAllocationFromDateToDate(@RequestParam("from_date") Date from_date,
			@RequestParam("to_date") Date to_date,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
		logger.info("findAllocationFromDateToDate ... ");
		try {
			data = allocationService.findAllocationFromDateToDate(from_date, to_date, page, pageSize);
			Long count = allocationService.NumRecordsllocationFromDateToDate(from_date, to_date);
			message.setPages(Business.getTotalsPages(count, pageSize));
		} catch (Exception e) {
			logger.error("ERROR: Get connection error", e.getMessage());
			message.setPayLoad("false", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
					false);
			return message;
		}
		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE,
				"find Allcation By Project ID  ID Successfull", true);
		return message;
	}

}
