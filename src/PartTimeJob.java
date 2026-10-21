public class PartTimeJob extends JobPosting {
    private double hoursPerMonth;

    public PartTimeJob(int jobId, int employerId, String title,
                       String location, double salary,
                       String description, String requiredSkills,
                       double hoursPerMonth) {

        super(jobId, employerId, title, location, salary,
              description, requiredSkills);

        this.hoursPerMonth = hoursPerMonth;
    }

    @Override
    public double getHoursPerMonth() {
        return hoursPerMonth;
    }

    @Override
    public void setHoursPerMonth(double hoursPerMonth) {
        this.hoursPerMonth = hoursPerMonth;
    }

    @Override
    public String getJobType() {
        return "PART-TIME";
    }

    @Override
    public String getBenefits() {
        return "Flexible working hours";
    }

    // Part-time salary is an hourly rate
    @Override
    public String getSalaryDescription() {
        return formatMoney(getSalary()) + " per hour";
    }

    // Hourly rate x hours worked in a month
    @Override
    public double computeMonthlySalary() {
        return getSalary() * hoursPerMonth;
    }
}