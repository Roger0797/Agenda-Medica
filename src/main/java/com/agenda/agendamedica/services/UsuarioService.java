package com.agenda.agendamedica.services;

import java.sql.Date;
import java.util.ArrayList;
//import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agenda.agendamedica.models.CentroSaludMedicoModel;
import com.agenda.agendamedica.models.CentroSaludModel;
import com.agenda.agendamedica.models.CitaModel;
import com.agenda.agendamedica.models.MedicoModel;
//import com.agenda.agendamedica.models.TimeslotModel;
import com.agenda.agendamedica.models.UsuarioModel;
import com.agenda.agendamedica.repositories.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	@Autowired
	CentroSaludMedicoService centroSaludMedicoService;
	

	
	public MedicoModel verMedicoAsignado(Long id) {
			ArrayList<UsuarioModel> users = (ArrayList<UsuarioModel>) usuarioRepository.findAll();
			for(UsuarioModel x:users) {
				if(x.getUserId()==(id)) {
					return  x.getCentroSaludMedicoModel().getMedicoModel();
				}
			}
				
		return null;
		
	}
	
	public CentroSaludModel verCSAsignado(Long id) {
		ArrayList<UsuarioModel> users = (ArrayList<UsuarioModel>) usuarioRepository.findAll();
		for(UsuarioModel x:users) {
			if(x.getUserId()==(id)) {
				return  x.getCentroSaludMedicoModel().getCentroSaludModel();
			}
		}
			
	return null;
	}
	
	
	//Pasar id de usuario
	//Formaro date 2022-06-29
	public ArrayList<CitaModel> verDisponibilidadMedico(Long id, Date date){
		ArrayList<UsuarioModel> users = (ArrayList<UsuarioModel>) usuarioRepository.findAll();
		CentroSaludMedicoModel csm = new CentroSaludMedicoModel();
		for(UsuarioModel x:users) {
			if(x.getUserId()==(id)) {
				csm= x.getCentroSaludMedicoModel();
			}
		}
		
		ArrayList<CitaModel> CitasUsersCSM = new ArrayList<CitaModel>();
		for(UsuarioModel user: csm.getUsuarioModel()) {
			CitasUsersCSM.addAll(user.getCitaModel());
		}
		return CitasUsersCSM;
	
	}
	
	
	
}
