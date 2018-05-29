package Com.IFI.InternalTool.DS.DAO;

import java.util.List;

import org.springframework.stereotype.Repository;

import Com.IFI.InternalTool.DS.Model.Project;
import Com.IFI.InternalTool.DS.Model.Project_manager;

@Repository
public interface ProjectDAO {
	List<Project> getAllProject();
	boolean saveProject(Project project);
	boolean deleteProject(long project_id);
	Project getProjectById(long project_id);
	List<Project_manager> getProjectManagerByEmp(long employee_id,long project_id);
	List<Long> getProjectByEmp(long employee_id);
}
