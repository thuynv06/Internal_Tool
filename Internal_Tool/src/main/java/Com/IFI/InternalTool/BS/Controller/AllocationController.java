package Com.IFI.InternalTool.BS.Controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public @ResponseBody Payload getAllocation(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize,
			 @RequestParam("desc") Boolean desc) {
		try {
			data = allocationService.getAllocation(page, pageSize,desc);
		} catch (Exception e) {
			logger.error("ERROR: Get connection error", e);
			message.setPayLoad(data, AppConstants.STATUS_KO, AppConstants.FAILED_CODE,
					"ERROR: Get connection error" + e, false);
			return message;
		}
		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Get Allocations Successfull", true);
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
}
