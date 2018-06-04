package Com.IFI.InternalTool.BS.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import Com.IFI.InternalTool.BS.Service.Impl.EmployeeServiceImpl;
import Com.IFI.InternalTool.Payloads.Payload;

import Com.IFI.InternalTool.Utils.AppConstants;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	@Autowired
	EmployeeServiceImpl employeeService;

	@Autowired
	AuthenticationManager authenticationManager;
	Payload message = new Payload();
	Object data = "";

	private static final Logger logger = LoggerFactory.getLogger(Group_IFIController.class);

	@GetMapping
	public @ResponseBody Payload getEmployees(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize,
			@RequestParam("sortedColumn") String sortedColumn, @RequestParam("desc") Boolean desc) {

		try {
			data = employeeService.getAllEmployee(page, pageSize, sortedColumn, desc);
		} catch (Exception e) {
			logger.error("ERROR: Get connection error", e);
			message.setPayLoad(data, AppConstants.STATUS_KO, AppConstants.FAILED_CODE,
					"ERROR: Get connection error" + e, false);
			return message;
		}
		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Get Employees Successfull", true);
		return message;
	}

	
	
}
