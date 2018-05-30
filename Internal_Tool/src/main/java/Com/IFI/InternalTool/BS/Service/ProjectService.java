package Com.IFI.InternalTool.BS.Service;

import java.util.List;

import Com.IFI.InternalTool.DS.Model.Project;
import Com.IFI.InternalTool.DS.Model.ProjectManager;

public interface ProjectService {
	List<Project> getAllProject();
	boolean saveProject(Project project);
	boolean deleteProject(long project_id);
	Project getProjectById(long project_id);
	List<ProjectManager> getProjectManagerByEmp(long employee_id,long project_id);
	List<Long> getProjectByEmp(long employee_id);

}
