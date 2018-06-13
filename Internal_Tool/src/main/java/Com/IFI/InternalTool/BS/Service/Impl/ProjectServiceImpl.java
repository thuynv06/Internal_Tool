package Com.IFI.InternalTool.BS.Service.Impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Com.IFI.InternalTool.BS.Service.ProjectService;
import Com.IFI.InternalTool.DS.DAO.ProjectDAO;
import Com.IFI.InternalTool.DS.DAO.Impl.ProjectDAOImpl;
import Com.IFI.InternalTool.DS.DAO.Impl.ProjectMembersDAOImpl;
import Com.IFI.InternalTool.DS.Model.Allocation;
import Com.IFI.InternalTool.DS.Model.Employee;
//import Com.IFI.InternalTool.DS.DAO.Impl.ProjectDAOImpl;
import Com.IFI.InternalTool.DS.Model.Project;
import Com.IFI.InternalTool.DS.Model.ProjectManager;
import Com.IFI.InternalTool.DS.Model.ProjectMembers;
import Com.IFI.InternalTool.Utils.Business;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	ProjectDAOImpl projectDAO;
	@Autowired
	AllocationServiceImpl allocationServiceImpl;
	@Autowired
	EmployeeServiceImpl employeeServiceImpl;
	@Autowired
	ProjectMembersDAOImpl projectMemberDAO;

	@Override
	public List<Project> getAllProjects(int page, int pageSize) {
		return projectDAO.getAllProjects(page, pageSize);
	}

	@Override
	public List<Project> getAllProject() {
		return projectDAO.getAllProject();
	}

	@Override
	public void saveProject(long managerId, Project project) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(project.getStart_date());
		project.setMonth(calendar.MONTH);
		project.setYear(calendar.YEAR);
		projectDAO.saveProject(project);
		//tu dong them manager vao bang
		ProjectMembers projectMembers = new ProjectMembers();
		projectMembers.setEmployee_id(managerId);
		//da co ham get priority ben employee 
		//can sua lai
		projectMembers.setPriority(employeeServiceImpl.getEmployeeById(managerId).getRole_id());
		projectMemberDAO.addMemberToProject(projectMembers);
	}

	@Override
	public boolean deleteProject(long project_id) {
		return projectDAO.deleteProject(project_id);
	}

	@Override
	public Project getProjectById(Long project_id) {
		Project project = projectDAO.getProjectById(project_id);
		project.setManager_Name(employeeServiceImpl.getEmployeeById(project.getManger_id()).getFullname());
		return project;
		
	}

	@Override
	public List<ProjectManager> getProjectManagerByEmp(long employee_id, long project_id) {
		return projectDAO.getProjectManagerByEmp(employee_id, project_id);
	}

	@Override
	public List<Long> getProjectByEmp(long employee_id) {
		return projectDAO.getProjectByEmp(employee_id);
	}

	@Override
	public List<Project> getProjectsOfGroup(String group_id, int page, int pageSize) {
		return projectDAO.getProjectsOfGroup(group_id, page, pageSize);
	}
	@Override
	public int NumerRecordsProjectsOfGroup(String group_id, int pageSize) {
		return Business.getTotalPage(projectDAO.NumerRecordsProjectsOfGroup(group_id), pageSize);
	}

	@Override
	public List<Project> findProjectNameLike(String projectName, int page, int pageSize) {
		return projectDAO.findProjectNameLike(projectName, page, pageSize);
	}
	
	@Override
	public int NumerRecordsProjectNameLike(String projectName, int pageSize) {
		return Business.getTotalPage(projectDAO.NumerRecordsProjectNameLike(projectName), pageSize);
	}

	@Override
	public Employee getBigestManager(long project_id) {
		return employeeServiceImpl.getEmployeeById(projectDAO.getBigestManagerId(project_id));
	}

	@Override
	public List<Employee> getListEmployee(long project_id, int page, int pageSize) {
		List<Employee> listEmployee = new ArrayList<Employee>();
		for (Long employee_id : projectDAO.getListEmployeeId(project_id, page, pageSize)) {
			listEmployee.add(employeeServiceImpl.getEmployeeById(employee_id));
		}
		return listEmployee;
	}
	
	@Override
	public int NumerRecordsListEmployee(long project_id, int pageSize) {
		return Business.getTotalPage(projectDAO.NumerRecordsListEmployeeId(project_id), pageSize);
	}

	@Override
	public boolean updateProject(Project project) {
		return projectDAO.updateProject(project);
	}

	@Override
	public List<Project> getListProjectOutOfDate(int page, int pageSize) {
		return projectDAO.getListProjectOutOfDate(page, pageSize);
	}
	@Override
	public int NumerRecordsListProjectOutOfDate(int pageSize) {
		return Business.getTotalPage(projectDAO.NumerRecordsListProjectOutOfDate(), pageSize);
	}


	@Override
	public List<Project> getProjectByMonthYear(int month, int year, int page, int pageSize) {
		if (year <= 0) {
			year = Calendar.getInstance().get(Calendar.YEAR);
		}
		return projectDAO.getProjectByMonthYear(month, year, page, pageSize);
	}
	@Override
	public int NumerRecordsProjectByMonthYear(int month, int year, int pageSize) {
		return Business.getTotalPage(projectDAO.NumerRecordsProjectByMonthYear(month, year), pageSize);
	}

	@Override
	public List<Project> getProjectAllocatedIn(long employee_id, int page, int pageSize) {
		return projectDAO.getProjectAllocatedIn(employee_id, page, pageSize);
	}

	@Override
	public int NumerRecordsProjectAllocatedIn(long employee_id, int pageSize) {	
		return Business.getTotalPage(projectDAO.NumerRecordsProjectAllocatedIn(employee_id), pageSize);
	}

	@Override
	public List<Project> getProjectAllocateTo(long employee_id, int page, int pageSize) {
		return projectDAO.getProjectAllocateTo(employee_id, page, pageSize);
	}
	@Override
	public int NumerRecordsProjectAllocateTo(long employee_id, int pageSize) {
		return Business.getTotalPage(projectDAO.NumerRecordsProjectAllocateTo(employee_id), pageSize);
	}

	@Override
	public Boolean addMemberToProject(long currentEmployeeId, ProjectMembers projectMember) {
		Employee subEmployee = employeeServiceImpl.getEmployeeById(projectMember.getEmployee_id());
		// kiem tra id nhan vien them vao co thuoc danh sach subEmployee khong
		if (employeeServiceImpl.getListSubEmployee(currentEmployeeId).contains(subEmployee)) {
			//da co service lay role id
			//can sua lai
			projectMember.setPriority(employeeServiceImpl.getEmployeeById(projectMember.getEmployee_id()).getRole_id());
			projectMember.setLeader_id(currentEmployeeId);
			return projectMemberDAO.addMemberToProject(projectMember);
		} else {
			return false;
		}

	}

	@Override
	public Boolean removeMemberOfProject(long currentEmployeeId, ProjectMembers projectMember) {
		// kiem tra nhan vien xoa co phai duoc them boi nhan vien hien tai khong
		if (projectMember.getLeader_id() == currentEmployeeId) {
			return projectMemberDAO.removeMemberOfProject(projectMember.getProject_members_id());
		} else {
			return false;
		}
	}

}