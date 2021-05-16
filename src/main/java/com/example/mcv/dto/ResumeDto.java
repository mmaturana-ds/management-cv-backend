package com.example.mcv.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

import com.example.mcv.entities.Experience;
import com.example.mcv.entities.Skills;
import com.example.mcv.entities.Studies;

public class ResumeDto {
	@NotBlank
	private String nombre;
	@NotBlank
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
    private Set<Experience> experiences = new HashSet<>();
    private Set<Studies> studies = new HashSet<>();
    private Set<Skills> skills = new HashSet<>();
    
    private String nombreEmpresa;
    private String direccionEmpresa;
    @Max(value = 200 , message = "la introducción no puede tener más de 200 caracteres")
    private String introduccionCarta;
    @Max(value = 300 , message = "el cuerpo no puede tener más de 300 caracteres")
    private String cuerpoCarta;
    
    public ResumeDto() {
    	
    }

	public ResumeDto(@NotBlank String nombre, @NotBlank String nombreUsuario, String fono, @Email String email,
			String avatar, String banner, Date fechaNacimiento, String nacionalidad, String direccion,
			Set<Experience> experiences, Set<Studies> studies, Set<Skills> skills, String nombreEmpresa,
			String direccionEmpresa,
			@Max(value = 200, message = "la introducción no puede tener más de 200 caracteres") String introduccionCarta,
			@Max(value = 300, message = "el cuerpo no puede tener más de 300 caracteres") String cuerpoCarta) {
		super();
		this.nombre = nombre;
		this.nombreUsuario = nombreUsuario;
		this.fono = fono;
		this.email = email;
		this.avatar = avatar;
		this.banner = banner;
		this.fechaNacimiento = fechaNacimiento;
		this.nacionalidad = nacionalidad;
		this.direccion = direccion;
		this.experiences = experiences;
		this.studies = studies;
		this.skills = skills;
		this.nombreEmpresa = nombreEmpresa;
		this.direccionEmpresa = direccionEmpresa;
		this.introduccionCarta = introduccionCarta;
		this.cuerpoCarta = cuerpoCarta;
	}

	public String getNombre() {
		return nombre;
	}
	
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public void setExperiences(Set<Experience> experiences) {
		this.experiences = experiences;
	}

	public Set<Studies> getStudies() {
		return studies;
	}

	public void setStudies(Set<Studies> studies) {
		this.studies = studies;
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
