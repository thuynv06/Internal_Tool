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

import Com.IFI.InternalTool.DS.DAO.ProjectDAO;
import Com.IFI.InternalTool.DS.Model.Allocation;
import Com.IFI.InternalTool.DS.Model.Project;
import Com.IFI.InternalTool.DS.Model.ProjectManager;

@Repository("ProjectDAO")
@Transactional
public class ProjectDAOImpl implements ProjectDAO {
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	@Autowired
	private AllocationDAOImpl allocationDaoImpl;

	@Override
	public List<Project> getAllProject(int page, int pageSize) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "FROM Project WHERE status = 1";
		Query query = session.createQuery(hql);
		query.setFirstResult((page - 1) * pageSize);
		query.setMaxResults(pageSize);
		List<Project> list = query.getResultList();
		session.close();
		return list;
	}

	@Override
	public boolean saveProject(Project project) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		boolean success = false;
		try {
			session.saveOrUpdate(project);
			success = true;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return success;
	}

	@Override
	public boolean deleteProject(long project_id) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();

		Transaction tx = null;
		tx = session.beginTransaction();
		// xoa allocation cua project do truoc
		for (Allocation allocation : allocationDaoImpl.findAllocationByProjectID(project_id, 1, Integer.MAX_VALUE)) {
			allocationDaoImpl.deleteById(allocation.getAllocation_id());
		}
		// xoa cac project manager

		String hql = "Delete from Project where project_id=:project_id";
		Query query = session.createQuery(hql);
		query.setParameter("project_id", project_id);
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
	public Project getProjectById(long project_id) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "FROM Project where project_id=:project_id and status = 1";
		Query query = session.createQuery(hql);
		query.setParameter("project_id", project_id);
		Project pro = (Project) query.uniqueResult();
		session.close();
		return pro;
	}

	@Override
	public List<ProjectManager> getProjectManagerByEmp(long employee_id, long project_id) {

		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "Select distinct p FROM Project_manager p LEFT JOIN Vacation AS v ON p.employee_id=v.employee_id where p.employee_id=:employee_id and p.project_id=:project_id";
		Query query = session.createQuery(hql);
		query.setParameter("employee_id", employee_id);
		query.setParameter("project_id", project_id);
		List<ProjectManager> list = query.list();
		return list;
	}

	@Override
	public List<Long> getProjectByEmp(long employee_id) {

		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "Select distinct p.project_id FROM Project_manager p LEFT JOIN Vacation AS v ON p.employee_id=v.employee_id where p.employee_id=:employee_id";
		Query query = session.createQuery(hql);
		query.setParameter("employee_id", employee_id);
		List<Long> list = query.list();
		return list;

	}

	@Override
	public List<Project> getProjectsOfGroup(String group_id, int page, int pageSize) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "FROM Project where group_id = :group_id and status = 1";
		Query query = session.createQuery(hql);
		query.setParameter("group_id", group_id);
		query.setFirstResult((page - 1) * pageSize);
		query.setMaxResults(pageSize);
		List<Project> list = query.getResultList();
		session.close();
		return list;
	}

	@Override
	public List<Project> findProjectNameLike(String projectName, int page, int pageSize) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "FROM Project where name Like :projectName";
		Query query = session.createQuery(hql);
		query.setParameter("projectName", "%" + projectName + "%");
		query.setFirstResult((page - 1) * pageSize);
		query.setMaxResults(pageSize);
		List<Project> list = query.getResultList();
		session.close();
		return list;
	}

	@Override
	public Long getBigestManagerId(long project_id) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "select employee_id FROM ProjectManager where project_id = :project_id and priority = (select max(priority) FROM ProjectManager where project_id = :project_id)";
		Query query = session.createQuery(hql);
		query.setParameter("project_id", project_id);
		Long result = (Long) query.uniqueResult();
		session.close();
		return result;
	}

	@Override
	public List<Long> getListEmployeeId(long project_id, int page, int pageSize) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "select distinct employee_id FROM ProjectManager where project_id = :project_id";
		Query query = session.createQuery(hql);
		query.setParameter("project_id", project_id);
		query.setFirstResult((page - 1) * pageSize);
		query.setMaxResults(pageSize);
		List<Long> list = query.getResultList();
		session.close();
		return list;
	}

	@Override
	public boolean updateProject(Project project) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		boolean success = false;
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Project currentProject = session.get(Project.class, project.getProject_id());
			currentProject.setDescription(project.getDescription());
			currentProject.setEnd_date(project.getEnd_date());
			currentProject.setGroup_id(project.getGroup_id());
			currentProject.setMonth(project.getMonth());
			currentProject.setName(project.getName());
			currentProject.setStart_date(project.getStart_date());
			currentProject.setStatus(project.isStatus());
			currentProject.setYear(project.getYear());
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
	public List<Project> getListProjectOutOfDate(int page, int pageSize) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "FROM Project where status = 0";
		Query query = session.createQuery(hql);
		query.setFirstResult((page - 1) * pageSize);
		query.setMaxResults(pageSize);
		List<Project> list = query.getResultList();
		session.close();
		return list;
	}

	@Override
	public List<Project> getProjectByMonthYear(int month, int year, int page, int pageSize) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "FROM Project where status = 1 and year = :year";
		if (month >= 1 && month <= 12) {
			hql += " and month = :month";
		}
		Query query = session.createQuery(hql);
		query.setParameter("year", year);
		if (month >= 1 && month <= 12) {
			query.setParameter("month", month);
		}
		query.setFirstResult((page - 1) * pageSize);
		query.setMaxResults(pageSize);
		List<Project> list = query.getResultList();
		session.close();
		return list;
	}

	
	@Override
	public List<Project> getProjectAllocatedIn(long employee_id, int page, int pageSize) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "from project where project_id in (select distinct project_id from allocation where employee_id = :employee_id)";
		Query query = session.createQuery(hql);
		query.setParameter("employee_id", employee_id);
		query.setFirstResult((page - 1) * pageSize);
		query.setMaxResults(pageSize);
		List<Project> list = query.getResultList();
		session.close();
		return list;
	}

	
	@Override
	public List<Project> getProjectAllocateTo(long employee_id, int page, int pageSize) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "from project where project_id in (select distinct project_id from allocation where manager_id = :employee_id)";
		Query query = session.createQuery(hql);
		query.setParameter("employee_id", employee_id);
		query.setFirstResult((page - 1) * pageSize);
		query.setMaxResults(pageSize);
		List<Project> list = query.getResultList();
		session.close();
		return list;
	}

}