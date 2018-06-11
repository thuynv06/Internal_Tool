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

import Com.IFI.InternalTool.DS.DAO.ProjectMembersDAO;
import Com.IFI.InternalTool.DS.Model.Allocation;
import Com.IFI.InternalTool.DS.Model.ProjectMembers;

@Repository("ProjectMembersDAO")
@Transactional
public class ProjectMembersDAOImpl implements ProjectMembersDAO {
	private boolean success = false;

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Autowired
	private AllocationDAOImpl allocationDAO;

	@Override
	public Boolean IsMembersOfProject(long employee_id, final long project_id) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = " From ProjectMembers where employee_id=:employee_id and project_id=:project_id";
		Query query = session.createQuery(hql);
		query.setParameter("employee_id", employee_id);
		query.setParameter("project_id", project_id);
		try {
			query.getSingleResult();
			success = true;
		} catch (Exception e) {
			return false;
		} finally {
			session.close();
		}
		return success;
	}

	@Override
	public Boolean AddMemberToProject(ProjectMembers projectMember) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		if (IsMembersOfProject(projectMember.getEmployee_id(), projectMember.getProject_id())) {
			return false;
		}

		// projectMember.setPriority(employeeDAO.getEmployeeById(projectMember.getEmployee_id()).getType_id());
		try {
			session.saveOrUpdate(projectMember);
			success = true;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return success;
	}

	@Override
	public Boolean RemoveMemberOfProject(final long project_id, final long employee_id) {

		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		String hql = "Delete from ProjectMembers where employee_id=:employee_id and project_id =: project_id";
		Query query = session.createQuery(hql);
		query.setParameter("project_id", project_id);
		query.setParameter("employee_id", employee_id);
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
	public List<Long> ListEmPloyeesIdInProject(long project_id) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "Select distinct pm.employee_id From ProjectMembers pm where pm.project_id =: project_id";
		Query query = session.createQuery(hql);
		query.setParameter("project_id", project_id);
		return query.getResultList();
	}

}
