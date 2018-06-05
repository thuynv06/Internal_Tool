package Com.IFI.InternalTool.DS.DAO;

import org.springframework.stereotype.Repository;
import java.util.List;

import Com.IFI.InternalTool.DS.Model.Employee;

@Repository
public interface EmployeeDAO {

	List<Employee> getAllEmployees(final int page,final int pageSize);
	
	Boolean saveEmployee(final Employee employee);
	
	Boolean deleteEmployee( final long employee_id);
	
	Employee getEmployeeById(final long employee_id);
	
	List<Long> getEmployeeByManager( final long manager_id);
 
	List<Employee> findEmployeeNameLike(final String name,int page, int pageSize);

	List<Employee> findEmployeeByGroupId(final String group_id,final int page,final int pageSize);

	
	
}
