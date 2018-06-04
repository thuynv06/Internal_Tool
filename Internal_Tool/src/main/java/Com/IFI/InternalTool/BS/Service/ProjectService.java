package Com.IFI.InternalTool.BS.Service;



import java.util.List;

import Com.IFI.InternalTool.DS.Model.Project;
import Com.IFI.InternalTool.DS.Model.ProjectManager;

public interface ProjectService {
	
	List<Project> getAllProject();
	
	public void saveProject(Project project);
//	
//	boolean deleteProject(long project_id);
//	
	Project getProjectById(Long project_id);
//	
//	List<ProjectManager> getProjectManagerByEmp(long employee_id,long project_id);
//	
//	List<Long> getProjectByEmp(long employee_id);
//	
//	List<Project> getProjectsOfGroup(String group_id);
	
	List<Project> findProjectLikeName(String projectName);
}
