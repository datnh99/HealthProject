package HealthDeclaration.controller;

import HealthDeclaration.modal.entity.User;
import HealthDeclaration.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {
    @Autowired
    IUserService service;

    @GetMapping("/getAll")
    private ResponseEntity getAll() {
        //ResponseMessa
        try {
            List<User> list = service.getAll();
            if (list.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getOne")
    private ResponseEntity<User> getByUsername(@RequestParam("username") String username) {
//        try {
//            User user = service.getByTen(username);
//            if (user == null) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//            return new ResponseEntity<>(user, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
        return null;
    }

    @PostMapping("/add")
    private ResponseEntity<User> add(@RequestBody User user) {
        try {
            return new ResponseEntity<>(service.add(user), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    private ResponseEntity<User> updateEmployee(@RequestBody User user) {
        if (user != null) {
            return new ResponseEntity<>(service.update(user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @DeleteMapping("/delete/{username}")
//    private ResponseEntity<Boolean> addEmployee(@PathVariable String username) {
//        try {
//            Account account = service.deleteByUsername(username);
//            if (account.getUsername() == null) {
//                return new ResponseEntity<>(false, HttpStatus.NO_CONTENT);
//            }
//            return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
