package Com.IFI.InternalTool.DS.DAO;

import java.util.Set;

public interface ProjectManagerDAO {
	Set<Long> getProjectIDs(final long employee_id);
	
}
