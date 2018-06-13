package Com.IFI.InternalTool.BS.Controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import Com.IFI.InternalTool.BS.Service.Impl.EmployeeServiceImpl;
import Com.IFI.InternalTool.DS.Model.Allocation;
import Com.IFI.InternalTool.DS.Model.Employee;
import Com.IFI.InternalTool.DS.Model.Project;
import Com.IFI.InternalTool.Payloads.Payload;
import Com.IFI.InternalTool.Security.CurrentUser;
import Com.IFI.InternalTool.Security.UserPrincipal;
import Com.IFI.InternalTool.Utils.AppConstants;
import Com.IFI.InternalTool.Utils.Business;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	@Autowired
	EmployeeServiceImpl employeeService;
	Payload message = new Payload();
	Object data = "";

	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@GetMapping
	public @ResponseBody Payload getEmployees(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {

		// logger.info(currentUser.getId()+ " ID Current User");

		boolean hasUserRole = currentUser.getAuthorities().stream()
				.anyMatch(r -> r.getAuthority().equals("ROLE_EMPLOYEE"));

		try {
			data = employeeService.getAllEmployees(hasUserRole, currentUser.getId(), page, pageSize);
		} catch (Exception e) {
			logger.error("ERROR: Get connection error", e);
			message.setPayLoad("FAILED", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
					false);
			return message;
		}
		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Get Employees Successfull", true);
		return message;
	}

	// Find Employee By Id
	@GetMapping("/{employee_id}")
	// @RolesAllowed("ROLE_USER")
	public @ResponseBody Payload findEmployeeById(@PathVariable Long employee_id) {
		logger.info("Find Employee By Id ... ");

		try {
			data = employeeService.getEmployeeById(employee_id);
		} catch (Exception e) {
			logger.error("ERROR:", e.getMessage());
			message.setPayLoad("FAILED", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR:" + e.getMessage(),
					false);
			return message;
		}
		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Find Employee By ID Successfull",
				true);
		return message;

	}

	// create Allocation
	@PostMapping("/create")
	public @ResponseBody Payload createEmployee(@CurrentUser UserPrincipal currentUser,
			@Valid @RequestBody Employee emp) {
		logger.info("Create Employee ... ");
		try {
			employeeService.createEmployee(currentUser.getId(), emp);

		} catch (Exception e) {
			logger.error("ERROR: Get connection error", e.getMessage());
			message.setPayLoad("false", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR:" + e.getMessage(),
					false);
			return message;
		}

		return message;

	}

	@PostMapping("/update")
	// @PreAuthorize("hasRole('ROLE_USER')")
	public @ResponseBody Payload updateEmployee(@Valid @RequestBody Employee emp) {
		logger.info("Update Employee... ");

		try {
			data = employeeService.EditEmployee(emp);
		} catch (Exception e) {
			logger.error("ERROR: Get connection error", e);
			message.setPayLoad(data, AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
					false);
			return message;
		}
		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Update Employee Successfull",
				true);
		return message;
	}

	// find employees with name likes
	@GetMapping("/findEmployeeNameLike/")
	public @ResponseBody Payload findEmployeeNameLike(@RequestParam("name") String nameLike,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
		logger.info("Find Employees Name Like ... ");
		try {
			data = employeeService.findEmployeeNameLike(nameLike, page, pageSize);
			Long count = employeeService.NumRecordsEmployeeNameLike(nameLike);
			message.setPages(Business.getTotalsPages(count, pageSize));
		} catch (Exception e) {
			logger.error("ERROR: Get connection error", e);
			message.setPayLoad("FAILED", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR:" + e.getMessage(),
					false);
			return message;
		}
		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Find Employees Name Like ... ",
				true);
		return message;
	}

	@GetMapping("/findEmployeeByGroupId/{group_id}")
	public @ResponseBody Payload findEmployeeByGroupId(@PathVariable(value = "group_id") String group_id,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
		logger.info("Find Employees By Group ID ... ");
		try {
			data = employeeService.findEmployeeByGroupId(group_id, page, pageSize);
			Long count = employeeService.NumRecordsEmployeeInGroup(group_id);
			message.setPages(Business.getTotalsPages(count, pageSize));
		} catch (Exception e) {
			logger.error("ERROR: Get connection error", e);
			message.setPayLoad("FAILED", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
					false);
			return message;
		}
		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Find Employees By Group ID ... ",
				true);
		return message;
	}

	@GetMapping("/getListEmployeeInProject")
	public @ResponseBody Payload getListEmployeeInProject(@RequestParam(value = "project_id") long project_id,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
		logger.info("Find Employees IN Project ... ... ");
		try {
			data = employeeService.getListEmployeeInProject(project_id, page, pageSize);
			Long count = employeeService.NumRecordsEmployeeInProject(project_id);
			message.setPages(Business.getTotalsPages(count, pageSize));

		} catch (Exception e) {
			logger.error("ERROR: Get connection error", e);
			message.setPayLoad("FAILED", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
					false);
			return message;
		}
		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE,
				"Find Employees IN Project ... ... ", true);
		return message;
	}

	@GetMapping("/getListEmployeeNotInProject")
	public @ResponseBody Payload getListEmployeeNOTInProject(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "project_id") long project_id,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
		logger.info("Find Employees Not IN Project ... ");
		try {
			data = employeeService.getListEmployeeNotInProject(currentUser.getId(), project_id, page, pageSize);
			Long count = employeeService.NumRecordsEmployeeNotInProject(currentUser.getId(), project_id);
			message.setPages(Business.getTotalsPages(count, pageSize));

		} catch (Exception e) {
			logger.error("ERROR: Get connection error", e);
			message.setPayLoad("FAILED", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
					false);
			return message;
		}
		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE,
				"Find Employees Not IN Project ... ", true);
		return message;
	}

}
