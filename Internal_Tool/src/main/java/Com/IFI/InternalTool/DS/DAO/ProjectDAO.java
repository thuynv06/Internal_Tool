package Com.IFI.InternalTool.DS.DAO;



import java.util.List;

import Com.IFI.InternalTool.DS.Model.Project;
import Com.IFI.InternalTool.DS.Model.ProjectManager;

public interface ProjectDAO {
	
	List<Project> getAllProject(int page,int pageSize,String sortedColumn,Boolean desc);
	
	public void saveProject(Project project);
	
	boolean deleteProject(long project_id);
	
	Project getProjectById(long project_id);
	
	List<ProjectManager> getProjectManagerByEmp(long employee_id,long project_id);
	
	List<Long> getProjectByEmp(long employee_id);
	
	List<Project> getProjectsOfGroup(String group_id);
	
	List<Project> findProjectNameLike(String projectName);
	
}
