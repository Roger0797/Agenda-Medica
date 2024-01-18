package com.agenda.agendamedica.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.agenda.agendamedica.models.AdminModel;

@Repository
public interface AdminRepository extends CrudRepository<AdminModel, Long>{

}
