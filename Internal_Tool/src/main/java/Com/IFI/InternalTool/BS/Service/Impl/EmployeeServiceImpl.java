package Com.IFI.InternalTool.BS.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Com.IFI.InternalTool.BS.Service.EmployeeService;
import Com.IFI.InternalTool.DS.DAO.Impl.EmployeeDAOImpl;
import Com.IFI.InternalTool.DS.Model.Employee;
@Service("EmployeeService")
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	EmployeeDAOImpl employeeDAO;
	
	@Override
	public List<Employee> getAllEmployee(int page, int pageSize,String sortedColumn,Boolean desc) {
		return employeeDAO.getAllEmployee(page, pageSize, sortedColumn, desc);
	}
	@Override
	public Long saveEmployee(Employee employee) {
		 return employeeDAO.saveEmployee(employee);
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
	
	
	
}
