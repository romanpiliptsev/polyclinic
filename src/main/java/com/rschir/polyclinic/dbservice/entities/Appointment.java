package com.rschir.polyclinic.dbservice.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointmentId")
    private Long appointmentId;

    @Column(name = "description")
    private String description;

    @Column(name = "appointmentDate")
    private Date appointmentDate;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE})
    @JsonBackReference(value = "doctorAppointments")
    @JoinColumn(name="doctorId")
    private Doctor doc;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE})
    @JsonBackReference(value = "patientAppointments")
    @JoinColumn(name="patientId")
    private Patient patientOfAppointment;


    public Appointment() {
        appointmentDate = new Date();
    }

    public Appointment(String description, Date appointmentDate) {
        this.description = description;
        this.appointmentDate = appointmentDate;
    }

    public Appointment(String description, Date appointmentDate, Doctor doctor, Patient patient) {
        this.description = description;
        this.appointmentDate = appointmentDate;
        this.doc = doctor;
        this.patientOfAppointment = patient;
    }

    public Appointment(String description, Doctor doctor, Patient patient) {
        this.description = description;
        this.appointmentDate = new Date();
        this.doc = doctor;
        this.patientOfAppointment = patient;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public String getDescription() {
        return description;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public Doctor getDoctor() {
        return doc;
    }

    public Patient getPatient() {
        return patientOfAppointment;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public void setDoctor(Doctor doctor) {
        this.doc = doctor;
    }

    public void setPatient(Patient patient) {
        this.patientOfAppointment = patient;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId=" + appointmentId +
                ", description='" + description + '\'' +
                ", appointmentDate=" + appointmentDate +
                ", doctor=" + doc +
                ", patient=" + patientOfAppointment +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(appointmentId, that.appointmentId) &&
                Objects.equals(description, that.description) &&
                Objects.equals(appointmentDate, that.appointmentDate) &&
                Objects.equals(doc, that.doc) &&
                Objects.equals(patientOfAppointment, that.patientOfAppointment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appointmentId, description, appointmentDate, doc, patientOfAppointment);
    }
}
