package Com.IFI.InternalTool.DS.DAO;

import java.util.List;

import Com.IFI.InternalTool.DS.Model.Project;
import Com.IFI.InternalTool.DS.Model.ProjectManager;

public interface ProjectDAO {

	// lay tat ca project
	List<Project> getAllProject(int page, int pageSize);

	// tao hoac chinh sua
	boolean saveProject(Project project);

	// xoa
	boolean deleteProject(long project_id);

	// tim kiem theo id
	Project getProjectById(long project_id);

	// tim kiem manager
	List<ProjectManager> getProjectManagerByEmp(long employee_id, long project_id);

	// tim kiem theo 1 employee
	List<Long> getProjectByEmp(long employee_id);

	// tim kiem theo 1 group
	List<Project> getProjectsOfGroup(String group_id, int page, int pageSize);

	// tim kiem theo ten
	List<Project> findProjectNameLike(String projectName, int page, int pageSize);

	// tim kiem id manager cao nhat
	Long getBigestManagerId(long project_id);

	// tim kiem danh sach id nhan vien trong project
	List<Long> getListEmployeeId(long project_id, int page, int pageSize);

	// update project
	boolean updateProject(Project project);

	// tim kiem cac project da off
	List<Project> getListProjectOutOfDate(int page, int pageSize);

	// tim kiem theo nam thang
	List<Project> getProjectByMonthYear(int month, int year, int page, int pageSize);

	// tim kiem project cua nhan vien tham gia vao
	List<Project> getProjectAllocatedIn(long employee_id, int page, int pageSize);

	// lay cac project ma nhan vien do quan ly
	List<Project> getProjectAllocateTo(long employee_id, int page, int pageSize);
}