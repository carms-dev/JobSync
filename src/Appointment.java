public class Appointment {
    private int appointmentId;
    private int applicationId;
    private int employerId;
    private int applicantId;
    private String date;
    private String time;
    private String place;
    private String status;

    public Appointment(int appointmentId, int applicationId,
                       int employerId, int applicantId,
                       String date, String time, String place,
                       String status) {
        this.appointmentId = appointmentId;
        this.applicationId = applicationId;
        this.employerId = employerId;
        this.applicantId = applicantId;
        this.date = date;
        this.time = time;
        this.place = place;
        this.status = status;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public int getEmployerId() {
        return employerId;
    }

    public int getApplicantId() {
        return applicantId;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getPlace() {
        return place;
    }

    public String getStatus() {
        return status;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void displayAppointment() {
        System.out.println("\n===== INTERVIEW APPOINTMENT =====");
        System.out.println("Appointment ID: " + appointmentId);
        System.out.println("Application ID: " + applicationId);
        System.out.println("Date: " + date);
        System.out.println("Time: " + time);
        System.out.println("Place: " + place);
        System.out.println("Status: " + status);
    }
}