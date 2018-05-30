package Com.IFI.InternalTool.DS.DAO;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Com.IFI.InternalTool.DS.Model.Group_IFI;
@Repository
public interface Group_IFIDAO  extends JpaRepository<Group_IFI, Long>{
	Optional<Group_IFI> findByname(String username);
	
	
	
}
