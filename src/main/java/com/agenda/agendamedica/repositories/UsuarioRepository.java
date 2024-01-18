package com.agenda.agendamedica.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.agenda.agendamedica.models.*;

@Repository
public interface UsuarioRepository extends CrudRepository<UsuarioModel, Long> {
	Optional<UsuarioModel> findByUserName(String username);
}








