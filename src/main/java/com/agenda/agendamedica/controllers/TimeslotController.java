package com.agenda.agendamedica.controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.agenda.agendamedica.models.TimeslotModel;
import com.agenda.agendamedica.repositories.TimeslotRepository;
import com.agenda.agendamedica.services.TimeslotService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/timeslot")
public class TimeslotController {

    @Autowired
    TimeslotService timService;
    
    @Autowired
    TimeslotRepository timeslotRepository;

    @GetMapping("/listar")
    public ResponseEntity<ArrayList<TimeslotModel>> obtenerTimeslot() {
        ArrayList<TimeslotModel> timeslots = (ArrayList<TimeslotModel>) timeslotRepository.findAll();
        return ResponseEntity.ok(timeslots);
    }

	@GetMapping("/buscar/{id}")
	public ResponseEntity<TimeslotModel> buscaTimeslotPorID(@PathVariable("id") Long id) {
		Optional<TimeslotModel> timeslot = timeslotRepository.findById(id);
		return ResponseEntity.ok(timeslot.orElse(null)); // O simplemente ResponseEntity.ok(timeslot)
	}
    @PostMapping("/crear")
    public ResponseEntity<TimeslotModel> registraTimeslot(@RequestBody TimeslotModel timeslot) {
        TimeslotModel createdTimeslot = timeslotRepository.save(timeslot);
        return ResponseEntity.ok(createdTimeslot);
    }

    @GetMapping("/disponibilidadMedico/{id}/{fecha}")
    public ResponseEntity<ArrayList<TimeslotModel>> verDispobilidadMedicoUsuario(@PathVariable("id") Long id, @PathVariable("fecha") Date date) {
        ArrayList<TimeslotModel> timeslots = timService.verTimeslotLibres(id, date);
        return ResponseEntity.ok(timeslots);
    }
}