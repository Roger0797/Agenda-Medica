package com.agenda.agendamedica.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agenda.agendamedica.models.CentroSaludModel;
import com.agenda.agendamedica.repositories.CentroSaludRepository;

import Config.ComprarCS;

@Service
public class CentroSaludService {

	@Autowired
	CentroSaludRepository centroSaludRepository;
	
	public ArrayList<CentroSaludModel> verCSUrgencias(){
		ArrayList<CentroSaludModel> cs= (ArrayList<CentroSaludModel>) centroSaludRepository.findAll();
		ArrayList<CentroSaludModel> csAux= new ArrayList<CentroSaludModel>();
		for(CentroSaludModel centro: cs) {
			if(centro.isUrgencias()) {
				csAux.add(centro);
			}
		}
		java.util.Collections.sort(csAux, new ComprarCS());
		return csAux;
	}
	
}
