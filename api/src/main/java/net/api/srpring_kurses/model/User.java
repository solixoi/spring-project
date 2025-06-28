package net.api.srpring_kurses.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String email;

    public Long getId() {
        return id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id")
    )
    private Set<Role> roles;

    public String getEmail() {
        return email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @OneToMany(mappedBy = "teacher")
    private List<Course> taughtCourses = new ArrayList<>();

    @OneToMany(mappedBy = "student")
    private List<StudentProgress> progressList = new ArrayList<>();

    @OneToMany(mappedBy = "student")
    private List<StudentSectionProgress> sectionProgressList = new ArrayList<>();

    public List<Course> getTaughtCourses() {
        return taughtCourses;
    }

    public List<StudentProgress> getProgressList() {
        return progressList;
    }

    public List<StudentSectionProgress> getSectionProgressList() {
        return sectionProgressList;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProgressList(List<StudentProgress> progressList) {
        this.progressList = progressList;
    }

    public void setSectionProgressList(List<StudentSectionProgress> sectionProgressList) {
        this.sectionProgressList = sectionProgressList;
    }

    public void setTaughtCourses(List<Course> taughtCourses) {
        this.taughtCourses = taughtCourses;
    }
}
