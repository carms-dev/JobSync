
public class Resume {
    private String fullName;
    private String email;
    private String phone;
    private String location;
    private String summary;
    private String education;

    private String skill1;
    private String skill2;
    private String skill3;
    private String skill4;
    private String skill5;

    private String experienceTitle;
    private String organization;
    private String experienceDescription;

    public Resume(
            String fullName, String email, String phone,
            String location, String summary, String education,
            String skill1, String skill2, String skill3,
            String skill4, String skill5,
            String experienceTitle, String organization,
            String experienceDescription) {

        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.location = location;
        this.summary = summary;
        this.education = education;
        this.skill1 = skill1;
        this.skill2 = skill2;
        this.skill3 = skill3;
        this.skill4 = skill4;
        this.skill5 = skill5;
        this.experienceTitle = experienceTitle;
        this.organization = organization;
        this.experienceDescription = experienceDescription;
    }

    // Compatibility with older resume records
    public Resume(String skills, String education, String experience) {
        this("", "", "", "", "", education,
                "", "", "", "", "", "", "", experience);
        setSkills(skills);
    }

    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getLocation() { return location; }
    public String getSummary() { return summary; }
    public String getEducation() { return education; }

    public String getSkill1() { return skill1; }
    public String getSkill2() { return skill2; }
    public String getSkill3() { return skill3; }
    public String getSkill4() { return skill4; }
    public String getSkill5() { return skill5; }

    public String getExperienceTitle() { return experienceTitle; }
    public String getOrganization() { return organization; }
    public String getExperienceDescription() {
        return experienceDescription;
    }

    public void setFullName(String value) { fullName = value; }
    public void setEmail(String value) { email = value; }
    public void setPhone(String value) { phone = value; }
    public void setLocation(String value) { location = value; }
    public void setSummary(String value) { summary = value; }
    public void setEducation(String value) { education = value; }

    public void setSkill1(String value) { skill1 = value; }
    public void setSkill2(String value) { skill2 = value; }
    public void setSkill3(String value) { skill3 = value; }
    public void setSkill4(String value) { skill4 = value; }
    public void setSkill5(String value) { skill5 = value; }

    public void setExperienceTitle(String value) {
        experienceTitle = value;
    }

    public void setOrganization(String value) {
        organization = value;
    }

    public void setExperienceDescription(String value) {
        experienceDescription = value;
    }

    // Older method names used elsewhere in JobSync
    public String getSkills() {
        return String.join(", ",
                skill1, skill2, skill3, skill4, skill5);
    }

    public void setSkills(String skills) {
        String[] parts = skills.split(",", 5);
        skill1 = parts.length > 0 ? parts[0].trim() : "";
        skill2 = parts.length > 1 ? parts[1].trim() : "";
        skill3 = parts.length > 2 ? parts[2].trim() : "";
        skill4 = parts.length > 3 ? parts[3].trim() : "";
        skill5 = parts.length > 4 ? parts[4].trim() : "";
    }

    public String getExperience() {
        return experienceDescription;
    }

    public void setExperience(String value) {
        experienceDescription = value;
    }

    public void displayResume() {
        System.out.println("\n===== RESUME =====");
        System.out.println("Full Name: " + fullName);
        System.out.println("Email: " + email);
        System.out.println("Mobile: " + phone);
        System.out.println("Address: " + location);
        System.out.println("Profile Summary: " + summary);
        System.out.println("Education: " + education);
        System.out.println("Skills: " + getSkills());
        System.out.println("Experience: " + experienceDescription);
    }
}