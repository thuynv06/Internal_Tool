package Com.IFI.InternalTool.BS.Service;



import Com.IFI.InternalTool.DS.Model.Project;

import Com.IFI.InternalTool.Payloads.PagedResponse;
import Com.IFI.InternalTool.Payloads.ProjectRequest;

public interface ProjectService {
	
	public Project createProject(ProjectRequest projectRequest);
	
	public void deleteProjectById(String group_id);
	
	public Project getProjectById(String group_id);
	
	public PagedResponse<Project>  getAllGroup();
	
	public PagedResponse<Project> findProjectByName(String name);
	
	public PagedResponse<Project> findProjectsLikeName(String name);

}
