package Com.IFI.InternalTool.BS.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Com.IFI.InternalTool.BS.Service.ProjectService;
import Com.IFI.InternalTool.DS.DAO.ProjectDAO;
import Com.IFI.InternalTool.DS.Model.Project;
import Com.IFI.InternalTool.Payloads.PagedResponse;
import Com.IFI.InternalTool.Payloads.ProjectRequest;
@Service("ProjectService")
public class ProjectServiceImpl implements ProjectService{
	@Autowired
	ProjectDAO projectDAO;

	@Override
	public Project createProject(ProjectRequest projectRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteProjectById(String group_id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Project getProjectById(String group_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PagedResponse<Project> getAllGroup() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PagedResponse<Project> findProjectByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PagedResponse<Project> findProjectsLikeName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		return super.equals(arg0);
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
	
}
