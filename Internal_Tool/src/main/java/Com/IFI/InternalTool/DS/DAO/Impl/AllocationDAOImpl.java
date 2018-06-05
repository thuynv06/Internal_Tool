package Com.IFI.InternalTool.DS.DAO.Impl;

import java.sql.Date;
import java.time.LocalDate;
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
	public List<Allocation> getAllocations(int page, int pageSize) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "FROM Allocation ";
		Query query = session.createQuery(hql);
		query.setFirstResult((page - 1) * pageSize);
		query.setMaxResults((page+1)*pageSize -1);
		List<Allocation> list = query.getResultList();
		session.close();
		return list;
	}

	// @Override
	// public PagedResponse<AllocationResponse> getAllocation1(int page, int
	// pageSize, Boolean desc) {
	// Session session =
	// entityManagerFactory.unwrap(SessionFactory.class).openSession();
	// String hql = "select a.allocation_id,a.employee_id, e.fullname,
	// a.project_id,p.name,a.allocation_plan, a.start_date,a.end_date from
	// Allocation a, Employee e, Project p where a.employee_id= e.employee_id and
	// p.project_id = a.project_id";
	//
	// Query query = session.createQuery(hql);
	// query.setFirstResult((page - 1) * pageSize);
	// query.setFetchSize(pageSize);
	// query.setMaxResults(pageSize);
	// List<AllocationResponse> list = query.getResultList();
	// if (list.size() > pageSize) {
	// return new PagedResponse<>(list.subList(0, pageSize));
	// }
	// logger.info(list+ " ");
	// session.close();
	// return new PagedResponse(list.subList(0, pageSize));
	// }

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
	public boolean deleteById(long allocation_id) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "Delete from Allocation where allocation_id=:allocation_id";
		Query query = session.createQuery(hql);
		query.setParameter("allocation_id", allocation_id);
		query.executeUpdate();
		session.close();
		return true;
	}

	@Override
	public Allocation findById(long allocation_id) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "FROM Allocation where allocation_id=:allocation_id ";
		Query query = session.createQuery(hql);
		query.setParameter("allocation_id", allocation_id);
		Allocation allocation = (Allocation) query.uniqueResult();
		session.close();
		return allocation;
	}

	@Override
	public LocalDate findMaxEndDate(long employee_id) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "SELECT MAX(a.end_date) FROM Allocation a where a.employee_id = :employee_id";
		Query query = session.createQuery(hql);
		query.setParameter("employee_id", employee_id);
		// Date maxEndDate=
		// logger.info(maxEndDate + "max_end_date" );
		return (LocalDate) query.uniqueResult();
	}

	public List<Allocation> searchAllocationWithTime(final int year, final int month, final int page,
			final int pageSize) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "select a FROM Allocation a where ";

		if (year > 0) {
			hql += "a.year = :year";
			if (month >= 1 && month <= 12) {
				hql += " and a.month = :month ";
			}
		} else {
			if (month >= 1 && month <= 12) {
				hql += "a.month = :month ";
			}
		}

		Query query = session.createQuery(hql);

		if (year > 0) {
			query.setParameter("year", year);
		}
		if (month >= 1 && month <= 12) {
			query.setParameter("month", month);
		}

		query.setFirstResult((page - 1) * pageSize);
		query.setMaxResults(pageSize);

		List<Allocation> list = query.getResultList();
		session.close();
		return list;
	}

	@Override
	public List<Allocation> findAllocationByEmployeeID(final long employee_id, final int page, final int pageSize) {

		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "select a FROM Allocation a where a.employee_id = :employee_id ";
		Query query = session.createQuery(hql);
		query.setParameter("employee_id", employee_id);
		query.setFirstResult((page - 1) * pageSize);
		query.setMaxResults(pageSize);
		List<Allocation> list = query.getResultList();
		session.close();
		return list;

	}

	@Override
	public List<Allocation> findAllocationByProjectID(long project_id, int page, int pageSize) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "select a FROM Allocation a where a.project_id = :project_id";
		Query query = session.createQuery(hql);
		query.setParameter("project_id", project_id);

		query.setFirstResult((page - 1) * pageSize);
		query.setMaxResults(pageSize);

		List<Allocation> list = query.getResultList();
		session.close();
		return list;
	}
	
	

}
