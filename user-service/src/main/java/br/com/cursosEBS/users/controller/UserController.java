package br.com.cursosEBS.users.controller;

import br.com.cursosEBS.users.dto.*;
import br.com.cursosEBS.users.enums.StatusPayment;
import br.com.cursosEBS.users.service.ResetPasswordService;
import br.com.cursosEBS.users.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private ResetPasswordService resetPasswordService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/register")
    public ResponseEntity<UserViewDTO> createUser(@RequestBody @Valid UserRegisterDTO user, UriComponentsBuilder uriBuilder) {
        var users = service.createUser(user);
        URI uri = uriBuilder.path("/user/{id}").buildAndExpand(users.id()).toUri();

        return ResponseEntity.created(uri).body(users);
    }

    @GetMapping
    public ResponseEntity<PageDTO<ListUserViewDTO>> getListUsers(@PageableDefault(page = 0, size = 5) Pageable page, @RequestParam(required = false) String search) {
        return ResponseEntity.ok(service.getListUsers(page, search));
    }

    @GetMapping("/instructor")
    public ResponseEntity<Page<InstructorDTO>> getListInstructor(@PageableDefault(size = 50, sort = "name") Pageable pageable, @RequestParam String instructor) {
        return ResponseEntity.ok(service.getListInstructor(pageable, instructor));
    }

    @GetMapping("/instructor/{id}")
    public ResponseEntity<InstructorDTO> getInstructorById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getInstructorById(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserViewDTO> getListUsers(@PathVariable @NotNull Long id) {
        return ResponseEntity.ok(service.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserViewDTO> updateUser(@PathVariable @NotNull Long id, @RequestBody @Valid UserRegisterDTO dto){
        return ResponseEntity.ok().body(service.updateUser(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserRegisterDTO> deleteUserByid(@PathVariable @NotNull Long id) {
        service.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/resetPassword")
    public ResponseEntity<EmailDTO> resetPassword(@RequestParam String username){
        System.out.println(username.toLowerCase());
        var user = resetPasswordService.resetPassword(username.toLowerCase());

        rabbitTemplate.convertAndSend("email.ex","", user);

        return ResponseEntity.ok(user);
    }

    @PostMapping("/changePassword")
    public ResponseEntity changePassword(@RequestBody ChangePasswordDTO dto){
        resetPasswordService.changePassword(dto);
        return ResponseEntity.ok().build();
    }

    /*@PutMapping("/registered/user")
    public void registeredUser(@RequestBody WebHookDTO dto) {
        service.registeredUser(dto);
    }*/

    @PatchMapping("/update-profile")
    public ResponseEntity updateProfile(UpdateProfileDTO dto) {
        service.updateProfile(dto);
        return ResponseEntity.noContent().build();
    }


}
