package vn.edu.leading.shop.controllers.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.leading.shop.controllers.api.dto.UserDTO;
import vn.edu.leading.shop.errors.ObjectNotFoundException;
import vn.edu.leading.shop.models.UserModel;
import vn.edu.leading.shop.services.UserService;

import javax.validation.Valid;

public class UserApiController {
    private final UserService userService;

    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> list() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity save(@Valid @RequestBody UserDTO userDTO) {
        UserModel userModel = new UserModel();
        userModel.setUsername(userDTO.getUsername());
        userModel.setPassword(userDTO.getPassword());
        return new ResponseEntity(userService.save(userModel), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        UserModel userModel = userService.findById(id).orElseThrow(() -> new ObjectNotFoundException("shipper"));
        userModel.setUsername(userDTO.getUsername());
        userModel.setPassword(userDTO.getPassword());
        return new ResponseEntity(userService.save(userModel), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        userService.findById(id).orElseThrow(() -> new ObjectNotFoundException("shipper"));
        return new ResponseEntity<>(userService.delete(id), HttpStatus.OK);
    }
}
