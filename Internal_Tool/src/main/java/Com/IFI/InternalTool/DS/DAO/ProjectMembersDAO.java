package Com.IFI.InternalTool.DS.DAO;

import java.util.List;

import Com.IFI.InternalTool.DS.Model.ProjectMembers;

public interface ProjectMembersDAO {
	
	Boolean IsMembersOfProject(final long employee_id,final long project_id);
	
	Boolean AddMemberToProject(final ProjectMembers projectMember);
	
	Boolean RemoveMemberOfProject(final long project_id,final long employee_id);
	
	List<Long> ListEmPloyeesIdInProject(final long project_id);
	
	Boolean deleteAllProjectMember(final long project_id);
	
}
