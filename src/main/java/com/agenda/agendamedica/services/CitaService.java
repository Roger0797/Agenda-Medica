package com.agenda.agendamedica.services;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agenda.agendamedica.models.CitaModel;
import com.agenda.agendamedica.models.TimeslotModel;
import com.agenda.agendamedica.models.UsuarioModel;
import com.agenda.agendamedica.repositories.CitaRepository;
import com.agenda.agendamedica.repositories.UsuarioRepository;

import Config.CompararCitas;

@Service
public class CitaService {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    CitaRepository citaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    public ArrayList<CitaModel> verCitasUsuario(Long idUsuario) {
        ArrayList<CitaModel> citas = (ArrayList<CitaModel>) citaRepository.findAll();
        ArrayList<CitaModel> citasAux = new ArrayList<CitaModel>();
        for (CitaModel cita : citas) {
            if (cita.getUsuarioModel().getUserId() == idUsuario) {
                citasAux.add(cita);
            }
        }
        java.util.Collections.sort(citasAux, new CompararCitas());

        return citasAux;
    }

    public ArrayList<CitaModel> verCitasPasadaUsuario(Long idUsuario) {
        ArrayList<CitaModel> citas = (ArrayList<CitaModel>) citaRepository.findAll();
        ArrayList<CitaModel> citasAux = new ArrayList<CitaModel>();
        Date date = new Date(System.currentTimeMillis());

        for (CitaModel cita : citas) {
            if (cita.getFecha().before(date)) {
                citasAux.add(cita);
            }
        }
        java.util.Collections.sort(citasAux, new CompararCitas());
        return citasAux;
    }

    public ArrayList<CitaModel> verCitasProximasUsuario(Long idUsuario) {
        ArrayList<CitaModel> citas = verCitasUsuario(idUsuario);
        ArrayList<CitaModel> citasAux = new ArrayList<CitaModel>();
        Date date = new Date(System.currentTimeMillis());
        for (CitaModel cita : citas) {
            if (cita.getFecha().after(date)) {
                citasAux.add(cita);
            }
        }
        return citasAux;
    }

    public ArrayList<CitaModel> verCitasMedico(Long idMedico) {
        ArrayList<UsuarioModel> users = (ArrayList<UsuarioModel>) usuarioRepository.findAll();
        ArrayList<UsuarioModel> userAux = new ArrayList<UsuarioModel>();
        ArrayList<CitaModel> citas = new ArrayList<CitaModel>();
        for (UsuarioModel u : users) {
            if ((usuarioService.verMedicoAsignado(u.getUserId())).getUserId() == idMedico) {
                userAux.add(u);
            }
        }

        for (UsuarioModel u2 : userAux) {
            citas.addAll(verCitasUsuario(u2.getUserId()));
        }

        java.util.Collections.sort(citas, new CompararCitas());
        return citas;

    }

   // @SuppressWarnings("deprecation")
	public ArrayList<TimeslotModel> verTimeslotLibresMedico(Long id, Date date) {
		ArrayList<CitaModel> citas = usuarioService.verDisponibilidadMedico(id, date);
		ArrayList<TimeslotModel> timeslots = new ArrayList<TimeslotModel>();
	
		for (CitaModel cita : citas) {
			// Convierte la fecha de java.util.Date a LocalDate
			Instant instant = Instant.ofEpochMilli(cita.getFecha().getTime());
			LocalDate citaFecha = instant.atZone(ZoneId.systemDefault()).toLocalDate();
	
			LocalDate inputFecha = date.toLocalDate();
	
			if (citaFecha.equals(inputFecha)) {
				TimeslotModel timeslot = cita.getTimeslot();
				if (!timeslots.contains(timeslot)) {
					timeslots.add(timeslot);
				}
			}
		}
	
		return timeslots;
	}
    
}
