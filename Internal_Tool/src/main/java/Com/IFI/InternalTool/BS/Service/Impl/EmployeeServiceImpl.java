package Com.IFI.InternalTool.BS.Service.Impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Com.IFI.InternalTool.BS.Service.EmployeeService;
import Com.IFI.InternalTool.DS.DAO.Impl.AllocationDAOImpl;
import Com.IFI.InternalTool.DS.DAO.Impl.EmployeeDAOImpl;
import Com.IFI.InternalTool.DS.DAO.Impl.TypeDAOImpl;
import Com.IFI.InternalTool.DS.Model.Allocation;
import Com.IFI.InternalTool.DS.Model.Employee;
import Com.IFI.InternalTool.DS.Model.TypeName;
import Com.IFI.InternalTool.DS.Model.Types;
import Com.IFI.InternalTool.Security.UserPrincipal;

@Service("EmployeeService")
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	EmployeeDAOImpl employeeDAO;
	@Autowired
	TypeDAOImpl TypesDAO;
	@Autowired
	AllocationServiceImpl allocationServiceImpl;
	@Autowired
	EmployeeServiceImpl employeeServiceImpl;
	@Autowired
	ProjectServiceImpl projectServiceImpl;

	@Override
	public List<Employee> getAllEmployees(long employee_id, int page, int pageSize) {
		return employeeDAO.getAllEmployees(page, pageSize);
	}

	@Override
	public Boolean createEmployee(final long currentUserID, final Employee emp) {
		
		emp.setPassword(passwordEncoder.encode(emp.getPassword()));
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
		//xoa cac allocation
		
		List<Allocation> listAllocationOfEmployee = allocationServiceImpl.findAllocationByEmployeeID(employee_id, 1, Integer.MAX_VALUE, false);
		boolean success = true;
		for (Allocation allocation : listAllocationOfEmployee) {
			success = allocationServiceImpl.deleteByID(allocation.getAllocation_id());	
			if (!success) {
				break;
			}
		}
		// neu xoa tat ca cac allocation cua nhan vien do thanh cong thi xoa nhan vien
		if (success) {
			return employeeDAO.deleteEmployee(employee_id);
		}else {
			return success;
		}
		
	}

	@Override
	public Employee getEmployeeById(long employee_id) {
		Employee emp = employeeDAO.getEmployeeById(employee_id);
		if (emp != null) {
			emp.setType_name(emp.getTypes().getType_name().toString());
			emp.setRole_name(emp.getRole().getName().toString());
		}
		
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
	public List<Employee> getListEmployeeInProject(long leaderId, long projectId, int page, int pageSize) {
		return projectServiceImpl.getListEmployee(leaderId, projectId, page, pageSize);
		//return convertList(employeeDAO.getListEmployeeInProject(project_id, page, pageSize));
	}

	@Override
	public List<Employee> getListEmployeeNotInProject(long currentEmployeeId, long projectId, int page, int pageSize) {
		List<Employee> listSubEmployee = employeeServiceImpl.getListSubEmployee(currentEmployeeId);
		List<Employee> listEmployeeInProject = projectServiceImpl.getListEmployee(currentEmployeeId, projectId, 1, Integer.MAX_VALUE);
		List<Employee> listEmployeeNotInProject = new ArrayList<Employee>();
		for (Employee employee : listSubEmployee) {
			boolean notIn = true;
			for (int i = 0; i < listEmployeeInProject.size(); i++) {
				if (employee.getEmployee_id().longValue() == listEmployeeInProject.get(i).getEmployee_id().longValue()) {
					notIn = false;
					break;
				}
			}
			if (notIn) {
				listEmployeeNotInProject.add(employee);
			}			
		}
		return listEmployeeNotInProject;
		//return convertList(employeeDAO.getListEmployeeNotInProject(employee_id, project_id, page, pageSize));
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

	@Override
	public List<Employee> getListEmployeeInProjectDoNotAllocated(long currentEmployeeId, long projectId) {
		List<Employee>  listEmployeeInProject = getListEmployeeInProject(currentEmployeeId, projectId, 1, Integer.MAX_VALUE);
		List<Employee> listEmployeeInProjectDoNotAllocated = new ArrayList<Employee>();
		for (Employee employee : listEmployeeInProject) {
			if (allocationServiceImpl.findAllocationByEmpIdProId(employee.getEmployee_id().longValue(), projectId).size() == 0) {
				listEmployeeInProjectDoNotAllocated.add(employee);
			}
		}
		return listEmployeeInProjectDoNotAllocated;
	}

	@Override
	public List<Types> getAllTypes() {
		return TypesDAO.getAllTypes();
	}

}
