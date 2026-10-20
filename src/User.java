public abstract class User {
    private int userId;
    private String username;
    private String password;
    private String fullName;

    public User(int userId, String username, String password, String fullName) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean login(String username, String password) {
        return this.username.equals(username)
                && this.password.equals(password);
    }

    public abstract void displayMenu();
}