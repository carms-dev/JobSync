public class FullTimeJob extends JobPosting {
    private double hoursPerMonth;

    public FullTimeJob(int jobId, int employerId, String title,
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
        return "FULL-TIME";
    }

    @Override
    public String getBenefits() {
        return "Regular employment benefits";
    }

    // Full-time salary is already a monthly amount
    @Override
    public String getSalaryDescription() {
        return formatMoney(getSalary()) + " per month";
    }

    @Override
    public double computeMonthlySalary() {
        return getSalary();
    }
}