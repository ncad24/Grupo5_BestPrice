package com.upc.trabajoarquitectura.respository;

import com.upc.trabajoarquitectura.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    //Querys
    public Optional<Usuario> findBynombreUsuario(String nombreUsuario);
}
