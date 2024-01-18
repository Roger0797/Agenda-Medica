package com.agenda.agendamedica.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.agenda.agendamedica.models.HistorialMedicoModel;

@Repository
public interface HistorialMedicoRepository extends CrudRepository<HistorialMedicoModel, Long>{

}
