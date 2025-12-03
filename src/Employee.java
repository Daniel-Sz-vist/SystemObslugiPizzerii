public class Employee extends Person {
    private Role role;


    public Employee(String name, Role role) {
        super(name);
        this.role = role;
    }


    public Role getRoleEnum() {
        return role;
    }


    @Override
    public String getRole() {
        return role.name();
    }


    @Override
    public String toString() {
        return "Employee{name='" + getName() + "', role=" + role + "}";
    }
}