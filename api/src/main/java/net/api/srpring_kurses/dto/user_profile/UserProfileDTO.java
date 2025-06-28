package net.api.srpring_kurses.dto.user_profile;


import com.fasterxml.jackson.annotation.JsonProperty;
import net.api.srpring_kurses.model.Role;
import net.api.srpring_kurses.model.User;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserProfileDTO {
    private Long id;
    private String username;
    private String email;
    private String role;
    private List<UserProfileCourseDTO> courses;
    @JsonProperty("section_progress")
    private List<UserProfileSectionDTO> sections;

    public UserProfileDTO() {}

    public UserProfileDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.role = user.getRoles().stream()
                .map(Role::getName)
                .findFirst()
                .orElse("STUDENT");
        this.courses = Optional.ofNullable(user.getProgressList())
                .orElse(Collections.emptyList())
                .stream()
                .map(UserProfileCourseDTO::new)
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public List<UserProfileCourseDTO> getCourses() {
        return courses;
    }

    public List<UserProfileSectionDTO> getSections() {
        return sections;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSections(List<UserProfileSectionDTO> sections) {
        this.sections = sections;
    }

    public void setCourses(List<UserProfileCourseDTO> courses) {
        this.courses = courses;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
