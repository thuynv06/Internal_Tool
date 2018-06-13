package Com.IFI.InternalTool.BS.Controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import Com.IFI.InternalTool.BS.Service.EmployeeService;
import Com.IFI.InternalTool.BS.Service.OvertimeService;
import Com.IFI.InternalTool.BS.Service.ProjectService;
import Com.IFI.InternalTool.DS.Model.Overtime;
import Com.IFI.InternalTool.DS.Model.Overtime_Approved;
import Com.IFI.InternalTool.DS.Model.Overtime_Log;
import Com.IFI.InternalTool.DS.Model.Overtime_Type;
import Com.IFI.InternalTool.DS.Model.Project;
import Com.IFI.InternalTool.DS.Model.ProjectManager;
import Com.IFI.InternalTool.DS.Model.SearchModel.OvertimeSearch;
import Com.IFI.InternalTool.Payloads.CountOvertimeResponse;
import Com.IFI.InternalTool.Payloads.CountOvertimeResponseManager;
import Com.IFI.InternalTool.Payloads.Payload;


@RestController
@RequestMapping("/api")
public class OvertimeRestController {
		@Autowired
		EmployeeService employeeService;
		@Autowired
		ProjectService projectService;
		@Autowired
		OvertimeService overtimeService;
	
		@GetMapping("/overtimes/{overtime_id}")
		public Overtime getOvertimeById(@PathVariable long overtime_id){ 
			return overtimeService.getOvertimeById(overtime_id);
		}
		
		// get all overtime (employee page)
		@GetMapping("/overtimes/employee")
		public @ResponseBody Payload getOvertimeByEmp(@RequestParam("page") int page,
				@RequestParam("pageSize") int pageSize, @RequestParam(required=false) String sortedColumn,
				@RequestParam(required = false) Boolean desc, @RequestParam("is_approved") Boolean is_approved,
				@RequestParam List<Integer> status) throws ParseException {
			Payload message = new Payload();
			Long employee_id=employeeService.getEmployeeIdAuthenticated();
			List<Overtime> list = overtimeService.getAllOvertimeByEmp(employee_id, page, pageSize, sortedColumn, desc, is_approved, status);
			int size = list.size();
			if(size!=0) {
				for(Overtime o : list) {
					List<String> approved_manager = new ArrayList<String>();
					String disapproved_manager = null;
					String project_name = null;
					String overtime_type_name = null;
					String next_approved_manager = null;
					List<Long> e = overtimeService.getApprovedIdByOvertimeId(o.getOvertime_id());
					for(Long i:e) {
						if(employeeService.getEmployeeById(i)!=null)
						approved_manager.add(employeeService.getEmployeeById(i).getFullname());
					}
					if(overtimeService.getDisApproveIdByOvertimeId(o.getOvertime_id())!=null) {
						disapproved_manager = employeeService.getEmployeeById(overtimeService.getDisApproveIdByOvertimeId(o.getOvertime_id())).getFullname();
					}
						project_name = projectService.getProjectById(o.getProject_id()).getName();
						overtime_type_name = overtimeService.getOvertimeTypeByID(o.getOvertime_type()).getName();
						if(overtimeService.getManagerIdByEmpProAndStatus(employee_id, o.getProject_id(), o.getStatus())!=null) {
							next_approved_manager = employeeService.getEmployeeById(overtimeService.getManagerIdByEmpProAndStatus(employee_id, o.getProject_id(), o.getStatus())).getFullname();
						}
					o.setProject_name(project_name);
					o.setOvertime_type_name(overtime_type_name);
					o.setApproved_manager(approved_manager);
					o.setDisapproved_manager(disapproved_manager);
					o.setNext_approve_manager(next_approved_manager);
				}
				message.setMessage("Get overtime by employee successfully");
				message.setCode("CODE OK!");
				message.setStatus("OK!");
				message.setData(list);
				Long count = overtimeService.countAllOvertimeByEmp(status, is_approved, employee_id);
				int pages = (int) (count / pageSize);
				if (count % pageSize > 0) {
					pages++;
				}
				message.setPages(pages);
				
			}else {
				message.setMessage("Overtime by employee not found!");
				message.setCode("CODE OK!");
				message.setStatus("OK!");
			}
			
			
			return message;
		}
		// get overtime number by status (employee page)
		@GetMapping("/overtimes/employee/count")
		public CountOvertimeResponse countOvertimeByStatus(){
			Long employee_id=employeeService.getEmployeeIdAuthenticated();
			List<Long> count =overtimeService.countOvertimeByStatus(employee_id);
			CountOvertimeResponse countOvertimeResponse=new CountOvertimeResponse();
			countOvertimeResponse.setLastest(count.get(0));
			countOvertimeResponse.setApproving(count.get(1));
			countOvertimeResponse.setApproved(count.get(2));
			countOvertimeResponse.setDisapproved(count.get(3));
			return countOvertimeResponse;

		}
		// get all overtime type
		@GetMapping("/overtimesType")
		public List<Overtime_Type> getOvertimeType(){
			return overtimeService.getAllOvertimeType();
		}
		//add overtime
		@PostMapping("/overtimes/add")
		public @ResponseBody Payload saveOvertime(@RequestBody Overtime overtime) {
			 Payload message = new Payload();
			 List<Long> check = projectService.getProjectByEmp(overtime.getEmployee_id());
			 if(check.size()>0) {
				 List<ProjectManager> pm = projectService.getProjectManagerByEmp(overtime.getEmployee_id(), overtime.getProject_id());
				 if(pm.size()>0) {
					 for(ProjectManager u:pm) {
						 Project project = projectService.getProjectById(u.getProject_id());
						 if(project.getEnd_date()!=null) {
							 Date start_date = project.getStart_date();
							 Date end_date = project.getEnd_date();
							 Date from_hour = overtime.getFrom_hour();
							 Date to_hour = overtime.getTo_hour();
							 if(from_hour.compareTo(start_date)>0 && to_hour.compareTo(end_date)<0 && from_hour.compareTo(to_hour)<0) {
								 Date date2 = new Date();
								 overtime.setEmployee_id(overtime.getEmployee_id());
								 overtime.setCreated_at(date2);
								 overtime.setUpdated_at(date2);
								 overtime.setStatus(1);
								 overtime.setIs_approved(null);
								 overtimeService.saveOvertime(overtime);
								 String next_approve_manager=null;
								 if(overtimeService.getManagerIdByEmpProAndStatus(overtime.getEmployee_id(), overtime.getProject_id(), overtime.getStatus())!=null) {
									 next_approve_manager = employeeService.getEmployeeById(overtimeService.getManagerIdByEmpProAndStatus(overtime.getEmployee_id(), overtime.getProject_id(), overtime.getStatus())).getFullname();
								 }
								 overtime.setNext_approve_manager(next_approve_manager);
								 overtime.setProject_name(projectService.getProjectById(overtime.getProject_id()).getName());
								 overtime.setOvertime_type_name(overtimeService.getOvertimeTypeByID(overtime.getOvertime_type()).getName());
								 Overtime_Approved overtime_Approved = new Overtime_Approved();
								 overtime_Approved.setOvertime_id(overtime.getOvertime_id());
								 overtime_Approved.setManager_id(u.getManager_id());
								 overtime_Approved.setPriority(u.getPriority());
								 overtimeService.saveOvertimeApproved(overtime_Approved);
								 List<Long> listManagerId = overtimeService.getManagerByOvertimeId(overtime.getOvertime_id());
								 Overtime_Log ol = new Overtime_Log();
								 for(Long a:listManagerId) {
									 ol.setOvertime_id(overtime.getOvertime_id());
									 ol.setNext_approve_id(a);
									 overtimeService.saveOvertimeLog(ol);
								 }
								 message.setMessage("Add overtime successfully");
								 message.setCode("CODE OK!");
								 message.setStatus("OK!");
								 message.setData(overtime);
							 }
							 else {
									message.setMessage("Wrong Date");
									message.setCode("Error!");
									message.setStatus("Error");
								}
						 }
						 if(project.getEnd_date()==null) {
							 Date start_date = project.getStart_date();
							 Date from_hour = overtime.getFrom_hour();
							 Date to_hour = overtime.getTo_hour();
							 if(from_hour.compareTo(start_date)>0 && to_hour.compareTo(to_hour)<0 && from_hour.compareTo(to_hour)<0)  {
								 Date date2 = new Date();
								 overtime.setEmployee_id(overtime.getEmployee_id());
								 overtime.setCreated_at(date2);
								 overtime.setUpdated_at(date2);
								 overtime.setStatus(1);
								 overtime.setIs_approved(null);
								 overtimeService.saveOvertime(overtime);
								 String next_approve_manager=null;
								 if(overtimeService.getManagerIdByEmpProAndStatus(overtime.getEmployee_id(), overtime.getProject_id(), overtime.getStatus())!=null) {
									 next_approve_manager = employeeService.getEmployeeById(overtimeService.getManagerIdByEmpProAndStatus(overtime.getEmployee_id(), overtime.getProject_id(), overtime.getStatus())).getFullname();
								 }
								 overtime.setNext_approve_manager(next_approve_manager);
								 overtime.setProject_name(projectService.getProjectById(overtime.getProject_id()).getName());
								 overtime.setOvertime_type_name(overtimeService.getOvertimeTypeByID(overtime.getOvertime_type()).getName());
								 Overtime_Approved overtime_Approved = new Overtime_Approved();
								 overtime_Approved.setOvertime_id(overtime.getOvertime_id());
								 overtime_Approved.setManager_id(u.getManager_id());
								 overtime_Approved.setPriority(u.getPriority());
								 overtimeService.saveOvertimeApproved(overtime_Approved);
								 List<Long> listManagerId = overtimeService.getManagerByOvertimeId(overtime.getOvertime_id());
								 Overtime_Log ol = new Overtime_Log();
								 for(Long a:listManagerId) {
									 ol.setOvertime_id(overtime.getOvertime_id());
									 ol.setNext_approve_id(a);
									 overtimeService.saveOvertimeLog(ol);
								 }
								 message.setMessage("Add overtime successfully");
								 message.setCode("CODE OK!");
								 message.setStatus("OK!");
								 message.setData(overtime);
							 } else {
									message.setMessage("Wrong Date");
									message.setCode("Error!");
									message.setStatus("Error");
								}
							}
						}
					}
					else {
						message.setMessage("You dont belong to this project");
						message.setCode("Error");
						message.setStatus("Error");
					}
				}
				else {
					message.setMessage("You are the highest level management or You dont belong to any project");
					message.setCode("Error");
					message.setStatus("Error");
				}

				return message;
			}
		
		// edit overtime
		@PutMapping("/overtimes/edit")
		public @ResponseBody Payload editOvertime(@RequestBody Overtime overtime) {
			Payload message = new Payload();
			Overtime o = overtimeService.getOvertimeById(overtime.getOvertime_id());
			if (o.getStatus() == 1) {
				List<ProjectManager> pm = projectService.getProjectManagerByEmp(overtime.getEmployee_id(), overtime.getProject_id());
				if(pm.size()>0) {
						for (ProjectManager u : pm) {
							Project p = projectService.getProjectById(u.getProject_id());// get project to check date
							if (p.getEnd_date() != null) {
								Date end_date = p.getEnd_date();
								Date start_date = p.getStart_date();
								Date from_hour = overtime.getFrom_hour();
								Date to_hour = overtime.getTo_hour();
								if (from_hour.compareTo(start_date) > 0 && to_hour.compareTo(end_date) < 0
										&& from_hour.compareTo(to_hour) < 0 ) {
												Date date2 = new java.util.Date();
												overtime.setCreated_at(o.getCreated_at());
												overtime.setStatus(o.getStatus());
												overtime.setUpdated_at(date2);
												overtime.setCreated_at(o.getCreated_at());
												overtime.setEmployee_id(o.getEmployee_id());
												overtimeService.saveOvertime(overtime);
												//get more info
												String next_approve_manager=null;
												if(overtimeService.getManagerIdByEmpProAndStatus(overtime.getEmployee_id(), overtime.getProject_id(), overtime.getStatus())!=null) {
													next_approve_manager=employeeService.getEmployeeById(overtimeService.getManagerIdByEmpProAndStatus(overtime.getEmployee_id(), overtime.getProject_id(), overtime.getStatus())).getFullname();
												}
												overtime.setNext_approve_manager(next_approve_manager);//get next_approve manager
												overtime.setProject_name(projectService.getProjectById(overtime.getProject_id()).getName());//get project name
												overtime.setOvertime_type_name(overtimeService.getOvertimeTypeByID(overtime.getOvertime_type()).getName());
												message.setMessage("Edit project successfully");
												message.setCode("CODE OK!");
												message.setStatus("OK!");
												message.setData(overtime);
											}
										else {
											message.setStatus("Error!");
											message.setMessage("Wrong date, can not edit");
										}
								}
							if (p.getEnd_date() == null) {
								Date end_date = p.getEnd_date();
								Date start_date = p.getStart_date();
								Date from_hour = overtime.getFrom_hour();
								Date to_hour = overtime.getTo_hour();
								if (from_hour.compareTo(start_date) > 0 && to_hour.compareTo(end_date) < 0
										&& from_hour.compareTo(to_hour) < 0 ) {
												Date date2 = new java.util.Date();
												overtime.setCreated_at(o.getCreated_at());
												overtime.setStatus(o.getStatus());
												overtime.setUpdated_at(date2);
												overtime.setCreated_at(o.getCreated_at());
												overtime.setEmployee_id(o.getEmployee_id());
												overtimeService.saveOvertime(overtime);
												//get more info
												String next_approve_manager=null;
												if(overtimeService.getManagerIdByEmpProAndStatus(overtime.getEmployee_id(), overtime.getProject_id(), overtime.getStatus())!=null) {
													next_approve_manager=employeeService.getEmployeeById(overtimeService.getManagerIdByEmpProAndStatus(overtime.getEmployee_id(), overtime.getProject_id(), overtime.getStatus())).getFullname();
												}
												overtime.setNext_approve_manager(next_approve_manager);//get next_approve manager
												overtime.setProject_name(projectService.getProjectById(overtime.getProject_id()).getName());//get project name
												overtime.setOvertime_type_name(overtimeService.getOvertimeTypeByID(overtime.getOvertime_type()).getName());
												message.setMessage("Edit project successfully");
												message.setCode("CODE OK!");
												message.setStatus("OK!");
												message.setData(overtime);
											}
										else {
											message.setStatus("Error!");
											message.setMessage("Wrong date, can not edit");
										}
								}
							
						}
				}
						else {
							message.setMessage("You dont belong to this project");
							message.setCode("Error!");
							message.setStatus("Error");
						}
						
			}

				 else {
					message.setStatus("Error!");
					message.setCode("Error!");
					message.setMessage("Overtime is processing, You can not update");
				 }
			return message;
		}
		
		// delete overtime by id
		@DeleteMapping("/overtimes/delete/{overtime_id}")
		public @ResponseBody Payload deleteOvertime(@PathVariable long overtime_id) {
			Payload message = new Payload();
			Overtime overtime = overtimeService.getOvertimeById(overtime_id);
			if (overtime != null) {
				if (overtime.getStatus() == 1) {
					if (overtimeService.deleteOvertime(overtime_id)) {
						message.setMessage("Delete overtime successfully");
						message.setCode("CODE OK!");
						message.setStatus("OK!");
						message.setData("");
					}
				} else {
					message.setCode("CODE OK!");
					message.setStatus("OK!");
					message.setMessage("Overtime is processing, You can not delete");
				}
			} else {
				message.setMessage("Can not delete, overtime not found");
				message.setCode("CODE OK!");
				message.setStatus("OK!");
			}
			return message;
		}
		
		// get overtime ( manager/leader page)
		@PreAuthorize("hasRole('LEADER_A') OR hasRole('LEADER_B') OR hasRole('LEADER_C')")
		@GetMapping("/overtimes/manager")
		public @ResponseBody Payload getEmployeeOvertimeByManager(@RequestParam("page") int page,
				@RequestParam("pageSize") int pageSize, @RequestParam("sortedColumn") String sortedColumn,
				@RequestParam("desc") Boolean desc) {
			Payload message=new Payload();
			Long manager_id=employeeService.getEmployeeIdAuthenticated();
			List<Overtime> listOvertime = overtimeService.getAllOvertimeByEmp2(manager_id, page, pageSize, sortedColumn, desc);
			if( listOvertime.size() > 0) {
					for(Overtime overtime:listOvertime) {
						List<String> approved_manager=new ArrayList<String>();
						String disapproved_manager=null;
						String project_name=null;
						String employee_name=null;
						String overtime_type_name=null;
						String next_approve_manager=null;
						List<Long> e=overtimeService.getApprovedIdByOvertimeId(overtime.getOvertime_id());
						for(Long i:e) {
							if(employeeService.getEmployeeById(i)!=null)
							approved_manager.add(employeeService.getEmployeeById(i).getFullname());
						}
						//get employee name
							employee_name=employeeService.getEmployeeById(overtime.getEmployee_id()).getFullname();
						//get project name
							project_name=projectService.getProjectById(overtime.getProject_id()).getName();
						//get overtime type name
							overtime_type_name=overtimeService.getOvertimeTypeByID(overtime.getOvertime_type()).getName();
							overtime.setProject_name(project_name);
							overtime.setEmployee_name(employee_name);
							overtime.setOvertime_type_name(overtime_type_name);
							overtime.setApproved_manager(approved_manager);
							overtime.setDisapproved_manager(disapproved_manager);
							overtime.setNext_approve_manager(next_approve_manager);
					}
					Long count = overtimeService.countAllOvertimeByEmp2(manager_id);
					int pages = (int) (count / pageSize);
					if (count % pageSize > 0) {
						pages++;
					}
					message.setPages(pages);
					message.setMessage("Get overtime successfully");
					message.setCode("CODE OK!");
					message.setStatus("OK!");
					message.setData(listOvertime);
				}
			else {
				message.setCode("CODE OK!");
				message.setStatus("OK!");
				message.setMessage("No result!");
			}
			return message;

		}
		
		//get number of overtime manager need approve
		@PreAuthorize("hasRole('LEADER_A') OR hasRole('LEADER_B') OR hasRole('LEADER_C')")
		@GetMapping("/overtimes/manager/count")
		public CountOvertimeResponseManager countOvertimeByStatus2(){
			Long manager_id=employeeService.getEmployeeIdAuthenticated();
			List<Long> count=overtimeService.countOvertimeByStatusManager(manager_id);
			CountOvertimeResponseManager countOvertimeResponseManager=new CountOvertimeResponseManager();
			countOvertimeResponseManager.setApproved(count.get(1));
			countOvertimeResponseManager.setNeed_approve(count.get(0));
			countOvertimeResponseManager.setDisapproved(count.get(2));
			return countOvertimeResponseManager;
		}
		//get all approved overtime by manager
			@PreAuthorize("hasRole('LEADER_A') OR hasRole('LEADER_B') OR hasRole('LEADER_C')")
				@GetMapping("/overtimes/getallapproved")
				public @ResponseBody Payload getApprovedOvertimeLogByMng(@RequestParam("page") int page,
						  @RequestParam("pageSize") int pageSize){
					Long manager_id=employeeService.getEmployeeIdAuthenticated();
					List<Overtime> result=new ArrayList<Overtime>();
					Payload message = new Payload();
					List<Long> list=overtimeService.getApprovedIdOvertimeLogByManager(manager_id, page, pageSize);
					for(Long i:list) {
					result.add(overtimeService.getOvertimeById(i));
					}
					for(Overtime o:result) {
					List<String> approved_manager=new ArrayList<String>();
					String disapproved_manager=null;
					String project_name=null;
					String employee_name=null;
					String overtime_type_name=null;
					String next_approve_manager=null;
					
					List<Long> e=overtimeService.getApprovedIdByOvertimeId(o.getOvertime_id());
					for(Long i:e) {
					if(employeeService.getEmployeeById(i)!=null)
					approved_manager.add(employeeService.getEmployeeById(i).getFullname());
						}
					//get employee name
					employee_name=employeeService.getEmployeeById(o.getEmployee_id()).getFullname();
					//get project name
					project_name=projectService.getProjectById(o.getProject_id()).getName();
					//get overtime type name
					overtime_type_name=overtimeService.getOvertimeTypeByID(o.getOvertime_type()).getName();
					
					o.setProject_name(project_name);
					o.setEmployee_name(employee_name);
					o.setOvertime_type_name(overtime_type_name);
					o.setApproved_manager(approved_manager);
					o.setDisapproved_manager(disapproved_manager);
					o.setNext_approve_manager(next_approve_manager);
					}
					Long count = overtimeService.countApprovedOvertimeByManager(manager_id);
					int pages = (int) (count / pageSize);
					if (count % pageSize > 0) {
						pages++;
					}
					message.setPages(pages);
					message.setCode("OK");
					message.setStatus("OK");
					message.setData(result);
					message.setMessage("Get Overtime successfully! ");
				
					return message;
				}
				//get all overtime disapproved by manager
				@PreAuthorize("hasRole('LEADER_A') OR hasRole('LEADER_B') OR hasRole('LEADER_C')")
				@GetMapping("/overtimes/getalldisapproved")
				public @ResponseBody Payload getDisApprovedOvertimeLogByManager(@RequestParam("page") int page,
						  											 @RequestParam("pageSize") int pageSize){
					Long manager_id=employeeService.getEmployeeIdAuthenticated();
					List<Overtime> result=new ArrayList<Overtime>();
					Payload message = new Payload();
					List<Long> list=overtimeService.getDisapproveIdOvertimeLogByManager(manager_id, page, pageSize);
					for(Long i:list) {
						result.add(overtimeService.getOvertimeById(i));
					}
					for(Overtime o:result) {
						List<String> approved_manager=new ArrayList<String>();
						String disapproved_manager=null;
						String project_name=null;
						String employee_name=null;
						String overtime_type_name=null;
						String next_approve_manager=null;
						List<Long> e=overtimeService.getApprovedIdByOvertimeId(o.getOvertime_id());
						for(Long i:e) {
							if(employeeService.getEmployeeById(i)!=null)
							approved_manager.add(employeeService.getEmployeeById(i).getFullname());
						}
						//get employee name
							employee_name=employeeService.getEmployeeById(o.getEmployee_id()).getFullname();
						//get project name
							project_name=projectService.getProjectById(o.getProject_id()).getName();
						//get overtime type name
							overtime_type_name=overtimeService.getOvertimeTypeByID(o.getOvertime_type()).getName();
						o.setProject_name(project_name);
						o.setEmployee_name(employee_name);
						o.setOvertime_type_name(overtime_type_name);
						o.setApproved_manager(approved_manager);
						o.setDisapproved_manager(disapproved_manager);
						o.setNext_approve_manager(next_approve_manager);
					}
					Long count = overtimeService.countDisapprovedOvertimeByManager(manager_id);
					int pages = (int) (count / pageSize);
					if (count % pageSize > 0) {
						pages++;
					}
					message.setPages(pages);
					message.setCode("OK");
					message.setStatus("OK");
					message.setData(result);
					message.setMessage("Get Overtime successfully! ");
				
					return message;
				}
				
				//approve request
				@PreAuthorize("hasRole('LEADER_A') OR hasRole('LEADER_B') OR hasRole('LEADER_C')")
				@GetMapping("/overtimes/approve")
				public @ResponseBody Payload approveEmployeeRequest(@RequestParam("overtime_id") long overtime_id) {
					Payload message = new Payload();
					Long manager_id=employeeService.getEmployeeIdAuthenticated();
					Overtime overtime = overtimeService.getOvertimeById(overtime_id);
					List<Long> test = overtimeService.getManagerByOvertimeId(overtime_id);
					for (Long a : test) {
						if (manager_id == a) {
							Overtime_Log overtime_Log = overtimeService.getOverTimeLogByOvertimeIdAndNextApprovedId(overtime_id, manager_id);
							int max = overtimeService.getMaxPriority(overtime_id);
							int my_prio = overtimeService.getPriority(manager_id, overtime_id);
							if (my_prio < max) {
								overtime.setStatus(my_prio + 1);
								overtime.setIs_approved(false);
								overtime_Log.setApproved_id(manager_id);
								overtimeService.saveOvertimeLog(overtime_Log);
								overtimeService.saveOvertime(overtime);
							} else if (my_prio == max) {
								overtime.setStatus(max + 1);
								overtime.setIs_approved(true);
								overtimeService.saveOvertime(overtime);
								overtime_Log.setApproved_id(manager_id);
								overtimeService.saveOvertimeLog(overtime_Log);
							}
							message.setCode("OK");
							message.setStatus("OK");
							message.setData(overtime);
							message.setMessage("Approve Overtime successfully! ");
							break;
						} else {
							message.setCode("Error");
							message.setStatus("Error");
							message.setMessage("You don't have permission to approve this overtime!");
						}
					}

					return message;
				}
				
				//disapprove request
				@PreAuthorize("hasRole('LEADER_A') OR hasRole('LEADER_B') OR hasRole('LEADER_C')")
				@GetMapping("/overtimes/disapprove")
				public @ResponseBody Payload disapproveEmployeeRequest(@RequestParam("overtime_id") long overtime_id) {
					Payload message = new Payload();
					Long manager_id=employeeService.getEmployeeIdAuthenticated();
					Overtime overtime = overtimeService.getOvertimeById(overtime_id);
					List<Long> test = overtimeService.getManagerByOvertimeId(overtime_id);
					for (Long a : test) {
						if (manager_id == a) {
							Overtime_Log overtime_Log = overtimeService.getOverTimeLogByOvertimeIdAndNextApprovedId(overtime_id, manager_id);
							overtime.setStatus(-1);
							overtime.setIs_approved(false);
							overtimeService.saveOvertime(overtime);
							overtime_Log.setDisapproved_id(manager_id);
							overtimeService.saveOvertimeLog(overtime_Log);
							message.setCode("OK");
							message.setStatus("OK");
							message.setData(overtime);
							message.setMessage("Disapprove Overtime successfully! ");
							break;
						} else {
							message.setCode("Error");
							message.setStatus("Error");
							message.setMessage("You don't have permission to disapprove this overtime!");
						}
					}
					return message;

				}
				//search page manager/leader
				@PreAuthorize("hasRole('LEADER_A') OR hasRole('LEADER_B') OR hasRole('LEADER_C')")
				@PostMapping("/overtimes/manager/search")
				public @ResponseBody Payload searchOvertimeManager(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize,
						@RequestParam(required = false) String sortedColumn, @RequestParam(required = false) Boolean desc,
						@RequestParam Boolean is_approved,
						@RequestParam List<Integer> status,
						@RequestBody OvertimeSearch overtimeSearch) {
					Long manager_id=employeeService.getEmployeeIdAuthenticated();
					Payload message = new Payload();
					List<Overtime> list = overtimeService.searchOvertimeManager(manager_id, page, pageSize, sortedColumn, desc, is_approved, status, overtimeSearch);
					System.out.println(list);
					if (list.size() > 0) {
						for(Overtime o:list) {
							List<String> approved_manager=new ArrayList<String>();
							String disapproved_manager=null;
							String project_name=null;
							String employee_name=null;
							String overtime_type_name=null;
							String next_approve_manager=null;
							List<Long> e=overtimeService.getApprovedIdByOvertimeId(o.getOvertime_id());
							System.out.println(list);
							for(Long i:e) {
								if(employeeService.getEmployeeById(i)!=null)
								approved_manager.add(employeeService.getEmployeeById(i).getFullname());
							}
							//get employee name
								employee_name=employeeService.getEmployeeById(o.getEmployee_id()).getFullname();
							//get project name
								project_name=projectService.getProjectById(o.getProject_id()).getName();
							//get overtime type name
								overtime_type_name=overtimeService.getOvertimeTypeByID(o.getOvertime_type()).getName();
							o.setProject_name(project_name);
							o.setEmployee_name(employee_name);
							o.setOvertime_type_name(overtime_type_name);
							o.setApproved_manager(approved_manager);
							o.setDisapproved_manager(disapproved_manager);
							o.setNext_approve_manager(next_approve_manager);
						}
						Long count = overtimeService.CountSearchOvertimeManager(manager_id, is_approved, status, overtimeSearch);
						int pages = (int) (count / pageSize);
						if (count % pageSize > 0) {
							pages++;
						}
						message.setPages(pages);
						message.setMessage("Search overtime successfully");
						message.setCode("CODE OK!");
						message.setStatus("OK!");
						message.setData(list);
					}

					else {
						message.setCode("CODE OK!");
						message.setStatus("OK!");
						message.setMessage("No result!");
					}
					return message;
				}
				
				//search page employee
				@PostMapping("/overtimes/employee/search")
				public @ResponseBody Payload searchOvertimeEmployee(@RequestParam("page") int page,
						@RequestParam("pageSize") int pageSize, @RequestParam(required = false) String sortedColumn,
						@RequestParam(required = false) Boolean desc,
						@RequestParam Boolean is_approved,
						@RequestParam List<Integer> status,
						@RequestBody OvertimeSearch overtimeSearch) {
					Long employee_id=employeeService.getEmployeeIdAuthenticated();
					Payload message = new Payload();
					List<Overtime> list = overtimeService.searchOvertimeEmployee(employee_id, page, pageSize, sortedColumn, desc, is_approved, status, overtimeSearch);
					System.out.println(list);
					if (list.size() > 0) {
						for(Overtime o:list) {
							List<String> approved_manager=new ArrayList<String>();
							String disapproved_manager=null;
							String project_name=null;
							String overtime_type_name=null;
							String next_approve_manager=null;
							List<Long> e=overtimeService.getApprovedIdByOvertimeId(o.getOvertime_id());
							for(Long i:e) {
								if(employeeService.getEmployeeById(i)!=null)
								approved_manager.add(employeeService.getEmployeeById(i).getFullname());
								
							}
							//get disapprove manager name
							if(overtimeService.getDisApproveIdByOvertimeId(o.getOvertime_id())!=null) {
								disapproved_manager=employeeService.getEmployeeById(overtimeService.getDisApproveIdByOvertimeId(o.getOvertime_id())).getFullname();
							}
							//get project name
							if(projectService.getProjectById(o.getProject_id())!=null) {
								project_name=projectService.getProjectById(o.getProject_id()).getName();
							}
							//get overtime type name
							if(overtimeService.getOvertimeTypeByID(o.getOvertime_type())!=null) {
								overtime_type_name=overtimeService.getOvertimeTypeByID(o.getOvertime_type()).getName();
							}
							//get next approve manager name
							if(overtimeService.getManagerIdByEmpProAndStatus(employee_id, o.getProject_id(), o.getStatus())!=null) {
								next_approve_manager=employeeService.getEmployeeById(overtimeService.getManagerIdByEmpProAndStatus(employee_id, o.getProject_id(), o.getStatus())).getFullname();
							}
							o.setProject_name(project_name);
							o.setOvertime_type_name(overtime_type_name);
							o.setApproved_manager(approved_manager);
							o.setDisapproved_manager(disapproved_manager);
							o.setNext_approve_manager(next_approve_manager);
						}
						Long count = overtimeService.CountSearchManagerEmployee(employee_id, is_approved, status, overtimeSearch);
						int pages = (int) (count / pageSize);
						if (count % pageSize > 0) {
							pages++;
						}
						message.setPages(pages);
						message.setMessage("Search overtime successfully");
						message.setCode("CODE OK!");
						message.setStatus("OK!");
						message.setData(list);
					}

					else {
						message.setCode("CODE OK!");
						message.setStatus("OK!");
						message.setMessage("No result!");
					}
					return message;
				}
				
		
		

}

