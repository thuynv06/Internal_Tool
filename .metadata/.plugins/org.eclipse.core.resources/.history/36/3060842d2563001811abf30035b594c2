package Com.IFI.InternalTool.DS.DAO.Impl;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import Com.IFI.InternalTool.DS.DAO.VacationDAO;
import Com.IFI.InternalTool.DS.Model.Vacation;
import Com.IFI.InternalTool.DS.Model.Vacation_approved;
import Com.IFI.InternalTool.DS.Model.Vacation_type;
import Com.IFI.InternalTool.DS.Model.SearchModel.VacationSearch;
@Repository("VacationDAO")
@Transactional
public class VacationDAOImpl implements VacationDAO{
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	@Override
	public List<Vacation> getAllVacationByEmp(long employee_id) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "FROM Vacation where employee_id=:employee_id";
		Query query = session.createQuery(hql);
		query.setParameter("employee_id", employee_id);
		List<Vacation> list=query.list();
		session.close();
		return list;
	}
	@Override
	public boolean saveVacation(Vacation vacation) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Transaction tx = null;
		tx=session.beginTransaction();
		session.saveOrUpdate(vacation);
		session.close();
		tx.commit();
		return true;
	}
	@Override
	public boolean deleteVacation(long vacation_id) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Transaction tx = null;
		tx=session.beginTransaction();
		String hql = "Delete from Vacation where vacation_id=:vacation_id and is_updateable=true";
		Query query = session.createQuery(hql);
		query.setParameter("vacation_id", vacation_id);
		query.executeUpdate();
		tx.commit();
		session.close();
		return true;
	}
	@Override
	public Vacation getVacationById(long vacation_id) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "FROM Vacation where vacation_id=:vacation_id";
		Query query = session.createQuery(hql);
		query.setParameter("vacation_id", vacation_id);
		Vacation v=(Vacation) query.uniqueResult();
		return v;
	}
	@Override
	public void saveVacationApproved(Vacation_approved vacation_approved) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Transaction tx = null;
		tx=session.beginTransaction();
		session.saveOrUpdate(vacation_approved);
		session.close();
		tx.commit();
	}
	@Override
	public List<Vacation_type> getAllVacationType() {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "from Vacation_type";
		Query query = session.createQuery(hql);
		List<Vacation_type> list=query.list();
		session.close();
		return list;
	}
	@Override
	public List<Vacation> searchVacation(VacationSearch vacationSearch) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "Select v from Vacation v INNER JOIN Employee AS e ON v.employee_id= e.employee_id INNER JOIN Project AS p ON v.project_id=p.project_id ";
		hql+="WHERE (:emp_name IS NULL OR e.fullname LIKE CONCAT('%', :emp_name, '%')) ";
		hql+="AND (:status =0 or v.status=:status) ";
		hql+="AND (:pro_name IS NULL OR p.name LIKE CONCAT('%', :pro_name, '%')) ";
		hql+="AND (:from_date IS NULL or DATE_FORMAT(v.from_date, '%Y-%m-%d')=DATE_FORMAT(:from_date, '%Y-%m-%d') ) ";
		hql+="AND (:to_date IS NULL or DATE_FORMAT(v.to_date, '%Y-%m-%d')=DATE_FORMAT(:to_date, '%Y-%m-%d') ) ";
		Query query = session.createQuery(hql);
		query.setParameter("emp_name", vacationSearch.getEmp_name());
		query.setParameter("pro_name", vacationSearch.getPro_name());
		query.setParameter("from_date", vacationSearch.getFrom_date());
		query.setParameter("to_date", vacationSearch.getTo_date());
		query.setParameter("status", vacationSearch.getStatus());
		List<Vacation> list=query.list();
		session.close();
		return list;
		
	}

}
