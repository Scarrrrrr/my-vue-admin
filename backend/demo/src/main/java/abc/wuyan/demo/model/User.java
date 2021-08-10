package abc.wuyan.demo.model;

public class User {
    private Integer uid;
    private String name;
    private String password;
    private String role;
    private String sex;
    private String email;
    public User(int uid, String name, String password){
        this.uid = uid;
        this.name = name;
        this.password = password;
    }
    public User(){

    }
    public Integer getId(){ return this.uid;}

    public void setId(int uid) {this.uid = uid;}

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole(){
        return this.role;
    }

    public void setRole(String role){
        this.role = role;
    }

    public String getSex(){
        return this.sex;
    }

    public void setSex(String sex){
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
