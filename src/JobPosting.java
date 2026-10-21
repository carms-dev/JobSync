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

    // =========================
    // GETTERS
    // =========================

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

    // Status text used in jobs.txt and on screen
    public String getStatus() {
        return open ? "OPEN" : "CLOSED";
    }

    // =========================
    // SETTERS
    // =========================

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

    // Used when a job is loaded back from jobs.txt
    public void setOpen(boolean open) {
        this.open = open;
    }

    public void closeJob() {
        this.open = false;
    }

    // =========================
    // ABSTRACT METHODS
    // Every job type must define its own version (polymorphism)
    // =========================

    public abstract String getJobType();

    public abstract String getBenefits();

    public abstract String getSalaryDescription();

    public abstract double computeMonthlySalary();

    public abstract double getHoursPerMonth();

    public abstract void setHoursPerMonth(double hoursPerMonth);

    // =========================
    // DISPLAY
    // =========================

    // Same code works for any job type: the calls to getJobType(),
    // getBenefits(), etc. run the version of the actual object.
    public String getDetails() {
        return "Job ID: " + jobId + "\n"
                + "Title: " + title + "\n"
                + "Job Type: " + getJobType() + "\n"
                + "Location: " + location + "\n"
                + "Pay: " + getSalaryDescription() + "\n"
                + "Hours per Month: " + formatHours(getHoursPerMonth()) + "\n"
                + "Estimated Monthly Pay: " + formatMoney(computeMonthlySalary()) + "\n"
                + "Benefits: " + getBenefits() + "\n"
                + "Description: " + description + "\n"
                + "Required Skills: " + requiredSkills + "\n"
                + "Status: " + getStatus();
    }

    public void displayJob() {
        System.out.println("\n===== JOB DETAILS =====");
        System.out.println(getDetails());
    }

    // =========================
    // FILE RECORD (jobs.txt)
    // Format: id|employerId|title|location|salary|description|skills|status|type|hours
    // =========================

    public String toFileString() {
        return jobId + "|"
                + employerId + "|"
                + clean(title) + "|"
                + clean(location) + "|"
                + salary + "|"
                + clean(description) + "|"
                + clean(requiredSkills) + "|"
                + getStatus() + "|"
                + getJobType() + "|"
                + getHoursPerMonth();
    }

    // =========================
    // HELPERS
    // =========================

    protected static String formatMoney(double amount) {
        return "PHP " + String.format("%,.2f", amount);
    }

    protected static String formatHours(double hours) {
        if (hours == Math.floor(hours)) {
            return String.valueOf((long) hours);
        }
        return String.format("%.1f", hours);
    }

    // "|" separates fields in the .txt file, so it cannot appear inside text
    private static String clean(String text) {
        if (text == null) {
            return "";
        }

        return text
                .replace("|", "/")
                .replace("\n", " ")
                .replace("\r", " ");
    }
}