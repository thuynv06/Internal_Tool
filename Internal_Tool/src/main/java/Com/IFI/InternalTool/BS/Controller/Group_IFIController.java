package Com.IFI.InternalTool.BS.Controller;

import java.net.URI;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import Com.IFI.InternalTool.BS.Service.Impl.Group_IFIServiceImpl;
import Com.IFI.InternalTool.DS.Model.Group_IFI;
import Com.IFI.InternalTool.Payloads.Payload;
import Com.IFI.InternalTool.Payloads.GroupRequest;
import Com.IFI.InternalTool.Payloads.PagedResponse;
import Com.IFI.InternalTool.Utils.AppConstants;

@RestController
@RequestMapping("/api/group")
public class Group_IFIController {
	@Autowired
	Group_IFIServiceImpl groupService;

	@Autowired
	AuthenticationManager authenticationManager;
	Payload message = new Payload();
	Object data = "";

	private static final Logger logger = LoggerFactory.getLogger(Group_IFIController.class);

	// get all group
	@GetMapping
	// @RolesAllowed("ROLE_USER")
	public @ResponseBody Payload getAllGroup() {
		logger.info("Get All Groups ... ");
		try {
			data = groupService.getAllGroup();
		} catch (Exception e) {
			logger.error("ERROR: Get connection error", e);
			message.setPayLoad(data, AppConstants.STATUS_KO, AppConstants.FAILED_CODE,
					"ERROR: Get connection error" + e, false);
			return message;
		}
		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Get Gruops Successfull", true);
		return message;
	}

	// find Group By Name
	@GetMapping("/findGroupByName/{groupName}")
	// @RolesAllowed("ROLE_USER")
	public @ResponseBody Payload findGroupByName(@PathVariable String groupName) {
		logger.info("Find Group By Name ... ");

		try {
			data = groupService.findGroupByName(groupName);
		} catch (Exception e) {
			logger.error("ERROR: Get connection error", e);
			message.setPayLoad(data, AppConstants.STATUS_KO, AppConstants.FAILED_CODE,
					"ERROR: Get connection error" + e, false);
			return message;
		}
		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Find Group By Name Successfull",
				true);
		return message;

	}

	@PostMapping("/findGroupLikeName")
	// @RolesAllowed("ROLE_USER")
	public @ResponseBody Payload findGroupLikeName(@RequestParam("groupName") String groupName) {
		logger.info("Find Group Like Name ... ");

		try {
			data = groupService.findGroupsLikeName(groupName);
		} catch (Exception e) {
			logger.error("ERROR: Get connection error", e);
			message.setPayLoad(data, AppConstants.STATUS_KO, AppConstants.FAILED_CODE,
					"ERROR: Get connection error" + e, false);
			return message;
		}
		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Find Group Like Name Successfull",
				true);
		return message;

	}

	@PostMapping("/findGroupById")
	// @RolesAllowed("ROLE_USER")
	public @ResponseBody Payload findGroupById(@RequestParam("group_id") String group_id) {
		logger.info("Find Group By Id ... ");
		
		try {
			data = groupService.getGroupById(group_id);
		} catch (Exception e) {
			logger.error("ERROR: Get connection error", e);
			message.setPayLoad(data, AppConstants.STATUS_KO, AppConstants.FAILED_CODE,
					"ERROR: Get connection error" + e, false);
			return message;
		}
		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Find Group By ID Successfull",
				true);
		return message;

	}

	// create new Group
	@PostMapping("/create")
	// @RolesAllowed("ROLE_USER")
	public @ResponseBody Payload createGroup(@Valid @RequestBody GroupRequest groupRequest) {
		logger.info("Create Group ... ");

		try {
			data = groupService.createGroupIFI(groupRequest);
		} catch (Exception e) {
			logger.error("ERROR: Get connection error", e);
			message.setPayLoad(data, AppConstants.STATUS_KO, AppConstants.FAILED_CODE,
					"ERROR: Get connection error" + e, false);
			return message;
		}
		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Create group Successfull", true);
		return message;
	}

}
