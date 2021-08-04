package com.murilonerdx.hateos.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.murilonerdx.hateos.entities.Produto;
import com.murilonerdx.hateos.services.ProdutoService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

  private final ProdutoService service;

  @GetMapping()
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ResponseEntity<List<Produto>> getAllProdutos(){
    List<Produto> produtosList = service.getAll();
    if(produtosList.isEmpty()){
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }else{
      for(Produto produto : produtosList){
        long id = produto.getId();

        //Chama o proximo
        produto.add(linkTo(methodOn(ProdutoController.class).getOneProduto(id)).withSelfRel());
      }
      return new ResponseEntity<List<Produto>>(produtosList, HttpStatus.OK);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Produto> getOneProduto(@PathVariable(value="id") Long id){
    Produto produto = service.getById(id);
    if(produto == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }else{
      produto.add(linkTo(methodOn(ProdutoController.class).getAllProdutos()).withRel("Lista de Produtos"));
      return new ResponseEntity<Produto>(produto, HttpStatus.OK);
    }
  }

  @PostMapping
  public ResponseEntity<Produto> saveProduto(@RequestBody Produto produto){
    return new ResponseEntity<Produto>(service.insert(produto), HttpStatus.CREATED);
  }

}
