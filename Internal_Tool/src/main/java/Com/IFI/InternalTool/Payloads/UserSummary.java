package Com.IFI.InternalTool.Payloads;

public class UserSummary {
    private Long employee_id;
    private String username;
    private String name;

    public UserSummary(Long id, String username, String name) {
        this.employee_id = id;
        this.username = username;
        this.name = name;
    }

    public Long getId() {
        return employee_id;
    }

    public void setId(Long id) {
        this.employee_id= id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}