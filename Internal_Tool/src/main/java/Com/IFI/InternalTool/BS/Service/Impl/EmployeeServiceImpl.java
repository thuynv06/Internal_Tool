package Com.IFI.InternalTool.BS.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Com.IFI.InternalTool.BS.Service.EmployeeService;
import Com.IFI.InternalTool.DS.DAO.Impl.EmployeeDAOImpl;
import Com.IFI.InternalTool.DS.Model.Employee;

@Service("EmployeeService")
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeDAOImpl employeeDAO;

	@Override
	public List<Employee> getAllEmployees(int page, int pageSize) {
		return employeeDAO.getAllEmployees(page, pageSize);
	}

	@Override
	public Boolean saveEmployee(Employee employee) {
		if (employeeDAO.saveEmployee(employee)) {
			return true;
		}
		return false;
	}

	@Override
	public Boolean deleteEmployee(long employee_id) {
		return employeeDAO.deleteEmployee(employee_id);
	}

	@Override
	public Employee getEmployeeById(long employee_id) {
		return employeeDAO.getEmployeeById(employee_id);
	}

	@Override
	public List<Long> getEmployeeByManager(long manager_id) {
		return employeeDAO.getEmployeeByManager(manager_id);
	}

	@Override
	public List<Employee> findEmployeeNameLike(String name, int page, int pageSize) {
		return employeeDAO.findEmployeeNameLike(name, page, pageSize);
	}

	@Override
	public List<Employee> findEmployeeByGroupId(String group_id, int page, int pageSize) {
		// TODO Auto-generated method stub
		return employeeDAO.findEmployeeByGroupId(group_id, page, pageSize);
	}

}
