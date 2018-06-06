package Com.IFI.InternalTool.BS.Controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import Com.IFI.InternalTool.BS.Service.Impl.AllocationServiceImpl;
import Com.IFI.InternalTool.DS.Model.Allocation;
import Com.IFI.InternalTool.Payloads.Payload;
import Com.IFI.InternalTool.Utils.AppConstants;

@RestController
@RequestMapping("/api/allocations")
public class AllocationController {

	@Autowired
	private AllocationServiceImpl allocationService;

	private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

	Payload message = new Payload();
	Object data = "";

	@GetMapping
	
	public @ResponseBody Payload getAllocations(@RequestParam(value="page",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page ,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
		try {
			data = allocationService.getAllocations(page, pageSize);
		} catch (Exception e) {
			logger.error("ERROR: Get connection error", e);
			message.setPayLoad(data, AppConstants.STATUS_KO, AppConstants.FAILED_CODE,
					"ERROR: Get connection error" + e.getMessage(), false);
			return message;
		}
		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Get Allocations Successfull",
				true);
		return message;
	}

	
	@PostMapping("/create")
	// @RolesAllowed("ROLE_USER")
	public @ResponseBody Payload createAllocation(@Valid @RequestBody Allocation allocation) {
		logger.info("Create allocation ... ");

		if (allocationService.createAllocation(allocation)) {
			message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Create allocation Successfull",
					true);
			return message;
		} else {
			logger.error("ERROR: Some things wrong error");
			message.setPayLoad(data, AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: Some things wrong",
					false);
			return message;
		}

	}

	// Find Allocation By Id
	@GetMapping("/findById/{allocation_id}")
	// @RolesAllowed("ROLE_USER")
	public @ResponseBody Payload findAllocationById(@PathVariable Long allocation_id) {
		logger.info("Find Allocation By Id ... ");

		try {
			data = allocationService.findById(allocation_id);
		} catch (Exception e) {
			logger.error("ERROR: Get connection error", e.getMessage());
			message.setPayLoad(data, AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR:" + e.getMessage(),
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
			message.setPayLoad(data, AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: Some things wrong error",
					false);
			return message;
		}
	}

	@GetMapping("/searchAllcationWithTime")
	public @ResponseBody Payload searchAllocationWithMonth(@RequestParam("year") int year,
			@RequestParam("month") int month, @RequestParam("page") int page, @RequestParam("pageSize") int pageSize) {
		logger.info("search Allcation With Month ... ");
		try {
			data = allocationService.SearchAllocationWithTime(year, month, page, pageSize);
		} catch (Exception e) {
			logger.error("ERROR: Get connection error", e.getMessage());
			message.setPayLoad(data, AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
					false);
			return message;
		}
		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE,
				"search Allcation With Month Successfull", true);
		return message;
	}

	@GetMapping("/findAllocationByEmployeeID")
	public @ResponseBody Payload findAllocationByEmployeeID(@RequestParam("employee_id") int employee_id,
			@RequestParam("page") int page, @RequestParam("pageSize") int pageSize) {
		logger.info("search Allcation With Employee ID ... ");
		try {
			data = allocationService.findAllocationByEmployeeID(employee_id, page, pageSize);
		} catch (Exception e) {
			logger.error("ERROR: Get connection error", e.getMessage());
			message.setPayLoad(data, AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR:" + e.getMessage(),
					false);
			return message;
		}
		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE,
				"search Allcation With Employee ID Successfull", true);
		return message;
	}

	@GetMapping("/findAllocationByProjectID")
	public @ResponseBody Payload findAllocationByProjectID(@RequestParam("projecct_id") int project_id,
			@RequestParam("page") int page, @RequestParam("pageSize") int pageSize) {
		logger.info("find Allcation By Project ID ... ");
		try {
			data = allocationService.findAllocationByProjectID(project_id, page, pageSize);
		} catch (Exception e) {
			logger.error("ERROR: Get connection error", e.getMessage());
			message.setPayLoad(data, AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
					false);
			return message;
		}
		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE,
				"find Allcation By Project ID  ID Successfull", true);
		return message;
	}

}
