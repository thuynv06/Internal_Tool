package Com.IFI.InternalTool.BS.Service;

import java.util.List;

import Com.IFI.InternalTool.DS.Model.Employee;
import Com.IFI.InternalTool.DS.Model.Group_IFI;

public interface EmployeeService {

	List<Employee>  getAllEmployee();
	boolean saveEmployee(Employee employee);
	boolean deleteEmployee(long employee_id);
	Employee getEmployeeById(long employee_id);
	List<Group_IFI>  getAllGroup();
}
