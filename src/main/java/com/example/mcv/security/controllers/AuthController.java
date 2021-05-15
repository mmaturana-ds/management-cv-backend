package com.example.mcv.security.controllers;

import com.example.mcv.dto.Mensaje;
import com.example.mcv.security.dto.JwtDto;
import com.example.mcv.security.dto.LoginUsuario;
import com.example.mcv.security.dto.NuevoUsuario;
import com.example.mcv.security.entities.Rol;
import com.example.mcv.security.entities.Usuario;
import com.example.mcv.security.enums.RolNombre;
import com.example.mcv.security.jwt.JwtProvider;
import com.example.mcv.security.services.RolService;
import com.example.mcv.security.services.UsuarioService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
	
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal puestos o email inválido"), HttpStatus.BAD_REQUEST);
        if(usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario()))
            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        if(usuarioService.existsByEmail(nuevoUsuario.getEmail()))
            return new ResponseEntity(new Mensaje("ese email ya existe"), HttpStatus.BAD_REQUEST);

        Usuario usuario = new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getNombreUsuario(), nuevoUsuario.getEmail(),
                        passwordEncoder.encode(nuevoUsuario.getPassword()));

        Set<Rol> roles = new HashSet<>();
        roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
        if(nuevoUsuario.getRoles().contains("admin"))
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
        usuario.setRoles(roles);
        usuarioService.save(usuario);
        return new ResponseEntity(new Mensaje("usuario guardado"), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal puestos"), HttpStatus.BAD_REQUEST);
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(), loginUsuario.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
        return new ResponseEntity(jwtDto, HttpStatus.OK);
    }
    
	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("/update/{nombre}")
	public ResponseEntity<?> update(@PathVariable("nombre") String nombreUsuario, @RequestBody NuevoUsuario usuarioDetails, HttpServletRequest req) {
		if(!usuarioService.existsByNombreUsuario(nombreUsuario))
			return new ResponseEntity<>(new Mensaje("Usuario no encontrado"), HttpStatus.NOT_FOUND);

		String token = getToken(req);
		String _jwtUser = jwtProvider.getNombreUsuarioFromToken(token);
		
		if(!_jwtUser.equals(nombreUsuario)) 
			return new ResponseEntity<>(new Mensaje("no autorizado"), HttpStatus.FORBIDDEN);
		
		if(StringUtils.isBlank(usuarioDetails.getNombre()))
			return new ResponseEntity<>(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);

		if(StringUtils.isBlank(usuarioDetails.getEmail()) && usuarioService.getByEmail(usuarioDetails.getEmail()).get().getNombreUsuario() != nombreUsuario)
			return new ResponseEntity<>(new Mensaje("el email es obligatorio"), HttpStatus.BAD_REQUEST);

		Usuario usuario = usuarioService.getByNombreUsuario(nombreUsuario).get();
		usuario.setNombre(usuarioDetails.getNombre());
		usuario.setEmail(usuarioDetails.getEmail());
		usuarioService.save(usuario);
		return new ResponseEntity<>(new Mensaje("usuario actualizado"), HttpStatus.OK);
	}
	
    private String getToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer"))
            return header.replace("Bearer ", "");
        return null;
    }
}
