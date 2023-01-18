package by.mk_jd2_92_22.foodCounter.controllers;

import by.mk_jd2_92_22.foodCounter.model.Product;
import by.mk_jd2_92_22.foodCounter.services.api.IProductService;
import by.mk_jd2_92_22.foodCounter.services.dto.PageDTO;
import by.mk_jd2_92_22.foodCounter.services.dto.ProductDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {


    private final IProductService service;

    public ProductController(IProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody ProductDTO dto,
                                          @RequestHeader(HttpHeaders.AUTHORIZATION) HttpHeaders token){
        return ResponseEntity.ok(service.create(dto, token));
    }

    @GetMapping
    public ResponseEntity<PageDTO<Product>> get(@RequestParam int page,
                                @RequestParam int size){
        return ResponseEntity.ok(service.get(page, size));
    }

    @GetMapping("/my")
    public ResponseEntity<PageDTO<Product>> getMy(@RequestParam int page,
                                                @RequestParam int size){
        return ResponseEntity.ok(service.get(page, size));
    }

    @GetMapping
    @RequestMapping("/{uuid}")
    public ResponseEntity<Product> get(@PathVariable UUID uuid){
        return ResponseEntity.ok(service.get(uuid));
    }


    @PutMapping("/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<Product> update(@PathVariable UUID uuid,
                                   @PathVariable ("dt_update") LocalDateTime dtUpdate,
                                   @Valid @RequestBody ProductDTO dto,
                                   @RequestHeader(HttpHeaders.AUTHORIZATION) HttpHeaders token){

        Product product = service.update(uuid, dtUpdate, dto, token);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<?> delete(@PathVariable UUID uuid,
                             @PathVariable ("dt_update") LocalDateTime dtUpdate,
                                    @RequestHeader(HttpHeaders.AUTHORIZATION) HttpHeaders token){

        service.delete(uuid, dtUpdate, token);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
