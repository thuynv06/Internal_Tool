package Com.IFI.InternalTool.BS.Service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import Com.IFI.InternalTool.DS.Model.Employee;

public interface EmployeeService {
	// get list employeess and paginations
	public List<Employee> getAllEmployees( final long employee_id, final int page, final int pageSize);

	// get employss by ID
	public Employee getEmployeeById(final long employee_id);

	Boolean createEmployee(final long currentUserID, final Employee emp);

	public Boolean EditEmployee(Employee employee);

	// delete employees by id
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Boolean deleteEmployee(final long employee_id);

	// find Employees Name Like
	public List<Employee> findEmployeeNameLike(final String name, int page, int pageSize);

	Long NumRecordsEmployeeNameLike(final String name);

	// find Employees By Group ID
	public List<Employee> findEmployeeByGroupId(final String group_id, final int page, final int pageSize);

	Long NumRecordsEmployeeInGroup(final String group_id);

	// tim kiem danh sanh nhan vien duoc phan cong vao 1 project
	List<Employee> getListEmployeeInProject(long project_id, int page, int pageSize);

	Long NumRecordsEmployeeInProject(long project_id);

	// tim kiem danh sanh nhan vien chua duoc phan cong vao 1 project
	List<Employee> getListEmployeeNotInProject(final long employee_id, long project_id, int page, int pageSize);

	Long NumRecordsEmployeeNotInProject(final long employee_id, final long project_id);

	public List<Long> getEmployeeByManager(final long manager_id);

	Long getEmployeeIdAuthenticated();

	// lay danh sach nhan vien cap duoi
	List<Employee> getListSubEmployee(final long employee_id);

}
