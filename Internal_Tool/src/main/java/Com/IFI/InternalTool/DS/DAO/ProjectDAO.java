package Com.IFI.InternalTool.DS.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Com.IFI.InternalTool.DS.Model.Project;
import java.util.Optional;


@Repository
public interface ProjectDAO extends JpaRepository<Project, Long> {
	
	Optional<Project> findByName(String name);
	// find Project like Name
	@Query("SELECT p FROM Project p where p.name LIKE %:name%")
	List<Project> findProjectLikeName(@Param("name") String name);
	
	//find project with group_id
	@Query("SELECT p FROM Project p where  p.group_id = :group_id")
	List<Project> findProjectByGroupId(@Param("group_id") String group_id);
	
	//find Project by Month
	@Query("SELECT p FROM Project p where  p.month = :month")
	List<Project> findProjectByMonth(@Param("month") int month);
	//find Project by Year
	@Query("SELECT p FROM Project p where  p.year = :year")
	List<Project> findProjectByYear(@Param("year") int year);
	
//	List<Project> findByIdIn(List<Long> projectIds);
	
	

}
