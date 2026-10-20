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

    public double getHoursPerMonth() {
        return hoursPerMonth;
    }

    public void setHoursPerMonth(double hoursPerMonth) {
        this.hoursPerMonth = hoursPerMonth;
    }

    @Override
    public String getBenefits() {
        return "Regular employment benefits";
    }

    @Override
    public double computeMonthlySalary() {
        return getSalary();
    }
}