package com.example.mcv.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;

@Entity
public class Resume {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nombre;
	private String nombreUsuario;
	private String fono;
	@Email
    private String email;
	private String avatar;
	private String banner;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaNacimiento;   
    private String nacionalidad;
    private String direccion;
    
    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinTable(name = "resume_experience", joinColumns = @JoinColumn(name = "resume_id"),
    inverseJoinColumns = @JoinColumn(name = "experience_id"))
    private Set<Experience> experiences = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinTable(name = "resume_studies", joinColumns = @JoinColumn(name = "resume_id"),
    inverseJoinColumns = @JoinColumn(name = "studies_id"))
    private Set<Studies> studies = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinTable(name = "resume_skills", joinColumns = @JoinColumn(name = "resume_id"),
    inverseJoinColumns = @JoinColumn(name = "skills_id"))
    private Set<Skills> skills = new HashSet<>();

    private String nombreEmpresa;
    private String direccionEmpresa;
    @Column(length=200)
    private String introduccionCarta;
    @Column(length=300)
    private String cuerpoCarta;
    
    public Resume() {
    	
    }

	public Resume(String nombre, String nombreUsuario, String fono, @Email String email, String avatar, String banner,
			Date fechaNacimiento, String nacionalidad, String direccion, String nombreEmpresa, String direccionEmpresa,
			String introduccionCarta, String cuerpoCarta) {
		this.nombre = nombre;
		this.nombreUsuario = nombreUsuario;
		this.fono = fono;
		this.email = email;
		this.avatar = avatar;
		this.banner = banner;
		this.fechaNacimiento = fechaNacimiento;
		this.nacionalidad = nacionalidad;
		this.direccion = direccion;
		this.nombreEmpresa = nombreEmpresa;
		this.direccionEmpresa = direccionEmpresa;
		this.introduccionCarta = introduccionCarta;
		this.cuerpoCarta = cuerpoCarta;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getFono() {
		return fono;
	}

	public void setFono(String fono) {
		this.fono = fono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Set<Experience> getExperiences() {
		return experiences;
	}

	public void addExperiences(Experience experience) {
		this.experiences.add(experience);
	}
	
	public void setExperiences(Set<Experience> experiences) {
		this.experiences = experiences;
	}

	public Set<Studies> getStudies() {
		return studies;
	}
	
	public void addtStudies(Studies study) {
		this.studies.add(study);
	}

	public void setStudies(Set<Studies> studies) {
		this.studies = studies;
	}
	
	public void addtSkills(Skills skill) {
		this.skills.add(skill);
	}

	public Set<Skills> getSkills() {
		return skills;
	}

	public void setSkills(Set<Skills> skills) {
		this.skills = skills;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public String getDireccionEmpresa() {
		return direccionEmpresa;
	}

	public void setDireccionEmpresa(String direccionEmpresa) {
		this.direccionEmpresa = direccionEmpresa;
	}

	public String getIntroduccionCarta() {
		return introduccionCarta;
	}

	public void setIntroduccionCarta(String introduccionCarta) {
		this.introduccionCarta = introduccionCarta;
	}

	public String getCuerpoCarta() {
		return cuerpoCarta;
	}

	public void setCuerpoCarta(String cuerpoCarta) {
		this.cuerpoCarta = cuerpoCarta;
	}	
}
