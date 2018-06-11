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
	public List<Project> getAllProject(int page, int pageSize) {
		return projectDAO.getAllProject(page, pageSize);
	}

	@Override
	public void saveProject(Project project) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(project.getStart_date());
		project.setMonth(calendar.MONTH);
		project.setYear(calendar.YEAR);
		projectDAO.saveProject(project);
	}

	@Override
	public boolean deleteProject(long project_id) {
		return projectDAO.deleteProject(project_id);
	}

	@Override
	public Project getProjectById(Long project_id) {
		return projectDAO.getProjectById(project_id);
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
	public List<Project> findProjectNameLike(String projectName, int page, int pageSize) {
		return projectDAO.findProjectNameLike(projectName, page, pageSize);
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
	public boolean updateProject(Project project) {
		return projectDAO.updateProject(project);
	}

	@Override
	public List<Project> getListProjectOutOfDate(int page, int pageSize) {
		return projectDAO.getListProjectOutOfDate(page, pageSize);
	}

	@Override
	public List<Project> getProjectByMonthYear(int month, int year, int page, int pageSize) {
		if (year <= 0) {
			year = Calendar.getInstance().get(Calendar.YEAR);
		}
		return projectDAO.getProjectByMonthYear(month, year, page, pageSize);
	}

	@Override
	public List<Project> getProjectAllocatedIn(long employee_id, int page, int pageSize) {
		return projectDAO.getProjectAllocatedIn(employee_id, page, pageSize);
	}

	@Override
	public List<Project> getProjectAllocateTo(long employee_id, int page, int pageSize) {
		return projectDAO.getProjectAllocateTo(employee_id, page, pageSize);
	}

	@Override
	public Boolean addMemberToProject(long currentEmployeeId, ProjectMembers projectMember) {
		Employee currentEmployee = employeeServiceImpl.getEmployeeById(currentEmployeeId);
		//kiem tra id nhan vien them vao co thuoc danh sach subEmployee khong
		if (employeeServiceImpl.getListSubEmployee(projectMember.getEmployee_id()).contains(currentEmployee)) {
			projectMember.setPriority(employeeServiceImpl.getEmployeeById(projectMember.getEmployee_id()).getRole_id());
			return projectMemberDAO.addMemberToProject(projectMember);
		}else {
			return false;
		}
		
	}

	@Override
	public Boolean removeMemberOfProject(long currentEmployeeId, long projectMemberId) {
		Employee currentEmployee = employeeServiceImpl.getEmployeeById(currentEmployeeId);
		//kiem tra id nhan vien them vao co thuoc danh sach subEmployee khong
		// va nhan vien do co allocation chua
		if (employeeServiceImpl.getListSubEmployee(projectMemberId).contains(currentEmployee)
				&& allocationServiceImpl.findAllocationByEmployeeID(projectMemberId, 1, 1) == null) {				
			return projectMemberDAO.removeMemberOfProject(projectMemberId);
		}else {
			return false;
		}
	}

}