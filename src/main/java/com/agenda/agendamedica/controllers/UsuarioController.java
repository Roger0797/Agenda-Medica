package com.agenda.agendamedica.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agenda.agendamedica.models.CentroSaludModel;
import com.agenda.agendamedica.models.HistorialMedicoModel;
import com.agenda.agendamedica.models.MedicoModel;
import com.agenda.agendamedica.models.UsuarioModel;
import com.agenda.agendamedica.repositories.UsuarioRepository;
import com.agenda.agendamedica.services.UsuarioService;

import Config.compararUsuarios;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/listar")
   public ResponseEntity<List<UsuarioModel>> obtenerUsuarios() {
    List<UsuarioModel> users = StreamSupport
        .stream(usuarioRepository.findAll().spliterator(), false)
        .collect(Collectors.toList());
    users.sort(new compararUsuarios());
    return ResponseEntity.ok(users);
}

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarUsuarioPorID(@PathVariable("id") Long id) {
        Optional<UsuarioModel> user = usuarioRepository.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/crear")
    public ResponseEntity<UsuarioModel> registraUsuario(@RequestBody UsuarioModel usuario) {
        UsuarioModel user = usuario;
        user.setPassword(encoder.encode(usuario.getPassword()));
        usuarioRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/actualizaUsuario/{id}")
    public ResponseEntity<?> actualizaUsuario(@PathVariable("id") Long id, @RequestBody Map<String, String> datos) {
        Optional<UsuarioModel> userOptional = usuarioRepository.findById(id);
        if (userOptional.isPresent()) {
            UsuarioModel user = userOptional.get();
            user.setApellidos(datos.get("apellidos"));
            user.setNombre(datos.get("nombre"));
            user.setTelefono(datos.get("telefono"));
            user.setMail(datos.get("mail"));
            return ResponseEntity.ok(usuarioRepository.save(user));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscar/{id}/historialMedico")
    public ResponseEntity<HistorialMedicoModel> verHistorialUsuario(@PathVariable("id") Long id) {
        Optional<UsuarioModel> user = usuarioRepository.findById(id);
        HistorialMedicoModel hm = user.map(UsuarioModel::getHistorialMedico).orElse(null);
        return ResponseEntity.ok(hm);
    }

    @GetMapping("/buscar/{id}/medico")
    public ResponseEntity<MedicoModel> verMedicoUsuario(@PathVariable("id") Long id) {
        MedicoModel med = usuarioService.verMedicoAsignado(id);
        return med != null ? ResponseEntity.ok(med) : ResponseEntity.notFound().build();
    }

    @GetMapping("/buscar/{id}/centroSalud")
    public ResponseEntity<CentroSaludModel> verCSUsuario(@PathVariable("id") Long id) {
        CentroSaludModel cs = usuarioService.verCSAsignado(id);
        return cs != null ? ResponseEntity.ok(cs) : ResponseEntity.notFound().build();
    }

    @GetMapping("/buscarUserName/{username}")
    public ResponseEntity<?> buscarUsuarioPorUsername(@PathVariable("username") String username) {
        Optional<UsuarioModel> user = usuarioRepository.findByUserName(username);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminaUsuario(@PathVariable("id") Long id) {
        usuarioRepository.deleteById(id);
    }
}
