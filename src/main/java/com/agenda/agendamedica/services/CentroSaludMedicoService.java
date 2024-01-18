package com.agenda.agendamedica.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agenda.agendamedica.models.CentroSaludMedicoModel;
import com.agenda.agendamedica.models.UsuarioModel;
import com.agenda.agendamedica.repositories.CentroSaludMedicoRepository;

@Service
public class CentroSaludMedicoService {

	
	@Autowired
	CentroSaludMedicoRepository centroSaludMedicoRepository;
	
	public ArrayList<UsuarioModel> verUsuarios(Long id, ArrayList<UsuarioModel> arrayList){
		ArrayList<CentroSaludMedicoModel> csms= (ArrayList<CentroSaludMedicoModel> )centroSaludMedicoRepository.findAll();
		for(CentroSaludMedicoModel csm:csms ) {
			if(csm.getCentroSaludMedicoModelid()==id) {
				ArrayList<UsuarioModel> usuarioModel = arrayList;
				return usuarioModel;
			}
		}
		return null;
	}
	
	
}
