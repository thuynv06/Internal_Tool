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

import Com.IFI.InternalTool.DS.DAO.EmployeeDAO;
import Com.IFI.InternalTool.DS.Model.Employee;
import Com.IFI.InternalTool.DS.Model.Group_ifi;
@Repository("EmployeeDAO")
@Transactional
public class EmployeeDAOImpl implements EmployeeDAO{
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	@Override
	public List<Employee> getAllEmployee() {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "FROM Employee";
		Query query = session.createQuery(hql);
		//query.setParameter("userId", userId);
		List<Employee> list = query.list();
		session.close();
		return list;
	}
	//save or update
	@Override
	public boolean saveEmployee(Employee employee) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Transaction tx = null;
		tx=session.beginTransaction();
		session.saveOrUpdate(employee);
		tx.commit();
		session.close();
		return true;
	}
	@Override
	public boolean deleteEmployee(long employee_id) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Transaction tx = null;
		tx=session.beginTransaction();
		String hql = "Delete from Employee where employee_id=:employee_id";
		Query query = session.createQuery(hql);
		query.setParameter("employee_id", employee_id);
		query.executeUpdate();
		tx.commit();
		session.close();
		return true;
	}
	@Override
	public Employee getEmployeeById(long employee_id) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "FROM Employee where employee_id=:employee_id";
		Query query = session.createQuery(hql);
		query.setParameter("employee_id", employee_id);
		Employee emp = (Employee) query.uniqueResult();
		session.close();
		return emp;
	}
	@Override
	public List<Group_ifi> getAllGroup() {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "FROM Group_ifi";
		Query query = session.createQuery(hql);
		//query.setParameter("userId", userId);
		List<Group_ifi> list = query.list();
		session.close();
		return list;
	}
	
	

}
