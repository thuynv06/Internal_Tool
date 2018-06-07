package Com.IFI.InternalTool.BS.Service;

import java.util.List;

import Com.IFI.InternalTool.DS.Model.Employee;
import Com.IFI.InternalTool.DS.Model.Project;
import Com.IFI.InternalTool.DS.Model.ProjectManager;

public interface ProjectService {
	// lay tat ca project
	List<Project> getAllProject(int page, int pageSize);

	// luu project
	public void saveProject(Project project);

	// xoa project
	boolean deleteProject(long project_id);

	// lay theo id
	Project getProjectById(Long project_id);

	//
	List<ProjectManager> getProjectManagerByEmp(long employee_id, long project_id);

	List<Long> getProjectByEmp(long employee_id);

	// lay danh sach project theo group
	List<Project> getProjectsOfGroup(String group_id, int page, int pageSize);

	// tim kiem theo ten
	List<Project> findProjectNameLike(String projectName, int page, int pageSize);

	// tim kiem id manager cao nhat
	Employee getBigestManager(long project_id);

	// tim kiem danh sach nhan vien trong project
	List<Employee> getListEmployee(long project_id, int page, int pageSize);

	// update project
	boolean updateProject(Project project);

	// tim kiem cac project da off
	List<Project> getListProjectOutOfDate(int page, int pageSize);

	// tim kiem theo nam thang
	List<Project> getProjectByMonthYear(int month, int year, int page, int pageSize);
}