package com.upc.trabajoarquitectura.controller;

import com.upc.trabajoarquitectura.dtos.CategoriaDTO;
import com.upc.trabajoarquitectura.entities.Categoria;
import com.upc.trabajoarquitectura.interfaces.ICategoriaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200","http://18.223.169.236/"})
@RestController
@RequestMapping("/api")
public class CategoriaController {
    @Autowired
        private ICategoriaService categoriaService;

    @GetMapping("/categorias")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<CategoriaDTO> listarCategorias(){
        ModelMapper mapper = new ModelMapper();
        List<Categoria> categorias = categoriaService.listarCategorias();
        List<CategoriaDTO> categoriaDTO = Arrays.asList(mapper.map(categorias, CategoriaDTO[].class));
        return categoriaDTO;
    }

    @PostMapping("/categoria")
    @PreAuthorize("hasRole('ADMIN')")
    public CategoriaDTO registrarCategoria(@RequestBody CategoriaDTO categoriaDTO){
        ModelMapper mapper = new ModelMapper();
        Categoria categoria = mapper.map(categoriaDTO, Categoria.class);
        categoria = categoriaService.registrarCategoria(categoria);
        categoriaDTO = mapper.map(categoria, CategoriaDTO.class);
        return categoriaDTO;
    }

    @PutMapping("/categoria/actualizar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoriaDTO> actualizarCategoria(@RequestBody CategoriaDTO categoriaDTO){
        ModelMapper mapper = new ModelMapper();
        try {
            Categoria categoria = mapper.map(categoriaDTO, Categoria.class);
            categoria = categoriaService.actualizarCategoria(categoria);
            categoriaDTO = mapper.map(categoria, CategoriaDTO.class);
        }
        catch (Exception e){
            return new ResponseEntity<>(categoriaDTO, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(categoriaDTO);
    }

    @DeleteMapping("/categoria/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void eliminarCategoria(@PathVariable Long id) throws Exception{
        try{
            categoriaService.eliminarCategoria(id);
        }catch (Exception e){
            throw new Exception("Disculpe la molestia");
        }
    }
}
