public class Application {

    private int applicationId;
    private int applicantId;
    private int jobId;
    private String applicationDate;
    private String status;

    public Application(int applicationId, int applicantId, int jobId,
                       String applicationDate, String status) {

        this.applicationId = applicationId;
        this.applicantId = applicantId;
        this.jobId = jobId;
        this.applicationDate = applicationDate;
        this.status = status;
    }

    // =========================
    // GETTERS
    // =========================

    public int getApplicationId() {
        return applicationId;
    }

    public int getApplicantId() {
        return applicantId;
    }

    public int getJobId() {
        return jobId;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public String getStatus() {
        return status;
    }

    // =========================
    // SETTERS
    // =========================

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    // =========================
    // APPLICATION STATUS
    // =========================

    public void setStatus(String status) {

        if (status.equals("PENDING")
                || status.equals("ACCEPTED")
                || status.equals("REJECTED")
                || status.equals("INTERVIEW")
                || status.equals("WITHDRAWN")) {

            this.status = status;

        } else {

            System.out.println("Invalid application status.");
        }
    }

    // =========================
    // STATUS CHECKING
    // =========================

    public boolean isPending() {
        return status.equals("PENDING");
    }

    public boolean isAccepted() {
        return status.equals("ACCEPTED");
    }

    public boolean isRejected() {
        return status.equals("REJECTED");
    }

    public boolean isWithdrawn() {
        return status.equals("WITHDRAWN");
    }

    public boolean canWithdraw() {

        return status.equals("PENDING");
    }

    // =========================
    // DISPLAY
    // =========================

    public void displayApplication() {

        System.out.println("\n===== APPLICATION =====");

        System.out.println("Application ID: " + applicationId);
        System.out.println("Applicant ID: " + applicantId);
        System.out.println("Job ID: " + jobId);
        System.out.println("Application Date: " + applicationDate);
        System.out.println("Status: " + status);
    }
}