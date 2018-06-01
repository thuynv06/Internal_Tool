package Com.IFI.InternalTool.BS.Service.Impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Com.IFI.InternalTool.BS.Service.ProjectService;
import Com.IFI.InternalTool.DS.DAO.ProjectDAO;
import Com.IFI.InternalTool.DS.Model.Project;
import Com.IFI.InternalTool.Payloads.PagedResponse;

import Com.IFI.InternalTool.Utils.Business;

@Service
public class ProjectServiceImpl implements ProjectService {
	@Autowired
	private ProjectDAO projectDAO;
	
	Business b= new Business();
	@Override
	public Project createProject(final Project project) {
		project.setStatus(true);
		project.setMonth(b.getMonth(project.getStart_date()));
		project.setYear(b.getYear(project.getStart_date()));
		return projectDAO.save(project);

	}

	@Override
	public void deleteProjectById(long group_id) {
		
		projectDAO.deleteById(group_id);

	}

	@Override
	public Project getProjectById(long group_id) {
		return projectDAO.getOne(group_id);
	}

	@Override
	public PagedResponse<Project> getAllGroup() {
		List<Project> listProject= projectDAO.findAll();
		if (listProject.size() == 0) {
			return new PagedResponse<>(Collections.emptyList(), false);
		}
		return new PagedResponse<>(listProject, true);
	}

	@Override
	public PagedResponse<Project> findProjectByName(String name) {
		Optional<Project> p= projectDAO.findByName(name);
		
		return null;
	}

	@Override
	public PagedResponse<Project> findProjectsLikeName(String name) {
		List<Project> listProject= projectDAO.findProjectLikeName(name);
		if (listProject.size() == 0) {
			return new PagedResponse<>(Collections.emptyList(), false);
		}
		return new PagedResponse<>(listProject, true);
	}



}
