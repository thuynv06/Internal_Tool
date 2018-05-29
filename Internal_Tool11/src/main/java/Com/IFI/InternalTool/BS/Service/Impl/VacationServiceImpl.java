package Com.IFI.InternalTool.BS.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Com.IFI.InternalTool.BS.Service.VacationService;
import Com.IFI.InternalTool.DS.DAO.VacationDAO;
import Com.IFI.InternalTool.DS.Model.Vacation;
import Com.IFI.InternalTool.DS.Model.Vacation_Approved;
import Com.IFI.InternalTool.DS.Model.Vacation_Type;
import Com.IFI.InternalTool.DS.Model.SearchModel.VacationSearch;
@Service("VacationService")
public class VacationServiceImpl implements VacationService{
	@Autowired
	VacationDAO vacationDAO;
	
	@Override
	public List<Vacation> getAllVacationByEmp(long employee_id) {
		return vacationDAO.getAllVacationByEmp(employee_id);
	}
	
	@Override
	public boolean saveVacation(Vacation vacation) {
		return vacationDAO.saveVacation(vacation);
	}
	
	@Override
	public boolean deleteVacation(long vacation_id) {
		return vacationDAO.deleteVacation(vacation_id);
	}
	
	@Override
	public Vacation getVacationById(long vacation_id) {
		return vacationDAO.getVacationById(vacation_id);
	}
	
	@Override
	public void saveVacationApproved(Vacation_Approved vacation_approved) {
		vacationDAO.saveVacationApproved(vacation_approved);
	}
	
	@Override
	public List<Vacation_Type> getAllVacationType() {
		return vacationDAO.getAllVacationType();
	}
	
	@Override
	public List<Vacation> searchVacation(VacationSearch vacationSearch) {
		return vacationDAO.searchVacation(vacationSearch);
	}

}
