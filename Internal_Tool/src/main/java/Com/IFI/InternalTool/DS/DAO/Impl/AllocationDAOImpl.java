package Com.IFI.InternalTool.DS.DAO.Impl;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import Com.IFI.InternalTool.DS.DAO.AllocationDAO;
import Com.IFI.InternalTool.DS.Model.Allocation;
import Com.IFI.InternalTool.Payloads.AllocationResponse;
import Com.IFI.InternalTool.Payloads.PagedResponse;

@Repository("AllocationDAO")
@Transactional
public class AllocationDAOImpl implements AllocationDAO {
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	private static final Logger logger = LoggerFactory.getLogger(AllocationDAOImpl.class);

	@Override
	public Date findMaxEndDate(long employee_id) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "SELECT MAX(a.end_date) FROM Allocation a where a.employee_id = :employee_id";
		Query query = session.createQuery(hql);
		query.setParameter("employee_id", employee_id);
		// Date maxEndDate=
		// logger.info(maxEndDate + "max_end_date" );
		return (Date) query.uniqueResult();
	}

	@Override
	public List<Allocation> getAllAllocation(int page, int pageSize, Boolean desc) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "FROM Allocation ";
		if (desc != null) {
			String order = "";
			if (desc) {
				order = "desc";
			}
			hql += order;
		}
		Query query = session.createQuery(hql);
		query.setFirstResult((page - 1) * pageSize);
		query.setFetchSize(pageSize);
		query.setMaxResults(pageSize);
		List<Allocation> list = query.getResultList();
		if (list.size() > pageSize) {
			return list = list.subList(0, pageSize);
		}
		session.close();
		return list;
	}

	@Override
	public PagedResponse<AllocationResponse> getAllocation(int page, int pageSize, Boolean desc) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "select a.allocation_id,a.employee_id, e.fullname, a.project_id,p.name,a.allocation_plan, a.start_date,a.end_date from Allocation a, Employee e, Project p where a.employee_id= e.employee_id and p.project_id = a.project_id";
	
		Query query = session.createQuery(hql);
		query.setFirstResult((page - 1) * pageSize);
		query.setFetchSize(pageSize);
		query.setMaxResults(pageSize);
		List<AllocationResponse> list = query.getResultList();
		if (list.size() > pageSize) {
			return new PagedResponse<>(list.subList(0, pageSize));
		}
		logger.info(list+ " ");
		session.close();
		return new PagedResponse(list.subList(0, pageSize));
	}

	@Override
	public boolean saveAllocation(Allocation allocation) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		session.saveOrUpdate(allocation);
		tx.commit();
		session.close();
		return true;
	}

	@Override
	public boolean deleteAlocation(long allocation_id) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		String hql = "Delete from Allocation where allocation_id=:allocation_id";
		Query query = session.createQuery(hql);
		query.setParameter("allocation_id", allocation_id);
		query.executeUpdate();
		tx.commit();
		session.close();
		return true;
	}

	@Override
	public Allocation getAllocationById(long allocation_id) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "FROM Allocation where allocation_id=:allocation_id ";
		Query query = session.createQuery(hql);
		query.setParameter("allocation_id", allocation_id);
		Allocation allocation = (Allocation) query.uniqueResult();
		session.close();
		return allocation;
	}

}
