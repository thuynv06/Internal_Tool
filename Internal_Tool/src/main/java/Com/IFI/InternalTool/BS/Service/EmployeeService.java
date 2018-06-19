package Com.IFI.InternalTool.BS.Service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import Com.IFI.InternalTool.DS.Model.Employee;

public interface EmployeeService {
	// get list employeess and paginations
	@PreAuthorize("hasRole('ROLE_LEADER_A') OR hasRole('ROLE_LEADER_B') OR hasRole('ROLE_LEADER_C') OR hasRole('ROLE_ADMIN')")
	public List<Employee> getAllEmployees( final long employee_id, final int page, final int pageSize);

	// get employss by ID
	@PreAuthorize("hasRole('ROLE_LEADER_A') OR hasRole('ROLE_LEADER_B') OR hasRole('ROLE_LEADER_C') OR hasRole('ROLE_ADMIN')")
	public Employee getEmployeeById(final long employee_id);
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	Boolean createEmployee(final long currentUserID, final Employee emp);

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Boolean EditEmployee(Employee employee);

	// delete employees by id
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Boolean deleteEmployee(final long employee_id);

	// find Employees Name Like
	@PreAuthorize("hasRole('ROLE_LEADER_A') OR hasRole('ROLE_LEADER_B') OR hasRole('ROLE_LEADER_C') OR hasRole('ROLE_ADMIN')")
	public List<Employee> findEmployeeNameLike(final String name, int page, int pageSize);
	@PreAuthorize("hasRole('ROLE_LEADER_A') OR hasRole('ROLE_LEADER_B') OR hasRole('ROLE_LEADER_C') OR hasRole('ROLE_ADMIN')")
	Long NumRecordsEmployeeNameLike(final String name);

	// find Employees By Group ID
	@PreAuthorize("hasRole('ROLE_LEADER_A') OR hasRole('ROLE_LEADER_B') OR hasRole('ROLE_LEADER_C') OR hasRole('ROLE_ADMIN')")
	public List<Employee> findEmployeeByGroupId(final String group_id, final int page, final int pageSize);
	@PreAuthorize("hasRole('ROLE_LEADER_A') OR hasRole('ROLE_LEADER_B') OR hasRole('ROLE_LEADER_C') OR hasRole('ROLE_ADMIN')")
	Long NumRecordsEmployeeInGroup(final String group_id);

	// tim kiem danh sanh nhan vien duoc phan cong vao 1 project
	@PreAuthorize("hasRole('ROLE_LEADER_A') OR hasRole('ROLE_LEADER_B') OR hasRole('ROLE_LEADER_C') OR hasRole('ROLE_ADMIN')")
	List<Employee> getListEmployeeInProject(long project_id, int page, int pageSize);
	@PreAuthorize("hasRole('ROLE_LEADER_A') OR hasRole('ROLE_LEADER_B') OR hasRole('ROLE_LEADER_C') OR hasRole('ROLE_ADMIN')")
	Long NumRecordsEmployeeInProject(long project_id);

	// tim kiem danh sanh nhan vien chua duoc phan cong vao 1 project
	@PreAuthorize("hasRole('ROLE_LEADER_A') OR hasRole('ROLE_LEADER_B') OR hasRole('ROLE_LEADER_C') OR hasRole('ROLE_ADMIN')")
	List<Employee> getListEmployeeNotInProject(final long currentEmployeeId,final long projectId, int page, int pageSize);
	@PreAuthorize("hasRole('ROLE_LEADER_A') OR hasRole('ROLE_LEADER_B') OR hasRole('ROLE_LEADER_C') OR hasRole('ROLE_ADMIN')")
	Long NumRecordsEmployeeNotInProject(final long employee_id, final long project_id);

	public List<Long> getEmployeeByManager(final long manager_id);

	Long getEmployeeIdAuthenticated();

	// lay danh sach nhan vien cap duoi
	@PreAuthorize("hasRole('ROLE_LEADER_A') OR hasRole('ROLE_LEADER_B') OR hasRole('ROLE_LEADER_C') OR hasRole('ROLE_ADMIN')")
	List<Employee> getListSubEmployee(final long employee_id);
	
	//lấy danh sách nhân viên trong project nhưng chưa được phân công
	List<Employee> getListEmployeeInProjectDoNotAllocated(final long currentEmployeeId, final long projectId);

}
