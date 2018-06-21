package Com.IFI.InternalTool.DS.DAO.Impl;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
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
import Com.IFI.InternalTool.DS.DAO.ProjectManagerDAO;
import Com.IFI.InternalTool.DS.Model.Allocation;
import Com.IFI.InternalTool.DS.Model.AllocationDetail;
import Com.IFI.InternalTool.Utils.Business;

@Repository("AllocationDAO")
@Transactional
public class AllocationDAOImpl implements AllocationDAO {
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	private static final Logger logger = LoggerFactory.getLogger(AllocationDAOImpl.class);

	@Autowired
	AllocationDetailDAOImpl allocationDetailDAO;
	private boolean success = false;

	@Override
	public List<Allocation> getAllocations(final long employee_id, final int page, final int pageSize) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "FROM Allocation where employee_id= :employee_id ";

		Query query = session.createQuery(hql);
		query.setParameter("employee_id", employee_id);
		query.setFirstResult((page - 1) * pageSize);
		query.setMaxResults((page + 1) * pageSize - 1);
		List<Allocation> list = query.getResultList();
		session.close();
		return list;
	}

	@Override
	public List<Allocation> getAllocatedOfManager(final long employee_id, final int page, final int pageSize) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "Select a FROM Allocation a where a.project_id in (select pm.project_id from ProjectMembers pm where pm.manager_id = :employee_id)";
		Query query = session.createQuery(hql);
		query.setParameter("employee_id", employee_id);
		query.setFirstResult((page - 1) * pageSize);
		query.setMaxResults((page + 1) * pageSize - 1);
		List<Allocation> list = query.getResultList();
		session.close();
		return list;

	}

	@Override
	public Long NumRecordsAllocatedOfManager(long employee_id) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "Select count(*) FROM Allocation a where a.project_id in (select pm.project_id from ProjectMembers pm where pm.manager_id = :employee_id)";
		Query query = session.createQuery(hql);
		query.setParameter("employee_id", employee_id);
		Long count = (Long) query.uniqueResult();
		session.close();
		return count;
	}

	@Override
	public Boolean saveAllocation(final Allocation allocation) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(allocation);

			// // generic allocationDetail
			LocalDate start_date = allocation.getStart_date().toLocalDate();
			LocalDate end_date = allocation.getEnd_date().toLocalDate();
			while (start_date.isBefore(end_date) || start_date.equals(end_date)) {
				if ((start_date.getDayOfWeek() != DayOfWeek.SATURDAY
						&& start_date.getDayOfWeek() != DayOfWeek.SUNDAY)) {
					AllocationDetail a = new AllocationDetail();
					a.setAllocation_id(allocation.getAllocation_id());
					a.setEmployee_id(allocation.getEmployee_id());
					a.setDate((Date) (Date.valueOf(start_date)));
					a.setTime(8);
					allocationDetailDAO.saveAllocationDetail(a);
				}
				start_date = start_date.plusDays(1);
			}
			tx.commit();
			success = true;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return success;

	}

	@Override
	public Boolean updateAllocation(Allocation allocation) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		boolean success = false;
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Allocation currentAllocation = session.get(Allocation.class, allocation.getAllocation_id());
			// currentAllocation.setAllocation_plan(allocation.getAllocation_plan());
			currentAllocation.setEmployee_id(allocation.getEmployee_id());
			currentAllocation.setEnd_date(allocation.getEnd_date());
			currentAllocation.setMonth(allocation.getMonth());
			currentAllocation.setProject_id(allocation.getProject_id());
			currentAllocation.setStart_date(allocation.getStart_date());
			currentAllocation.setYear(allocation.getYear());
			//tinh lai allcation plan (copy code)		

			// get distance Time between start_date vs end_date not set Weekends;
			LocalDate start_date = currentAllocation.getStart_date().toLocalDate();
			LocalDate end_date = currentAllocation.getEnd_date().toLocalDate();			
			int distanceTime = Business.getDistanceTime(start_date, end_date);
			// get number days of month // get nums days weekend of month
			int numDaysOfMonth = currentAllocation.getStart_date().toLocalDate().lengthOfMonth();
			int numDaysWeekOfMonth = Business.numberWeekendOfMonth(start_date.getMonthValue(), start_date.getYear());
			// set allocation_plan
			double allocation_plan = Business.getAllocation_Plan(numDaysOfMonth, numDaysWeekOfMonth, distanceTime);			
			currentAllocation.setAllocation_plan(Business.getAllocation_Plan(numDaysOfMonth, numDaysWeekOfMonth, distanceTime));
			
			tx.commit();
			success = true;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return success;
	}

	@Override
	public Boolean deleteById(long allocation_id) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		// xoa cac detail allocation
		String hqlDeleteDetail = "delete from AllocationDetail where allocation_id = :allocation_id";
		Query queryDeleteDetail = session.createQuery(hqlDeleteDetail);
		queryDeleteDetail.setParameter("allocation_id", allocation_id);
		queryDeleteDetail.executeUpdate();
		// xoa allocation
		String hql = "Delete from Allocation where allocation_id=:allocation_id";
		Query query = session.createQuery(hql);
		query.setParameter("allocation_id", allocation_id);
		int row = query.executeUpdate();
		tx.commit();
		session.close();
		if (row > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Allocation findById(final long allocation_id) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "FROM Allocation where allocation_id=:allocation_id ";
		Query query = session.createQuery(hql);
		query.setParameter("allocation_id", allocation_id);
		Allocation allocation = (Allocation) query.uniqueResult();
		session.close();
		return allocation;
	}

	@Override
	public Date findMaxEndDate(long employee_id) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "SELECT MAX(a.end_date) FROM Allocation a where a.employee_id = :employee_id";
		Query query = session.createQuery(hql);
		query.setParameter("employee_id", employee_id);
		if (query.uniqueResult() != null) {
			return (Date) query.uniqueResult();
		}
		return null;
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
	public List<Allocation> findAllocationByEmployeeID(final long employee_id, final int page, final int pageSize, boolean isDESC) {

		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "select a FROM Allocation a where a.employee_id = :employee_id order by a.allocation_id ";
		if (isDESC) {
			hql += "desc";
		}else {
			hql += "asc";
		}
		Query query = session.createQuery(hql);
		query.setParameter("employee_id", employee_id);
		query.setFirstResult((page - 1) * pageSize);
		query.setMaxResults(pageSize);
		List<Allocation> list = query.getResultList();
		session.close();
		return list;

	}

	@Override
	public Long NumRecordsAllocationByEmployeeID(long employee_id) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "select count(*) FROM Allocation a where a.employee_id = :employee_id ";
		Query query = session.createQuery(hql);
		query.setParameter("employee_id", employee_id);
		Long count = (Long) query.uniqueResult();
		session.close();
		return count;
	}

	@Override
	public List<Allocation> findAllocationByProjectID(final long project_id, final int page, final int pageSize, boolean isDESC) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "select a FROM Allocation a where a.project_id = :project_id order by a.allocation_id ";
		if (isDESC) {
			hql += "desc";
		}else {
			hql += "asc";
		}
		Query query = session.createQuery(hql);
		query.setParameter("project_id", project_id);

		query.setFirstResult((page - 1) * pageSize);
		query.setMaxResults(pageSize);

		List<Allocation> list = query.getResultList();
		session.close();
		return list;
	}

	@Override
	public Long NumRecordsAllocationByProjectID(long project_id) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "select count(*) FROM Allocation a where a.project_id = :project_id";
		Query query = session.createQuery(hql);
		query.setParameter("project_id", project_id);
		Long count = (Long) query.uniqueResult();
		session.close();
		return count;
	}

	@Override
	public List<Allocation> findAllocationFromDateToDate(Date fromDate, Date toDate, final int page,
			final int pageSize) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "from Allocation where allocation_id in (select allocation_id from AllocationDetail where date between :fromDate and :toDate) order by allocation_id";
		Query query = session.createQuery(hql);
		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", fromDate);

		query.setFirstResult((page - 1) * pageSize);
		query.setMaxResults(pageSize);

		List<Allocation> list = query.getResultList();
		session.close();
		return list;
	}

	@Override
	public Long NumRecordsllocationFromDateToDate(Date fromDate, Date toDate) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "from Allocation where allocation_id in (select allocation_id from AllocationDetail where date between :fromDate and :toDate)";
		Query query = session.createQuery(hql);
		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", fromDate);
		Long count = (Long) query.uniqueResult();
		session.close();
		return count;
	}

	@Override
	public List<Allocation> searchAllocation(int year, int month, long project_id, long employee_id, int page,
			int pageSize) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "select a FROM Allocation a where a.year = :year";
		if (month >= 1 && month <= 12) {
			hql += " and a.month = :month ";
		}
		if (project_id > 0) {
			hql += " and a.project_id =: project_id";
		}
		if (employee_id > 0) {
			hql += " and a.employee_id =: employee_id";
		}
		Query query = session.createQuery(hql);
		query.setParameter("year", year);
		if (month >= 1 && month <= 12) {
			query.setParameter("month", month);
		}
		if (project_id > 0) {
			query.setParameter("project_id", project_id);
		}
		if (employee_id > 0) {
			query.setParameter("employee_id", employee_id);
		}
		query.setFirstResult((page - 1) * pageSize);
		query.setMaxResults(pageSize);
		List<Allocation> list = query.getResultList();
		session.close();
		return list;
	}

	@Override
	public Long NumRecordssearchAllocation(int year, int month, long project_id, long employee_id) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "select count(*) FROM Allocation where year = :year";
		if (month >= 1 && month <= 12) {
			hql += " and month = :month ";
		}
		if (project_id > 0) {
			hql += " and project_id =: project_id";
		}
		if (employee_id > 0) {
			hql += " and employee_id =: employee_id";
		}
		Query query = session.createQuery(hql);
		query.setParameter("year", year);
		if (month >= 1 && month <= 12) {
			query.setParameter("month", month);
		}
		if (project_id > 0) {
			query.setParameter("project_id", project_id);
		}
		if (employee_id > 0) {
			query.setParameter("employee_id", employee_id);
		}
		Long count = (Long) query.uniqueResult();
		session.close();
		return count;
	}

	@Override
	public Date findMaxEndDateInPoint(final long employee_id, final Date datePoint) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "SELECT MAX(a.end_date) FROM Allocation a where a.employee_id = :employee_id  and end_date < :datePoint";
		Query query = session.createQuery(hql);
		query.setParameter("employee_id", employee_id);
		if (query.uniqueResult() != null) {
			return (Date) query.uniqueResult();
		}
		return null;
	}

	@Override
	public Date findMinStartDateInPoint(long employee_id, Date datePoint) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "SELECT MIN(a.start_date) FROM Allocation a where a.employee_id = :employee_id  and start_date > :datePoint";
		Query query = session.createQuery(hql);
		query.setParameter("employee_id", employee_id);
		if (query.uniqueResult() != null) {
			return (Date) query.uniqueResult();
		}
		return null;
	}

	@Override
	public List<Allocation> findAllocationByEmpIdProId(long employeeId, long projectId) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "from Allocation where employee_id = :employee_id and project_id = :project_id";
		Query query = session.createQuery(hql);
		query.setParameter("employee_id", employeeId);
		query.setParameter("project_id", projectId);
		List<Allocation> result = query.getResultList();
		session.close();
		return result;
	}

}
