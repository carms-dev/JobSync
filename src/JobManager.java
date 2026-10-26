import java.util.ArrayList;
import java.util.List;

// Handles everything about job postings and the jobs.txt file.
// Each line of jobs.txt is turned into a FullTimeJob or PartTimeJob object,
// so the rest of the program works with objects instead of raw text.
public class JobManager {

    private static final String JOBS_FILE = "jobs.txt";

    // =========================
    // LOAD / FIND
    // =========================

    public ArrayList<JobPosting> loadJobs() {
        ArrayList<JobPosting> jobs = new ArrayList<>();

        for (String line : FileHandler.readAll(JOBS_FILE)) {
            JobPosting job = parseJob(line);

            if (job != null) {
                jobs.add(job);
            }
        }

        return jobs;
    }

    public JobPosting findJob(int jobId) {
        for (JobPosting job : loadJobs()) {
            if (job.getJobId() == jobId) {
                return job;
            }
        }

        return null;
    }

    public ArrayList<JobPosting> getOpenJobs() {
        ArrayList<JobPosting> result = new ArrayList<>();

        for (JobPosting job : loadJobs()) {
            if (job.isOpen()) {
                result.add(job);
            }
        }

        return result;
    }

    public ArrayList<JobPosting> getJobsByEmployer(int employerId,
                                                   boolean openOnly) {
        ArrayList<JobPosting> result = new ArrayList<>();

        for (JobPosting job : loadJobs()) {
            if (job.getEmployerId() == employerId
                    && (!openOnly || job.isOpen())) {
                result.add(job);
            }
        }

        return result;
    }

    public int getNextJobId() {
        int highestId = 0;

        for (String line : FileHandler.readAll(JOBS_FILE)) {
            String[] parts = line.split("\\|", -1);

            try {
                int id = Integer.parseInt(parts[0]);

                if (id > highestId) {
                    highestId = id;
                }

            } catch (NumberFormatException ignored) {
            }
        }

        return highestId + 1;
    }

    // =========================
    // ADD / UPDATE / DELETE
    // =========================

    public void addJob(JobPosting job) {
        FileHandler.appendLine(JOBS_FILE, job.toFileString());
    }

    // Replaces the saved record that has the same job ID
    public void updateJob(JobPosting job) {
        List<String> updated = new ArrayList<>();

        for (String line : FileHandler.readAll(JOBS_FILE)) {
            String[] parts = line.split("\\|", -1);

            if (parts[0].equals(String.valueOf(job.getJobId()))) {
                updated.add(job.toFileString());
            } else {
                updated.add(line);
            }
        }

        FileHandler.writeAll(JOBS_FILE, updated);
    }

    // The job is closed instead of erased, so old applications can still
    // show its title. A closed job no longer appears in searches.
    public void deleteJob(int jobId) {
        JobPosting job = findJob(jobId);

        if (job != null) {
            job.closeJob();
            updateJob(job);
        }
    }

    // =========================
    // SEARCH (method overloading)
    // Same method name, different parameters.
    // Only OPEN jobs are returned.
    // =========================

    // Search by title
    public ArrayList<JobPosting> search(String title) {
        ArrayList<JobPosting> result = new ArrayList<>();

        for (JobPosting job : getOpenJobs()) {
            if (job.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(job);
            }
        }

        return result;
    }

    // Search by title and location
    public ArrayList<JobPosting> search(String title, String location) {
        ArrayList<JobPosting> result = new ArrayList<>();

        for (JobPosting job : getOpenJobs()) {
            if (job.getTitle().toLowerCase().contains(title.toLowerCase())
                    && job.getLocation().toLowerCase()
                            .contains(location.toLowerCase())) {
                result.add(job);
            }
        }

        return result;
    }

    // Search by minimum monthly pay
    // (computeMonthlySalary() gives the right amount for each job type)
    public ArrayList<JobPosting> search(double minimumSalary) {
        ArrayList<JobPosting> result = new ArrayList<>();

        for (JobPosting job : getOpenJobs()) {
            if (job.computeMonthlySalary() >= minimumSalary) {
                result.add(job);
            }
        }

        return result;
    }

 
    private JobPosting parseJob(String line) {
        String[] parts = line.split("\\|", -1);

        if (parts.length < 8) {
            return null;
        }

        try {
            int jobId = Integer.parseInt(parts[0]);
            int employerId = Integer.parseInt(parts[1]);
            double salary = Double.parseDouble(parts[4]);

            String type = parts.length >= 9 ? parts[8] : "FULL-TIME";
            double hours = parts.length >= 10
                    ? Double.parseDouble(parts[9])
                    : 160;

            JobPosting job;

            if (type.equalsIgnoreCase("PART-TIME")) {
                job = new PartTimeJob(jobId, employerId, parts[2], parts[3],
                        salary, parts[5], parts[6], hours);
            } else {
                job = new FullTimeJob(jobId, employerId, parts[2], parts[3],
                        salary, parts[5], parts[6], hours);
            }

            job.setOpen(parts[7].equalsIgnoreCase("OPEN"));

            return job;

        } catch (NumberFormatException e) {
            return null;
        }
    }
}