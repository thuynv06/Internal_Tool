package Com.IFI.InternalTool.DS.DAO;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Com.IFI.InternalTool.DS.Model.Allocation;

@Repository
public interface AllocationDAO extends JpaRepository<Allocation, Long> {
	
	
}
