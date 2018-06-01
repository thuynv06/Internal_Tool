package Com.IFI.InternalTool.DS.DAO;

import org.springframework.stereotype.Repository;
import java.util.List;

import Com.IFI.InternalTool.DS.Model.Employee;

@Repository
public interface EmployeeDAO {

	List<Employee> getAllEmployee(int page, int pageSize, String sortedColumn, Boolean desc);

	Long saveEmployee(Employee employee);

	Boolean deleteEmployee(final long employee_id);

	Employee getEmployeeById(final long employee_id);

	List<Long> getEmployeeByManager(final long manager_id);
 
	
	// find Project like Name
	// @Query("SELECT emp FROM Employee emp where emp.fullname LIKE %:name% ") 
	List<Employee> findEmployeeLikeName(final String name,int page, int pageSize, String sortedColumn, Boolean desc);

	// find project with group_id
	// @Query("SELECT emp FROM Employee emp where emp.group_id = :group_id")
	Employee findEmployeeByGroupId(final String group_id);

	
	
}
