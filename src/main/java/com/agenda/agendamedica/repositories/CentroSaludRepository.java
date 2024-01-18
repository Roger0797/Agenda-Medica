package com.agenda.agendamedica.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.agenda.agendamedica.models.*;

@Repository
public interface  CentroSaludRepository extends CrudRepository<CentroSaludModel, Long> {
}