package Com.IFI.InternalTool.DS.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import Com.IFI.InternalTool.DS.Model.Group_IFI;
@Repository
public interface Group_IFIDAO  extends JpaRepository<Group_IFI, Long>{
	
	 List<Group_IFI> findByname(String group_name);
	
	@Query("SELECT g FROM group_ifi g where g.group.id = :groupId")
    Group_IFI findByGroupId(@Param("groupId") String groupId);
	
	
	@Query("Delete g FROM group_ifi g where g.group.id = :groupId")
    Group_IFI deleteGroupById(@Param("groupId") String groupId);
	
	
	
	
}
