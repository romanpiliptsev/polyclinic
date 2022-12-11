package com.rschir.polyclinic.dbservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "doctor")
@JsonIgnoreProperties({ "recipes", "appointments", "enabled", "accountNonLocked", "accountNonExpired", "credentialsNonExpired", "authorities", "password", "username" })
public class Doctor implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctorId")
    private Long doctorId;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "specialization")
    private String specialization;

    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE},
            mappedBy = "doctor")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Recipe> recipes;

    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE},
            mappedBy = "doc")
    @JsonManagedReference(value = "doctorAppointments")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Appointment> appointments;


    public Doctor() {
    }

    public Doctor(String firstName, String lastName, String patronymic, String specialization) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.specialization = specialization;
    }

    public Doctor(Long doctorId, String firstName, String lastName, String patronymic, String specialization) {
        this.doctorId = doctorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.specialization = specialization;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public void addRecipe(Recipe theRecipe) {
        if (recipes == null) {
            recipes = new ArrayList<>();
        }
        recipes.add(theRecipe);
        theRecipe.setDoctor(this);
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    @Override
    public String toString() {
        return "Doctor #" + doctorId + " - " + firstName + " " + lastName + " " + patronymic + " - " + specialization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Doctor)) return false;
        Doctor doctor = (Doctor) o;
        return doctorId.equals(doctor.doctorId) &&
                firstName.equals(doctor.firstName) &&
                lastName.equals(doctor.lastName) &&
                patronymic.equals(doctor.patronymic) &&
                specialization.equals(doctor.specialization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(doctorId, firstName, lastName, patronymic, specialization);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.specialization.equals("admin")) {
            return Collections.singletonList(new SimpleGrantedAuthority("admin"));
        } else if (this.specialization.equals("registry")) {
            return Collections.singletonList(new SimpleGrantedAuthority("registry"));
        } else {
            return Collections.singletonList(new SimpleGrantedAuthority("doctor"));
        }
    }

    @Override
    public String getPassword() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(this.lastName + this.doctorId.toString());
    }

    @Override
    public String getUsername() {
        return this.doctorId.toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
