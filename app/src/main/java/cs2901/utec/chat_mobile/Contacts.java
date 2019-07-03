package cs2901.utec.chat_mobile;

public class Contacts {
    public String fullname;
    public int id;
    public String name;
    public String password;
    public String username;

    public Contacts(){
    }

    public Contacts(String fullname, int id, String name, String password, String username) {
        this.fullname = fullname;
        this.id = id;
        this.name = name;
        this.password = password;
        this.username = username;
    }

    // Getters and Setters methods
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
