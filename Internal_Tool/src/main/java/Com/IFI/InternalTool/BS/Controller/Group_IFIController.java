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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import Com.IFI.InternalTool.BS.Service.Impl.Group_IFIServiceImpl;
import Com.IFI.InternalTool.DS.Model.Group_IFI;
import Com.IFI.InternalTool.Payloads.ApiResponse;
import Com.IFI.InternalTool.Payloads.GroupRequest;
import Com.IFI.InternalTool.Payloads.PagedResponse;


@RestController
@RequestMapping("/api/group")
public class Group_IFIController {
	@Autowired
	Group_IFIServiceImpl groupService;
	
	@Autowired
    AuthenticationManager authenticationManager;
	
	private static final Logger logger = LoggerFactory.getLogger(Group_IFIController.class);
    // get all group
	@GetMapping
	//@RolesAllowed("ROLE_USER")
    public PagedResponse<Group_IFI> getAllGroup() {
    	logger.info("Get All Groups ... ");
        return groupService.getAllGroup();
    }
	
    //find Group By Name
    @GetMapping ("/GroupName/{groupName}")
//	@RolesAllowed("ROLE_USER")
    public PagedResponse<Group_IFI> findGroupByName(@PathVariable String groupName) {
    	logger.info("Find Group By Name ... ");
        return groupService.findGroupByName(groupName);
              
    }
    
    
    @PostMapping ("/GroupID/")
    	//	@RolesAllowed("ROLE_USER")
    public Group_IFI findGroupById(@RequestParam("group_id") String group_id) {
    	logger.info("Find Group By Id ... ");
        return groupService.getGroupById(group_id);     
    }
    
	// create new Group
	@PostMapping("/create")
	//@RolesAllowed("ROLE_USER")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody GroupRequest groupRequest) {
		logger.info("Create Group ... ");
		Group_IFI g=groupService.createGroupIFI(groupRequest);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{group_id}").buildAndExpand(g.getGroup_id())
				.toUri();
		return ResponseEntity.created(location).body(new ApiResponse(true, "Group Created Successfully"));
	}	
	

}
