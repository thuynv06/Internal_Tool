package Com.IFI.InternalTool.DS.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import Com.IFI.InternalTool.DS.Model.Employee;

@Repository

public interface EmployeeDAO extends JpaRepository<Employee, Long> {


	Optional<Employee> findByEmail(String email);
    Optional<Employee> findByUsernameOrEmail(String username, String email);

    Optional<Employee> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    
	// find Project like Name
	@Query("SELECT emp FROM Employee emp where emp.fullname LIKE %:name%")
	List<Employee> findEmployeeLikeName(@Param("name") String name);
	
	//find project with group_id
	@Query("SELECT emp FROM Employee emp where emp.group_id = :group_id")
	List<Employee> findEmployeeByGroupId(@Param("group_id") String group_id);
	

    
}
