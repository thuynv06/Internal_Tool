package Com.IFI.InternalTool.BS.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Com.IFI.InternalTool.BS.Service.EmployeeService;
import Com.IFI.InternalTool.DS.DAO.Impl.EmployeeDAOImpl;
import Com.IFI.InternalTool.DS.DAO.Impl.TypeDAOImpl;
import Com.IFI.InternalTool.DS.Model.Employee;
import Com.IFI.InternalTool.Security.UserPrincipal;

@Service("EmployeeService")
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	EmployeeDAOImpl employeeDAO;
	@Autowired
	TypeDAOImpl TypesDAO;

	@Override
	public List<Employee> getAllEmployees(final boolean hasRoleEmPloyee, long employee_id, int page, int pageSize) {
		return employeeDAO.getAllEmployees(hasRoleEmPloyee, employee_id, page, pageSize);
	}

	@Override
	public Boolean createEmployee(final long currentUserID, final Employee emp) {
		return employeeDAO.saveEmployee(emp);
	}

	@Override
	public Boolean EditEmployee(Employee employee) {
		// endcode password
		employee.setPassword(passwordEncoder.encode(employee.getPassword()));
		return employeeDAO.EditEmployee(employee);
	}

	@Override
	public Boolean deleteEmployee(long employee_id) {
		return employeeDAO.deleteEmployee(employee_id);
	}

	@Override
	public Employee getEmployeeById(long employee_id) {
		Employee emp = employeeDAO.getEmployeeById(employee_id);
		emp.setType_name(TypesDAO.getTypeByID(emp.getType_id()).getType_name().name());
		emp.setRole_name(employeeDAO.getRolesByID(emp.getRole_id()).getName().name());
		return emp;
	}

	@Override
	public List<Long> getEmployeeByManager(long manager_id) {
		return employeeDAO.getEmployeeByManager(manager_id);
	}

	@Override
	public List<Employee> findEmployeeNameLike(String name, int page, int pageSize) {
		return convertList(employeeDAO.findEmployeeNameLike(name, page, pageSize));
	}

	@Override
	public List<Employee> findEmployeeByGroupId(String group_id, int page, int pageSize) {
		return convertList(employeeDAO.findEmployeeByGroupId(group_id, page, pageSize));
	}

	@Override
	public List<Employee> getListEmployeeInProject(long project_id, int page, int pageSize) {

		return convertList(employeeDAO.getListEmployeeInProject(project_id, page, pageSize));
	}

	@Override
	public List<Employee> getListEmployeeNotInProject(final long employee_id, long project_id, int page, int pageSize) {
		return convertList(employeeDAO.getListEmployeeNotInProject(employee_id, project_id, page, pageSize));
	}

	@Override
	public Long getEmployeeIdAuthenticated() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
		Long id = user.getId();
		return id;
	}

	@Override
	public List<Employee> getListSubEmployee(long employee_id) {
		return convertList(employeeDAO.getListSubEmployees(employee_id));
	}

	public List<Employee> convertList(final List<Employee> list) {
		for (Employee item : list) {
			item.setType_name(TypesDAO.getTypeByID(item.getType_id()).getType_name().name());
			item.setRole_name(employeeDAO.getRolesByID(item.getRole_id()).getName().name());
		}
		return list;
	}

	@Override
	public Long NumRecordsEmployeeInProject(long project_id) {

		return employeeDAO.NumRecordsEmployeeInProject(project_id);
	}

	@Override
	public Long NumRecordsEmployeeNotInProject(long employee_id, long project_id) {
		return employeeDAO.NumRecordsEmployeeNotInProject(employee_id, project_id);
	}

	@Override
	public Long NumRecordsEmployeeNameLike(String name) {
		return employeeDAO.NumRecordsEmployeeNameLike(name);
	}

	@Override
	public Long NumRecordsEmployeeInGroup(String group_id) {
		return employeeDAO.NumRecordsEmployeeInGroup(group_id);
	}

}
