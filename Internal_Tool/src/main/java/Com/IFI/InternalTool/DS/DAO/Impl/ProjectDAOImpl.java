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
import Com.IFI.InternalTool.DS.Model.Project;
import Com.IFI.InternalTool.DS.Model.ProjectManager;
@Repository("ProjectDAO")
@Transactional
public class ProjectDAOImpl implements ProjectDAO {
	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Override
	public List<Project> getAllProject(int page,int pageSize,String sortedColumn,Boolean desc) {
		
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "FROM Project ";
		if(sortedColumn != null && desc != null){
			String order = "";
			if(desc){
				order = "desc";
			}
			hql +="ORDER BY "+ sortedColumn + " " +  order;
		}
		Query query = session.createQuery(hql);
		query.setFirstResult((page-1)*pageSize);
		query.setMaxResults(pageSize);
		List<Project> list = query.getResultList();
		session.close();
		return list;
	}
	
	@Override
	public void saveProject(Project project) {
		
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		session.saveOrUpdate(project);
	
	}

	@Override
	public boolean deleteProject(long project_id) {
		
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Transaction tx = null;
		tx=session.beginTransaction();
		String hql = "Delete from Project where project_id=:project_id";
		Query query = session.createQuery(hql);
		
		String hql1 = "Delete from Project where project_id=:project_id";
		Query query = session.createQuery(hql);
		query.setParameter("project_id", project_id);
		query.executeUpdate();
		tx.commit();
		session.close();
		return true;
	}

	@Override
	public Project getProjectById(long project_id) {
		
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "FROM Project where project_id=:project_id and status=true";
		Query query = session.createQuery(hql);
		query.setParameter("project_id", project_id);
		Project pro = (Project) query.uniqueResult();
		session.close();
		return pro;
	}

	@Override
	public List<ProjectManager> getProjectManagerByEmp(long employee_id,long project_id) {
		
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "Select distinct p FROM Project_manager p LEFT JOIN Vacation AS v ON p.employee_id=v.employee_id where p.employee_id=:employee_id and p.project_id=:project_id";
		Query query = session.createQuery(hql);
		query.setParameter("employee_id", employee_id);
		query.setParameter("project_id", project_id);
		List<ProjectManager> list=query.list();
		return list;
	}
	
	@Override
	public List<Long> getProjectByEmp(long employee_id) {
		
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "Select distinct p.project_id FROM Project_manager p LEFT JOIN Vacation AS v ON p.employee_id=v.employee_id where p.employee_id=:employee_id";
		Query query = session.createQuery(hql);
		query.setParameter("employee_id", employee_id);
		List<Long> list=query.list();
		return list;
	}
	
	// get list projects of group
	@Override
	public List<Project> getProjectsOfGroup(String group_id) {
		
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "FROM Project where group_id = :group_id";
		Query query = session.createQuery(hql);
		query.setParameter("group_id", group_id);

		List<Project> list = query.list();
		session.close();
		return list;
	}

	@Override
	public List<Project> findProjectNameLike(String projectName) {
		
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "FROM Project where name Like %:projectName%";
		Query query = session.createQuery(hql);
		query.setParameter("projectName", projectName);
		List<Project> list = query.list();
		session.close();
		return list;
	}

	
	
	
	

}