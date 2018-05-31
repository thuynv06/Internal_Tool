package Com.IFI.InternalTool.BS.Service.Impl;

import org.springframework.stereotype.Service;

import Com.IFI.InternalTool.BS.Service.EmployeeService;
import Com.IFI.InternalTool.DS.Model.Employee;
import Com.IFI.InternalTool.Payloads.PagedResponse;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	@Override
	public Employee createEmployee(Employee emp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteEmployeeById(String employee_id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Employee getEmployeeById(long employee_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PagedResponse<Employee> getAllEmployee() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PagedResponse<Employee> findEmployeeLikeName(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
