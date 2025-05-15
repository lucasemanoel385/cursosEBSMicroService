package br.com.cursosEBS.users.service;

import br.com.cursosEBS.users.dto.*;
import br.com.cursosEBS.users.entity.Profile;
import br.com.cursosEBS.users.entity.Role;
import br.com.cursosEBS.users.entity.User;
import br.com.cursosEBS.users.enums.StatusPayment;
import br.com.cursosEBS.users.infra.exceptions.ValidationException;
import br.com.cursosEBS.users.repository.PasswordTokenRepository;
import br.com.cursosEBS.users.repository.ProfileRepository;
import br.com.cursosEBS.users.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ProfileRepository repositoryProfile;

    @Autowired
    private PasswordTokenRepository repositoryPassword;

    @Autowired
    private PasswordEncoder encoder;

    @Transactional
    public UserViewDTO createUser(UserRegisterDTO user) {

        if (repository.existsByEmail(user.email())) {
            throw new ValidationException("Email já existe");
        }

        var profile = user.role() == null ? new Profile(1L) : new Profile(user.role());

        User users = new User(user, profile);
        users.setPassword(encoder.encode(user.password()));
        users.setCreatedAt(LocalDateTime.now());
        repository.save(users);

        return new UserViewDTO(users);
    }

    public PageDTO<ListUserViewDTO> getListUsers(Pageable page, String search) {

        Page<ListUserViewDTO> pagee = search == null || search.isEmpty()
                ?
                repository.findAllExcludingRole(Role.ADMIN.toString(), page).map(ListUserViewDTO::new)
                :
                repository.findAllExcludingRoleAndSearch(Role.ADMIN.toString(), page, search).map(ListUserViewDTO::new);

        return new PageDTO<>(pagee);

    }

    @Transactional
    public UserViewDTO updateUser(Long id, UserRegisterDTO dto) {
        var t = repository.getReferenceById(id);
        User user = new User(dto, new Profile(dto.role()));
        user.setCreatedAt(t.getCreatedAt());
        user.setId(id);
        user = repository.save(user);
        return new UserViewDTO(user);
    }

    public UserViewDTO getUserById(Long id) {
        return new UserViewDTO(repository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Transactional
    public void deleteUserById(Long id) {
        repository.deleteById(id);
    }

    public Page<InstructorDTO> getListInstructor(Pageable pageable, String instructor) {
        return repository.findAllByRole(pageable, Role.INSTRUCTOR.toString(), instructor).map(InstructorDTO::new);
    }

    public InstructorDTO getInstructorById(Long id) {
        return new InstructorDTO(repository.findByIdWithProfileEqualsInstructor(id, Role.INSTRUCTOR.toString()).orElseThrow(EntityNotFoundException::new));
    }

    @Transactional
    public void updateProfile(UpdateProfileDTO dto) {

        var user = repository.findById(dto.id()).orElseThrow(EntityNotFoundException::new);

        var newUser = new User(user);

        if(dto.status().equals(StatusPayment.APPROVED)) {
            newUser.setProfile(List.of(new Profile(2L)));
            repository.save(newUser);
        }
        if(dto.status().equals(StatusPayment.REFUNDED)) {
            newUser.setProfile(List.of(new Profile(1L)));
            repository.save(newUser);
        }
    }
}
