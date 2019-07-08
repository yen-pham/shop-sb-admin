package vn.edu.leading.shop.controllers.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.leading.shop.controllers.api.dto.SupplierDTO;
import vn.edu.leading.shop.errors.ObjectNotFoundException;
import vn.edu.leading.shop.models.SupplierModel;
import vn.edu.leading.shop.services.SupplierService;

import javax.validation.Valid;

public class SupplierApiController {
    private final SupplierService supplierService;

    public SupplierApiController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    public ResponseEntity<?> list() {
        return new ResponseEntity<>(supplierService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity save(@Valid @RequestBody SupplierDTO supplierDTO) {
        SupplierModel supplierModel = new SupplierModel();
        supplierModel.setAddress(supplierDTO.getAddress());
        supplierModel.setContactName(supplierDTO.getContactName());
        supplierModel.setCity(supplierDTO.getCity());
        supplierModel.setPhone(supplierDTO.getPhone());
        supplierModel.setCountry(supplierDTO.getCountry());
        supplierModel.setPostalCode(supplierDTO.getPostalCode());
        supplierModel.setSupplierName(supplierDTO.getSupplierName());
        return new ResponseEntity(supplierService.save(supplierModel), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @Valid @RequestBody SupplierDTO supplierDTO) {
        SupplierModel supplierModel = supplierService.findById(id).orElseThrow(() -> new ObjectNotFoundException("shipper"));
        supplierModel.setAddress(supplierDTO.getAddress());
        supplierModel.setContactName(supplierDTO.getContactName());
        supplierModel.setCity(supplierDTO.getCity());
        supplierModel.setPhone(supplierDTO.getPhone());
        supplierModel.setCountry(supplierDTO.getCountry());
        supplierModel.setPostalCode(supplierDTO.getPostalCode());
        supplierModel.setSupplierName(supplierDTO.getSupplierName());
        return new ResponseEntity(supplierService.save(supplierModel), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        supplierService.findById(id).orElseThrow(() -> new ObjectNotFoundException("shipper"));
        return new ResponseEntity<>(supplierService.delete(id), HttpStatus.OK);
    }
}
