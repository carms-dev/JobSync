public abstract class JobPosting {
    private int jobId;
    private int employerId;
    private String title;
    private String location;
    private double salary;
    private String description;
    private String requiredSkills;
    private boolean open;

    public JobPosting(int jobId, int employerId, String title,
                      String location, double salary,
                      String description, String requiredSkills) {
        this.jobId = jobId;
        this.employerId = employerId;
        this.title = title;
        this.location = location;
        this.salary = salary;
        this.description = description;
        this.requiredSkills = requiredSkills;
        this.open = true;
    }

    public int getJobId() {
        return jobId;
    }

    public int getEmployerId() {
        return employerId;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public double getSalary() {
        return salary;
    }

    public String getDescription() {
        return description;
    }

    public String getRequiredSkills() {
        return requiredSkills;
    }

    public boolean isOpen() {
        return open;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRequiredSkills(String requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public void closeJob() {
        this.open = false;
    }

    public void displayJob() {
        System.out.println("\n===== JOB DETAILS =====");
        System.out.println("Job ID: " + jobId);
        System.out.println("Title: " + title);
        System.out.println("Location: " + location);
        System.out.println("Salary: " + salary);
        System.out.println("Description: " + description);
        System.out.println("Required Skills: " + requiredSkills);
        System.out.println("Status: " + (open ? "OPEN" : "CLOSED"));
    }

    public abstract String getBenefits();

    public abstract double computeMonthlySalary();
}