package Com.IFI.InternalTool.BS.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Com.IFI.InternalTool.BS.Service.ProjectService;
import Com.IFI.InternalTool.DS.DAO.ProjectDAO;
import Com.IFI.InternalTool.DS.DAO.Impl.ProjectDAOImpl;
//import Com.IFI.InternalTool.DS.DAO.Impl.ProjectDAOImpl;
import Com.IFI.InternalTool.DS.Model.Project;
import Com.IFI.InternalTool.DS.Model.ProjectManager;
@Service
public class ProjectServiceImpl implements ProjectService{
//	@Autowired
//	ProjectDAO projectDAO;
	@Autowired
	ProjectDAOImpl projectDAO;
	@Override
	public List<Project> getAllProject() {
		return projectDAO.getAllProject(); 
	}

	@Override
	public void saveProject(Project project) {
				
		 projectDAO.saveProject(project);
	}
//
//	@Override
//	public boolean deleteProject(long project_id) {
//		return projectDAO.deleteProject(project_id);
//	}
//
	@Override
	public Project getProjectById(Long project_id) {
		return projectDAO.getProjectById(project_id);
	}
//
//	@Override
//	public List<ProjectManager> getProjectManagerByEmp(long employee_id,long project_id) {
//		return projectDAO.getProjectManagerByEmp(employee_id,project_id);
//	}
//	
//	@Override
//	public List<Long> getProjectByEmp(long employee_id) {
//		return projectDAO.getProjectByEmp(employee_id);
//	}
//
//	@Override
//	public List<Project> getProjectsOfGroup(String group_id) {
//		
//		return projectDAO.getProjectsOfGroup(group_id);
//	}

	@Override
	public List<Project> findProjectLikeName(String projectName) {
		
		return projectDAO.findProjectLikeName(projectName);
	}
	
	
	
}
