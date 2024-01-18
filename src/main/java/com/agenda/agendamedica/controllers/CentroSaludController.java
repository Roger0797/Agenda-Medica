package com.agenda.agendamedica.controllers;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.agenda.agendamedica.models.CentroSaludModel;
import com.agenda.agendamedica.repositories.CentroSaludRepository;
import com.agenda.agendamedica.services.CentroSaludService;

import java.util.Collections; // Agrega esta importación

import Config.ComprarCS;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/centroSalud")
public class CentroSaludController {

    @Autowired
    CentroSaludRepository centroSaludRepository;

    @Autowired
    CentroSaludService centroSaludService;

    @GetMapping("/listar")
	public ResponseEntity<List<CentroSaludModel>> obtenerCS() {
		List<CentroSaludModel> cs = new ArrayList<>(centroSaludRepository.findAll());
		java.util.Collections.sort(cs, new ComprarCS());
		return ResponseEntity.ok(cs);
	}

    @GetMapping("/buscar/{id}")
    public ResponseEntity<CentroSaludModel> buscaCSPorID(@PathVariable("id") Long id) {
        Optional<CentroSaludModel> csOptional = centroSaludRepository.findById(id);
        return csOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/crear")
    public ResponseEntity<CentroSaludModel> registraCS(@RequestBody CentroSaludModel CS) {
        CentroSaludModel cs = centroSaludRepository.save(CS);
        return ResponseEntity.ok(cs);
    }

    @PatchMapping("/actualizaCentroSalud/{id}")
    public ResponseEntity<CentroSaludModel> actualizaCS(@PathVariable("id") Long id, @RequestBody Map<String, String> datos) {
        Optional<CentroSaludModel> csOptional = centroSaludRepository.findById(id);
        if (csOptional.isPresent()) {
            CentroSaludModel cs2 = csOptional.get();
            cs2.setDireccion("direccion");
            cs2.setNombre(datos.get("nombre"));
            cs2.setTelefono(datos.get("telefono"));
            cs2.setHoraApertura(datos.get("horaApertura"));
            cs2.setHoraCierre(datos.get("horaCierre"));

            if (datos.containsKey("urgencias")) {
                cs2.setUrgencias(Boolean.parseBoolean(datos.get("urgencias")));
            }

            return ResponseEntity.ok(centroSaludRepository.save(cs2));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/urgencias")
    public ResponseEntity<ArrayList<CentroSaludModel>> verCSUrgencias() {
        ArrayList<CentroSaludModel> cs = centroSaludService.verCSUrgencias();
        return ResponseEntity.ok(cs);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminaCS(@PathVariable("id") Long id) {
        centroSaludRepository.deleteById(id);
    }
}