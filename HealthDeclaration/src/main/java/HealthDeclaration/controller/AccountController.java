package HealthDeclaration.controller;

import HealthDeclaration.modal.entity.Account;
import HealthDeclaration.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("api/account")
public class AccountController {
    @Autowired
    IAccountService service;

    @GetMapping("/getAll")
    private ResponseEntity getAll() {
        //ResponseMessa
        try {
            List<Account> list = service.getAll();
            if (list.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getOne")
    private ResponseEntity<Account> getByUsername(@RequestParam String username) {
        try {
            Account account = service.getByUsername(username);
            if (account == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(account, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    private ResponseEntity<Account> add(@RequestBody Account account) {
        try {
            return new ResponseEntity<>(service.add(account), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    private ResponseEntity<Account> updateEmployee(@RequestBody Account account) {
        if (account != null) {
            return new ResponseEntity<>(service.update(account), HttpStatus.OK);
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
