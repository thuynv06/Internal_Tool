package Com.IFI.InternalTool.DS.DAO;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Priority;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import Com.IFI.InternalTool.DS.Model.Overtime;
import Com.IFI.InternalTool.DS.Model.Overtime_Approved;
import Com.IFI.InternalTool.DS.Model.Overtime_Log;
import Com.IFI.InternalTool.DS.Model.Overtime_Type;
import Com.IFI.InternalTool.DS.Model.SearchModel.OvertimeSearch;

@Repository
public class OvertimeDao {

	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	@Autowired
	private EntityManager entityManager;
	//a
	public List<Overtime_Type> getAllOvertimeType(){
		String hql = "FROM Overtime_Type";
		return (List<Overtime_Type>) entityManager.createQuery(hql).getResultList();
	}
	//a
	public Overtime_Type getOvertimeTypeById(long overtime_type_id) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "From Overtime_Type ot where ot.overtime_type_id=:overtime_type_id";
		Query query = session.createQuery(hql);
		query.setParameter("overtime_type_id", overtime_type_id);
		Overtime_Type ot=(Overtime_Type) query.uniqueResult();
		session.close();
		return ot;
	}
	//a
	public Long countAllOvertimeByEmp(List<Integer> status, Boolean is_approved, Long emp_id) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Long total = 0L;
		for (Integer mystatus : status) {
			String hql = "SELECT count(o.overtime_id) FROM  Overtime o WHERE o.employee_id = :emp_id and ((:is_approved IS NULL and o.is_approved IS NULL) or o.is_approved=:is_approved) and o.status=:mystatus";
			Query query = session.createQuery(hql);
			query.setParameter("emp_id", emp_id);
			query.setParameter("is_approved", is_approved);
			query.setParameter("mystatus", mystatus);
			Long count = (Long) query.uniqueResult();
			total = total + count;
		}
		session.close();
		return total;
	}
	
	//page employee
	//a
	public List<Overtime> getAllOvertimeByEmp(long employee_id, int page, int pageSize, String sortedColumn,Boolean desc, Boolean is_approved, List<Integer> status) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "FROM Overtime o where o.employee_id=:employee_id and ((:is_approved IS NULL and o.is_approved IS NULL) or o.is_approved=:is_approved) and o.status in (:status) ";
		if (sortedColumn != null && desc != null) {
			String order = "";
			if (desc) {
				order = "desc";
			}
			hql += "ORDER BY " + sortedColumn + " " + order;
		}else {
			
			hql += " ORDER BY overtime_id" +  " desc";
		}
		Query query = session.createQuery(hql);
		query.setParameter("employee_id", employee_id);
		query.setParameter("is_approved", is_approved);
		query.setParameterList("status", status);
		query.setFirstResult((page - 1) * pageSize);
		query.setFetchSize(pageSize);
		query.setMaxResults(pageSize);
		List<Overtime> list = query.list();
		session.close();
		return list;
	}
	// page manager
	//a
	public List<Overtime> getAllOvertimeByEmp2(long manager_id, int page, int pageSize, String sortedColumn,
			Boolean desc) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "Select o FROM Overtime o INNER JOIN Employee AS e ON o.employee_id= e.employee_id INNER JOIN Project AS p ON o.project_id=p.project_id INNER JOIN ProjectManager AS pm ON (o.employee_id=pm.employee_id and o.project_id=pm.project_id  and o.status=pm.priority)  where  pm.manager_id=:manager_id ";
		if (sortedColumn != null && desc != null) {
			String order = "";
			if (desc) {
				order = "desc";
			}
			hql += "ORDER BY " + sortedColumn + " " + order;
		}
		Query query = session.createQuery(hql);
		query.setParameter("manager_id", manager_id);
		query.setReadOnly(true);
		query.setFirstResult((page - 1) * pageSize);
		query.setFetchSize(pageSize);
		query.setMaxResults(pageSize);
		List<Overtime> list = query.list();
		if (list.size() > pageSize) {
			return list = list.subList(0, pageSize);
		}
		session.close();
		return list;
	}
	//a
	public List<Long> getApprovedIdByOvertimeId(Long overtime_id) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "Select o.approved_id From Overtime_Log o where overtime_id=:overtime_id and o.approved_id>0";
		Query query = session.createQuery(hql);
		query.setParameter("overtime_id", overtime_id);
		List<Long> list = query.list();
		session.close();
		return list;
	}
	//a
	public Long getDisApproveIdByOvertimeId(Long overtime_id) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "Select o.disapproved_id From Overtime_Log o where overtime_id=:overtime_id and o.disapproved_id>0";
		Query query = session.createQuery(hql);
		query.setParameter("overtime_id", overtime_id);
		Long a = (Long) query.uniqueResult();
		session.close();
		return a;
	}
	//a
	public Overtime getOvertimeById(long overtime_id) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "FROM Overtime where overtime_id=:overtime_id";
		Query query = session.createQuery(hql);
		query.setParameter("overtime_id", overtime_id);
		Overtime o=(Overtime) query.uniqueResult();
		return o;
	}
	//a
	public boolean saveOvertime(Overtime overtime) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Transaction tx = null;
		tx=session.beginTransaction();
		session.saveOrUpdate(overtime);
		session.close();
		tx.commit();
		return true;
	}
	//a
	public List<Long> getManagerByOvertimeId(long overtime_id){
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "Select t.manager_id From Overtime_Approved t INNER JOIN Overtime AS v ON (t.overtime_id=v.overtime_id) where  t.overtime_id=:overtime_id";
		Query query = session.createQuery(hql);
		query.setParameter("overtime_id", overtime_id);
		List<Long> list = query.list();
		session.close();
		return list;
	}
	//a
	public Long getManagerIDByEmpProAndStatus(long employee_id, long project_id, int status) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();

		String hql = "Select pm.manager_id from ProjectManager pm where pm.employee_id =: employee_id and pm.project_id=: project_id and pm.priority=: status";
		Query query = session.createQuery(hql);
		query.setParameter("employee_id", employee_id);
		query.setParameter("project_id", project_id);
		query.setParameter("status", status);
		Long result = (Long) query.uniqueResult();
		session.close();
		return result;
	}
	//a
	public boolean saveOvertimeLog(Overtime_Log overtime_Log) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Transaction tx = null;
		tx= session.beginTransaction();
		session.saveOrUpdate(overtime_Log);
		tx.commit();
		session.close();
		return true;
	}
	//a
	public boolean saveOvertimeApproved(Overtime_Approved overtime_Approved) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Transaction tx = null;
		tx=session.beginTransaction();
		session.saveOrUpdate(overtime_Approved);
		session.close();
		tx.commit();
		return true;
	}
	//a
	public Long countAllOvertimeByEmp2(long manager_id) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "Select count(o) FROM Overtime o INNER JOIN Employee AS e ON o.employee_id= e.employee_id INNER JOIN Project AS p ON o.project_id=p.project_id INNER JOIN ProjectManager AS pm ON (o.employee_id=pm.employee_id and o.project_id=pm.project_id  and o.status=pm.priority)  where  pm.manager_id=:manager_id ";
		Query query = session.createQuery(hql);
		query.setParameter("manager_id", manager_id);
		query.setReadOnly(true);
		Long result=(Long) query.uniqueResult();
		session.close();
		return result;
	}
	//a
	public boolean deleteOvertime(long overtime_id) {
		Session session = entityManager.unwrap(org.hibernate.Session.class);
		Transaction tx = null;
		tx = session.beginTransaction();
		String hql = "DELETE Overtime WHERE overtime_id =: overtime_id";
		Query query = session.createQuery(hql);
		query.setParameter("overtime_id", overtime_id);
		query.executeUpdate();
		tx.commit();
		session.close();
		return true;
		
	}
	//a
	public Overtime_Log getOverTimeLogByOvertimeIdAndNextApprovedId(long overtime_id, long next_approve_id){
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "FROM Overtime_Log where overtime_id = :overtime_id and next_approve_id = :next_approve_id";
		Query query = session.createQuery(hql);
		query.setParameter("overtime_id", overtime_id);
		query.setParameter("next_approve_id", next_approve_id);
		Overtime_Log overtime_Log = (Overtime_Log) query.uniqueResult();
		session.close();
		return overtime_Log;

	}
	//a
	public List<Long> getApprovedIdOvertimeLogByManager(long manager_id,int page, int pageSize) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
				String hql = "Select o.overtime_id From Overtime_Log o where o.approved_id=:manager_id ORDER BY o.overtime_log_id DESC";
				Query query = session.createQuery(hql);
				query.setParameter("manager_id", manager_id);
				query.setFetchSize(pageSize);
				query.setMaxResults(pageSize);
				List<Long> list = query.list();
				if (list.size() > pageSize) {
					return list = list.subList(0, pageSize);
				}
				session.close();
				return list;
	}
	//a
	public List<Long> getDisapproveIdOvertimeLogByManager(long manager_id,int page, int pageSize) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "Select o.overtime_id From Overtime_Log o where o.disapproved_id=:manager_id ORDER BY o.overtime_log_id DESC";
		Query query = session.createQuery(hql);
		query.setParameter("manager_id", manager_id);
		query.setFetchSize(pageSize);
		query.setMaxResults(pageSize);
		List<Long> list = query.list();
		if (list.size() > pageSize) {
			return list = list.subList(0, pageSize);
		}
		session.close();
		return list;
	}
	//a
	public int getPriority(long manager_id,long overtime_id) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "Select t.priority from Overtime_Approved t where manager_id=:manager_id and overtime_id=:overtime_id";
		Query query = session.createQuery(hql);
		query.setParameter("manager_id", manager_id);
		query.setParameter("overtime_id", overtime_id);
		int p=(int) query.uniqueResult();
		return p;
	}
	//a
	public int getMaxPriority(long overtime_id) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "Select MAX(t.priority) from Overtime_Approved t where overtime_id=:overtime_id";
		Query query = session.createQuery(hql);
		query.setParameter("overtime_id", overtime_id);
		int max=(int) query.uniqueResult();
		return max;
	}
	//a
	public List<Overtime> searchOvertimeManager(Long manager_id, int page, int pageSize, String sortedColumn, Boolean desc,Boolean is_approved,List<Integer> status,
			OvertimeSearch overtimeSearch) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		if(status.contains(-1)) {
			String hql = "Select o from Overtime o INNER JOIN Employee AS e ON o.employee_id= e.employee_id INNER JOIN Project AS p ON o.project_id=p.project_id INNER JOIN ProjectManager pm ON (pm.employee_id=o.employee_id and pm.project_id=o.project_id  and pm.priority=o.status ) ";
			hql += "WHERE (:emp_name IS NULL OR e.fullname LIKE CONCAT('%', :emp_name, '%')) ";
			hql += "AND (:pro_name IS NULL OR p.name LIKE CONCAT('%', :pro_name, '%')) ";
			hql += "AND ((:from_hour IS NULL and ( :to_hour IS NOT NULL and (:to_hour>= o.to_hour) or (:to_hour < o.to_hour and :to_hour>=o.from_hour))) ";
			hql += "or (:to_hour IS NULL and (:from_hour IS NOT NULL and :from_hour <= o.to_hour)) ";
			hql += "or (:from_hour >= o.from_hour and :from_hour <= o.to_hour and :to_hour >= o.from_hour and :to_hour <= o.to_hour and :from_hour <= :to_hour) ";
			hql += "or (:from_hour <= o.from_hour and :to_hour >= o.from_hour and :to_hour <= o.to_hour and :from_hour <= :to_hour) ";
			hql += "or (:from_hour >= o.from_hour and :from_hour <= o.to_hour and :to_hour >= o.to_hour and :from_hour <= :to_hour) ";
			hql += "or (:from_hour <= o.from_hour and :to_hour >= o.to_hour and :from_hour <= :to_hour) ";
			hql += "or (:from_hour IS NULL and :to_hour IS NULL)) ";
			hql += "AND ((:is_approved IS NULL and o.is_approved IS NULL) or o.is_approved=:is_approved) and o.status =-1 "; 
			hql += "AND (pm.manager_id=:manager_id) ";
			
			if (sortedColumn != null && desc != null) {
				String order = "";
				if (desc) {
					order = "desc";
				}
				hql += "ORDER BY " + sortedColumn + " " + order;
			}
			Query query = session.createQuery(hql);
			query.setParameter("manager_id", manager_id);
			query.setParameter("emp_name", overtimeSearch.getEmp_name());
			query.setParameter("pro_name", overtimeSearch.getPro_name());
			query.setParameter("from_hour", overtimeSearch.getFrom_hour());
			query.setParameter("to_hour", overtimeSearch.getTo_hour());
			query.setParameter("is_approved", is_approved);
			query.setReadOnly(true);
			query.setFirstResult((page - 1) * pageSize);
			query.setFetchSize(pageSize);
			query.setMaxResults(pageSize);
			List<Overtime> list = query.list();
			if (list.size() > pageSize) {
				return list = list.subList(0, pageSize);
			}
			session.close();
			return list;
		}else {
		String hql = "Select o from Overtime o INNER JOIN Employee AS e ON o.employee_id= e.employee_id INNER JOIN Project AS p ON o.project_id=p.project_id INNER JOIN ProjectManager pm ON (pm.employee_id=o.employee_id and pm.project_id=o.project_id  and pm.priority=o.status ) ";
		hql += "WHERE (:emp_name IS NULL OR e.fullname LIKE CONCAT('%', :emp_name, '%')) ";
		hql += "AND (:pro_name IS NULL OR p.name LIKE CONCAT('%', :pro_name, '%')) ";
		hql += "AND ((:from_hour IS NULL and ( :to_hour IS NOT NULL and (:to_hour>= o.to_hour) or (:to_hour < o.to_hour and :to_hour>=o.from_hour))) ";
		hql += "or (:to_hour IS NULL and (:from_hour IS NOT NULL and :from_hour <= o.to_hour)) ";
		hql += "or (:from_hour >= o.from_hour and :from_hour <= o.to_hour and :to_hour >= o.from_hour and :to_hour <= o.to_hour and :from_hour <= :to_hour) ";
		hql += "or (:from_hour <= o.from_hour and :to_hour >= o.from_hour and :to_hour <= o.to_hour and :from_hour <= :to_hour) ";
		hql += "or (:from_hour >= o.from_hour and :from_hour <= o.to_hour and :to_hour >= o.to_hour and :from_hour <= :to_hour) ";
		hql += "or (:from_hour <= o.from_hour and :to_hour >= o.to_hour and :from_hour <= :to_hour) ";
		hql += "or (:from_hour IS NULL and :to_hour IS NULL)) ";
		hql += "AND ((:is_approved IS NULL and o.is_approved IS NULL) or o.is_approved=:is_approved) and o.status in (:status) "; 
		hql += "AND (pm.manager_id=:manager_id) ";
		
		if (sortedColumn != null && desc != null) {
			String order = "";
			if (desc) {
				order = "desc";
			}
			hql += "ORDER BY " + sortedColumn + " " + order;
		}
		Query query = session.createQuery(hql);
		query.setParameter("manager_id", manager_id);
		query.setParameter("emp_name", overtimeSearch.getEmp_name());
		query.setParameter("pro_name", overtimeSearch.getPro_name());
		query.setParameter("from_hour", overtimeSearch.getFrom_hour());
		query.setParameter("to_hour", overtimeSearch.getTo_hour());
		query.setParameter("is_approved", is_approved);
		query.setParameterList("status",status);
		query.setReadOnly(true);
		query.setFirstResult((page - 1) * pageSize);
		query.setFetchSize(pageSize);
		query.setMaxResults(pageSize);
		List<Overtime> list = query.list();
		if (list.size() > pageSize) {
			return list = list.subList(0, pageSize);
		}
		session.close();
		return list;
		}

	}
	//a
	public Long CountSearchOvertimeManager(Long manager_id,Boolean is_approved,List<Integer> status,OvertimeSearch overtimeSearch) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		if(status.contains(-1)) {
			String hql = "Select count(o) from Overtime o INNER JOIN Employee AS e ON o.employee_id= e.employee_id INNER JOIN Project AS p ON o.project_id=p.project_id INNER JOIN ProjectManager pm ON (pm.employee_id=o.employee_id and pm.project_id=o.project_id and pm.priority=o.status) ";
			hql += "WHERE (:emp_name IS NULL OR e.fullname LIKE CONCAT('%', :emp_name, '%')) ";
			hql += "AND (:pro_name IS NULL OR p.name LIKE CONCAT('%', :pro_name, '%')) ";
			hql += "AND ((:from_hour IS NULL and ( :to_hour IS NOT NULL and (:to_hour>= o.to_hour) or (:to_hour < o.to_hour and :to_hour>=o.from_hour))) ";
			hql += "or (:to_hour IS NULL and (:from_hour IS NOT NULL and :from_hour <= o.to_hour)) ";
			hql += "or (:from_hour >= o.from_hour and :from_hour <= o.to_hour and :to_hour >= o.from_hour and :to_hour <= o.to_hour and :from_hour <= :to_hour) ";
			hql += "or (:from_hour <= o.from_hour and :to_hour >= o.from_hour and :to_hour <= o.to_hour and :from_hour <= :to_hour) ";
			hql += "or (:from_hour >= o.from_hour and :from_hour <= o.to_hour and :to_hour >= o.to_hour and :from_hour <= :to_hour) ";
			hql += "or (:from_hour <= o.from_hour and :to_hour >= o.to_hour and :from_hour <= :to_hour) ";
			hql += "or (:from_hour IS NULL and :to_hour IS NULL)) ";
			hql += "AND ((:is_approved IS NULL and o.is_approved IS NULL) or o.is_approved=:is_approved) and o.status =-1 "; 
			hql += "AND (pm.manager_id=:manager_id) ";
			Query query = session.createQuery(hql);
			query.setParameter("manager_id", manager_id);
			query.setParameter("emp_name", overtimeSearch.getEmp_name());
			query.setParameter("pro_name", overtimeSearch.getPro_name());
			query.setParameter("from_hour", overtimeSearch.getFrom_hour());
			query.setParameter("to_hour", overtimeSearch.getTo_hour());
			query.setParameter("is_approved", is_approved);
			query.setReadOnly(true);
			Long result=(Long) query.uniqueResult();
			session.close();
			return result;
		}else {
		String hql = "Select count(o) from Overtime o INNER JOIN Employee AS e ON o.employee_id= e.employee_id INNER JOIN Project AS p ON o.project_id=p.project_id INNER JOIN ProjectManager pm ON (pm.employee_id=o.employee_id and pm.project_id=o.project_id and pm.priority=o.status) ";
		hql += "WHERE (:emp_name IS NULL OR e.fullname LIKE CONCAT('%', :emp_name, '%')) ";
		hql += "AND (:pro_name IS NULL OR p.name LIKE CONCAT('%', :pro_name, '%')) ";
		hql += "AND ((:from_hour IS NULL and ( :to_hour IS NOT NULL and (:to_hour>= o.to_hour) or (:to_hour < o.to_hour and :to_hour>=o.from_hour))) ";
		hql += "or (:to_hour IS NULL and (:from_hour IS NOT NULL and :from_hour <= o.to_hour)) ";
		hql += "or (:from_hour >= o.from_hour and :from_hour <= o.to_hour and :to_hour >= o.from_hour and :to_hour <= o.to_hour and :from_hour <= :to_hour) ";
		hql += "or (:from_hour <= o.from_hour and :to_hour >= o.from_hour and :to_hour <= o.to_hour and :from_hour <= :to_hour) ";
		hql += "or (:from_hour >= o.from_hour and :from_hour <= o.to_hour and :to_hour >= o.to_hour and :from_hour <= :to_hour) ";
		hql += "or (:from_hour <= o.from_hour and :to_hour >= o.to_hour and :from_hour <= :to_hour) ";
		hql += "or (:from_hour IS NULL and :to_hour IS NULL)) ";
		hql += "AND ((:is_approved IS NULL and o.is_approved IS NULL) or o.is_approved=:is_approved) and o.status in (:status) "; 
		hql += "AND (pm.manager_id=:manager_id) ";
		Query query = session.createQuery(hql);
		query.setParameter("manager_id", manager_id);
		query.setParameter("emp_name", overtimeSearch.getEmp_name());
		query.setParameter("pro_name", overtimeSearch.getPro_name());
		query.setParameter("from_hour", overtimeSearch.getFrom_hour());
		query.setParameter("to_hour", overtimeSearch.getTo_hour());
		query.setParameter("is_approved", is_approved);
		query.setParameterList("status",status);
		query.setReadOnly(true);
		Long result=(Long) query.uniqueResult();
		session.close();
		return result;
		}
		
	}
	//a
	public List<Overtime> searchOvertimeEmployee(long employee_id,int page, int pageSize,String sortedColumn,Boolean desc,Boolean is_approved,List<Integer> status,OvertimeSearch overtimeSearch) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "Select o from Overtime o INNER JOIN Employee AS e ON o.employee_id= e.employee_id INNER JOIN Project AS p ON o.project_id=p.project_id ";
		hql += "WHERE (:pro_name IS NULL OR p.name LIKE CONCAT('%', :pro_name, '%')) ";
		hql += "AND ((:from_hour IS NULL and ( :to_hour  IS NOT NULL and (:to_hour  >= o.to_hour ) or (:to_hour <o.to_hour  and :to_hour >=o.from_hour ))) ";
		hql += "or (:to_hour  IS NULL and (:from_hour  IS NOT NULL and :from_hour  <= o.to_hour )) ";
		hql += "or (:from_hour  >= o.from_hour  and :from_hour  <= o.to_hour  and :to_hour  >= o.from_hour  and :to_hour  <= o.to_hour  and :from_hour  <= :to_hour ) ";
		hql += "or (:from_hour  <= o.from_hour  and :to_hour  >= o.from_hour  and :to_hour  <= o.to_hour  and :from_hour  <= :to_hour ) ";
		hql += "or (:from_hour  >= o.from_hour  and :from_hour  <= o.to_hour and :to_hour  >= o.to_hour  and :from_hour  <= :to_hour ) ";
		hql += "or (:from_hour  <= o.from_hour  and :to_hour >= o.to_hour  and :from_hour  <= :to_hour ) ";
		hql += "or (:from_hour  IS NULL and :to_hour IS NULL)) ";
		hql += "AND ((:is_approved IS NULL and o.is_approved IS NULL) or o.is_approved=:is_approved) and o.status in (:status) "; 
		hql += "AND (o.employee_id=:employee_id) ";
		if (sortedColumn != null && desc != null) {
			String order = "";
			if (desc) {
				order = "desc";
			}
			hql += "ORDER BY " + sortedColumn + " " + order;
		}
		Query query = session.createQuery(hql);
		query.setParameter("employee_id", employee_id);
		query.setParameter("pro_name", overtimeSearch.getPro_name());
		query.setParameter("from_hour", overtimeSearch.getFrom_hour());
		query.setParameter("to_hour", overtimeSearch.getTo_hour());
		query.setParameter("is_approved", is_approved);
		query.setParameterList("status",status);
		query.setFirstResult((page - 1) * pageSize);
		query.setFetchSize(pageSize);
		query.setMaxResults(pageSize);
		query.setReadOnly(true);
		List<Overtime> list = query.list();
		if (list.size() > pageSize) {
			return list = list.subList(0, pageSize);
		}
		session.close();
		return list;

	}
	//a
	public Long CountSearchManagerEmployee(Long employee_id,Boolean is_approved,List<Integer> status,OvertimeSearch overtimeSearch) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "Select count(o) from Overtime o INNER JOIN Employee AS e ON o.employee_id= e.employee_id INNER JOIN Project AS p ON o.project_id=p.project_id ";
		hql += "WHERE (:pro_name IS NULL OR p.name LIKE CONCAT('%', :pro_name, '%')) ";
		hql += "AND ((:from_hour IS NULL and ( :to_hour  IS NOT NULL and (:to_hour  >= o.to_hour ) or (:to_hour <o.to_hour  and :to_hour >=o.from_hour ))) ";
		hql += "or (:to_hour  IS NULL and (:from_hour  IS NOT NULL and :from_hour  <= o.to_hour )) ";
		hql += "or (:from_hour  >= o.from_hour  and :from_hour  <= o.to_hour  and :to_hour  >= o.from_hour  and :to_hour  <= o.to_hour  and :from_hour  <= :to_hour ) ";
		hql += "or (:from_hour  <= o.from_hour  and :to_hour  >= o.from_hour  and :to_hour  <= o.to_hour  and :from_hour  <= :to_hour ) ";
		hql += "or (:from_hour  >= o.from_hour  and :from_hour  <= o.to_hour and :to_hour  >= o.to_hour  and :from_hour  <= :to_hour ) ";
		hql += "or (:from_hour  <= o.from_hour  and :to_hour >= o.to_hour  and :from_hour  <= :to_hour ) ";
		hql += "or (:from_hour  IS NULL and :to_hour IS NULL)) ";
		hql += "AND ((:is_approved IS NULL and o.is_approved IS NULL) or o.is_approved=:is_approved) and o.status in (:status) "; 
		hql += "AND (o.employee_id=:employee_id) ";
		Query query = session.createQuery(hql);
		query.setParameter("employee_id", employee_id);
		query.setParameter("pro_name", overtimeSearch.getPro_name());
		query.setParameter("from_hour", overtimeSearch.getFrom_hour());
		query.setParameter("to_hour", overtimeSearch.getTo_hour());
		query.setParameter("is_approved", is_approved);
		query.setParameterList("status",status);
		query.setReadOnly(true);
		Long result=(Long) query.uniqueResult();
		session.close();
		return result;
	
	}
	//a
	public List<Long> countOvertimeByStatus(Long employee_id) {
		List<Long> result = new ArrayList<Long>();
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		// new
		String hql1 = "Select count(*) From Overtime o where o.employee_id=:employee_id and status=1 and is_approved IS NULL";
		Query query1 = session.createQuery(hql1);
		query1.setParameter("employee_id", employee_id);
		result.add((Long) query1.uniqueResult());
		// approving
		String hql2 = "Select count(*) From Overtime o where o.employee_id=:employee_id and status >= 1 and is_approved=false";
		Query query2 = session.createQuery(hql2);
		query2.setParameter("employee_id", employee_id);
		result.add((Long) query2.uniqueResult());
		// approved
		String hql3 = "Select count(*) From Overtime o where o.employee_id=:employee_id and status >= 1 and is_approved=true";
		Query query3 = session.createQuery(hql3);
		query3.setParameter("employee_id", employee_id);
		result.add((Long) query3.uniqueResult());
		// disapproved
		String hql4 = "Select count(*) From Overtime o where o.employee_id=:employee_id and status = -1 and is_approved=false";
		Query query4 = session.createQuery(hql4);
		query4.setParameter("employee_id", employee_id);
		result.add((Long) query4.uniqueResult());
		session.close();
		return result;
	}
	//a
	public List<Long> countOvertimeByStatusManager(Long manager_id) {
		List<Long> result = new ArrayList<Long>();
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		// need approve
		String hql1 = "Select count(o.next_approve_id) From Overtime_Log o where o.next_approve_id=:manager_id and o.approved_id=0 and o.disapproved_id=0";
		Query query1 = session.createQuery(hql1);
		query1.setParameter("manager_id", manager_id);
		result.add((Long) query1.uniqueResult());
		// approved
		String hql2 = "Select count(o.approved_id) From Overtime_Log o where o.approved_id=:manager_id";
		Query query2 = session.createQuery(hql2);
		query2.setParameter("manager_id", manager_id);
		result.add((Long) query2.uniqueResult());
		// disapproved
		String hql3 = "Select count(o.disapproved_id) From Overtime_Log o where o.disapproved_id=:manager_id";
		Query query3 = session.createQuery(hql3);
		query3.setParameter("manager_id", manager_id);
		result.add((Long) query3.uniqueResult());
		session.close();
		return result;
	}
	public Long countApprovedOvertimeByManager(long manager_id) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "Select count(o.overtime_log_id) From Overtime_Log o where o.approved_id=:manager_id ORDER BY o.overtime_log_id DESC";
		Query query = session.createQuery(hql);
		query.setParameter("manager_id", manager_id);
		Long result=(Long) query.uniqueResult();
		session.close();
		return result;
	}

	public Long countDisApprovedOvertimeByMng(long manager_id) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "Select count(o.overtime_log_id) From Overtime_Log o where o.disapproved_id=:manager_id ORDER BY o.overtime_log_id DESC";
		Query query = session.createQuery(hql);
		query.setParameter("manager_id", manager_id);
		Long result=(Long) query.uniqueResult();
		session.close();
		return result;
	}

	
	
	
}