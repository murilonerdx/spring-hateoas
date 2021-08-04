package com.murilonerdx.hateos.services;

import com.murilonerdx.hateos.entities.Produto;
import com.murilonerdx.hateos.repositories.ProdutoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ProdutoService{

  private final ProdutoRepository repository;


  public List<Produto> getAll(){
    return repository.findAll();
  }

  public Produto getById(Long id){
    return repository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  public Produto insert(Produto produto){
    return repository.save(produto);
  }

}
