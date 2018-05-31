package Com.IFI.InternalTool.DS.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import Com.IFI.InternalTool.DS.Model.Group_IFI;

@Repository
public interface Group_IFIDAO  extends JpaRepository<Group_IFI, Long>{
	
	List<Group_IFI> findByname(String group_name);
	
	@Query("SELECT g FROM Group_IFI g where g.name LIKE %:name%")
	List<Group_IFI> findGroupLikeName(@Param("name") String name);
	
	@Query("SELECT g FROM Group_IFI g where g.group_id = :group_id")
    Group_IFI findGroupById(@Param("group_id") String groupId);
	
	
	@Query("Delete FROM Group_IFI g where g.group_id = :group_id")
    Group_IFI deleteGroupById(@Param("group_id") String groupId);
	
	
	
	
}
