package Com.IFI.InternalTool.BS.Controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import Com.IFI.InternalTool.DS.Model.Project;
import Com.IFI.InternalTool.Payloads.Payload;
import Com.IFI.InternalTool.Utils.AppConstants;
import Com.IFI.InternalTool.BS.Service.Impl.ProjectServiceImpl;

@RestController
@RequestMapping("/api/project")
public class ProjectController {
	
	@Autowired
	private ProjectServiceImpl projectService;

	@Autowired
	AuthenticationManager authenticationManager;
	Payload message = new Payload();
	Object data = "";
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

	
	@PostMapping("/create")
	// @RolesAllowed("ROLE_USER")
	public @ResponseBody Payload createProject(@Valid @RequestBody Project projectRequest) {
		logger.info("Create Project ... ");

		try {
			data = projectService.createProject(projectRequest);
		} catch (Exception e) {
			logger.error("ERROR: Get connection error", e);
			message.setPayLoad(data, AppConstants.STATUS_KO, AppConstants.FAILED_CODE,
					"ERROR: Get connection error" + e, false);
			return message;
		}
		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Create Project Successfull", true);
		return message;
	}
	
	// find Project by ID
	
	@PostMapping("/findProjectById")
	// @RolesAllowed("ROLE_USER")
	public @ResponseBody Payload findProjectById(@RequestParam("project_id") long project_id) {
		logger.info("Get Project By Id ... ");
		try {
			data = projectService.getProjectById(project_id);
		} catch (Exception e) {
			logger.error("ERROR: Get connection error", e);
			message.setPayLoad(data, AppConstants.STATUS_KO, AppConstants.FAILED_CODE,
					"ERROR: Get connection error" + e, false);
			return message;
		}
		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Find Project By ID Successfull",
				true);
		return message;

	}
	
	
	// 
	@PostMapping("/findProjectLikeName")
	// @RolesAllowed("ROLE_USER")
	public @ResponseBody Payload findProjectLikeName(@RequestParam("projectName") String projectName) {
		logger.info("Find Project Like Name ... ");

		try {
			data = projectService.findProjectsLikeName(projectName);
		} catch (Exception e) {
			logger.error("ERROR: Get connection error", e);
			message.setPayLoad(data, AppConstants.STATUS_KO, AppConstants.FAILED_CODE,
					"ERROR: Get connection error" + e, false);
			return message;
		}
		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Find Project Like Name Successfull",
				true);
		return message;

	}
	
	
	
	
}