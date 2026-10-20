public class Employer extends User {

    public Employer(int userId, String username, String password, String fullName) {
        super(userId, username, password, fullName);
    }

    public void postJob() {
        System.out.println("Posting a new job...");
    }

    public void reviewApplication() {
        System.out.println("Reviewing applications...");
    }

    public void scheduleInterview() {
        System.out.println("Scheduling an interview...");
    }

    @Override
    public void displayMenu() {
        System.out.println("\n===== EMPLOYER MENU =====");
        System.out.println("1. Post Job");
        System.out.println("2. View My Jobs");
        System.out.println("3. Update Job");
        System.out.println("4. Close Job");
        System.out.println("5. View Applications");
        System.out.println("6. Review Application");
        System.out.println("7. Schedule Interview");
        System.out.println("8. Logout");
    }
}