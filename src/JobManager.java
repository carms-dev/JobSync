public class JobManager {

    public void addJob(JobPosting job) {
        System.out.println("Job added successfully.");
    }

    public void viewJob(JobPosting job) {
        if (job != null) {
            job.displayJob();
        }
    }

    public void updateJob(JobPosting job, String title,
                           String location, double salary,
                           String description, String requiredSkills) {

        if (job != null) {
            job.setTitle(title);
            job.setLocation(location);
            job.setSalary(salary);
            job.setDescription(description);
            job.setRequiredSkills(requiredSkills);

            System.out.println("Job updated successfully.");
        }
    }

    public void deleteJob(JobPosting job) {
        if (job != null) {
            job.closeJob();
            System.out.println("Job closed successfully.");
        }
    }

    // Search by title
    public void search(JobPosting[] jobs, String title) {
        for (JobPosting job : jobs) {
            if (job != null &&
                job.getTitle().toLowerCase().contains(title.toLowerCase()) &&
                job.isOpen()) {

                job.displayJob();
            }
        }
    }

    // Search by title and location
    public void search(JobPosting[] jobs, String title, String location) {
        for (JobPosting job : jobs) {
            if (job != null &&
                job.getTitle().toLowerCase().contains(title.toLowerCase()) &&
                job.getLocation().toLowerCase().contains(location.toLowerCase()) &&
                job.isOpen()) {

                job.displayJob();
            }
        }
    }

    // Search by minimum salary
    public void search(JobPosting[] jobs, double minimumSalary) {
        for (JobPosting job : jobs) {
            if (job != null &&
                job.getSalary() >= minimumSalary &&
                job.isOpen()) {

                job.displayJob();
            }
        }
    }
}