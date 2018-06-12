package Com.IFI.InternalTool.BS.Service;

import java.util.List;

import Com.IFI.InternalTool.DS.Model.Employee;
import Com.IFI.InternalTool.DS.Model.Project;
import Com.IFI.InternalTool.DS.Model.ProjectManager;
import Com.IFI.InternalTool.DS.Model.ProjectMembers;

public interface ProjectService {
	// lay tat ca project
	List<Project> getAllProjects(int page, int pageSize);

	List<Project> getAllProject();

	// luu project
	public void saveProject(Project project);

	// xoa project
	boolean deleteProject(long project_id);

	// lay theo id
	Project getProjectById(Long project_id);

	// lay danh sach project theo group
	List<Project> getProjectsOfGroup(String group_id, int page, int pageSize);

	// tim kiem theo ten
	List<Project> findProjectNameLike(String projectName, int page, int pageSize);

	// tim kiem id manager cao nhat
	Employee getBigestManager(long project_id);

	// tim kiem danh sach nhan vien trong project
	List<Employee> getListEmployee(long project_id, int page, int pageSize);

	// update project
	boolean updateProject(final Project project);

	Boolean addMemberToProject(final long currentEmployeeId, final ProjectMembers projectMember);

	Boolean removeMemberOfProject(final long currentEmployeeId, final long projectMemberId);

	// tim kiem cac project da off
	List<Project> getListProjectOutOfDate(int page, int pageSize);

	// tim kiem theo nam thang
	List<Project> getProjectByMonthYear(int month, int year, int page, int pageSize);

	// lay project nhan vien duoc phan cong vao
	List<Project> getProjectAllocatedIn(long employee_id, int page, int pageSize);

	Long NumerRecordsProjectAllocatedIn(long employee_id);

	// lay cac project ma nhan vien do quan ly
	List<Project> getProjectAllocateTo(long employee_id, int page, int pageSize);

	List<ProjectManager> getProjectManagerByEmp(long employee_id, long project_id);

	List<Long> getProjectByEmp(long employee_id);
}