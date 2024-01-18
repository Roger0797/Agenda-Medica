package com.agenda.agendamedica.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.agenda.agendamedica.models.TimeslotModel;

@Repository
public interface TimeslotRepository extends CrudRepository<TimeslotModel, Long> {
    
}
