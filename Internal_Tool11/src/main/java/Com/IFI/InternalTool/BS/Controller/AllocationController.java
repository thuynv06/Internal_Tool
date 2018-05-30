package Com.IFI.InternalTool.BS.Controller;

import org.hibernate.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import Com.IFI.InternalTool.BS.Service.AllocationService;
import Com.IFI.InternalTool.DS.Model.Allocation;
import Com.IFI.InternalTool.DS.Model.Employee;

@RestController
@RequestMapping("/api")
public class AllocationController {
	@Autowired
	AllocationService allocationService;
	
	private static final Logger logger = LoggerFactory.getLogger(AllocationController.class);
	Payload message = new Payload();
	Object data = "";
	
	// create Allocation
	@PostMapping("/allocations/create")
	public @ResponseBody Payload saveEmployee(@RequestBody Allocation allocation) {
		logger.info("Create Allocation = ");
		try {
			data = allocationService.saveAllocation(allocation);
		} catch (CacheException e) {
			logger.error("ERROR: Get connection error", e);
			message.setPayload("Code_faile","STATUS_KO","ERROR"+e.getMessage(),data);
			return message;
		}
		message.setPayload("Success_Code", "STATUS_OK", " Create Successfull", data);
		return message;
	}
}
