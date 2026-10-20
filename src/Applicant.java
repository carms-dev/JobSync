public class Applicant extends User {
    private Resume resume;

    public Applicant(int userId, String username, String password, String fullName) {
        super(userId, username, password, fullName);
    }

    public Resume getResume() {
        return resume;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }

    public void searchJob() {
        System.out.println("Searching for available jobs...");
    }

    public void applyForJob() {
        System.out.println("Applying for a job...");
    }

    public void viewApplication() {
        System.out.println("Viewing applications...");
    }

    @Override
    public void displayMenu() {
        System.out.println("\n===== APPLICANT MENU =====");
        System.out.println("1. Update Profile");
        System.out.println("2. Manage Resume");
        System.out.println("3. Search Jobs");
        System.out.println("4. Apply for Job");
        System.out.println("5. View Applications");
        System.out.println("6. View Interviews");
        System.out.println("7. Logout");
    }
}