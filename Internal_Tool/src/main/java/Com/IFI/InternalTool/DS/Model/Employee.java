package Com.IFI.InternalTool.DS.Model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;



@Entity
@Table(name="employee",uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
            })
})
public class Employee  {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long employee_id;
	
	@NotBlank
    @Size(max = 15)
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name="is_active")
	private boolean is_actice;
	@Column(name="code")
	private String code;
	@Column (name="group_id")
	private String group_id;
	@Column(name = "fullname")
	private String fullname;
	@Column(name = "address")
	private String address;
	@Column(name="age")
	private int age;
	@Column(name="email")
	@Email
	private String email;
	@Column(name = "phone")
	private String phone;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles= new HashSet<>();
	
	
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public Employee() {
		
	}
	 public Employee(String name, String username, String email, String password) {
	        this.fullname = name;
	        this.username = username;
	        this.email = email;
	        this.password = password;
	    }
	//setter getter
	

	public String getUsername() {
		return username;
	}


	public Long getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(Long employee_id) {
		this.employee_id = employee_id;
	}
	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public boolean isIs_actice() {
		return is_actice;
	}


	public void setIs_actice(boolean is_actice) {
		this.is_actice = is_actice;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getGroup_id() {
		return group_id;
	}


	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}


	public String getFullname() {
		return fullname;
	}


	public void setFullname(String fullname) {
		this.fullname = fullname;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}
	

	
}
