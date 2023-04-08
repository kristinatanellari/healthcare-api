package main.healthcare.api.service;

import io.jsonwebtoken.Jwt;
import main.healthcare.api.dto.PharmacistDTO;
import main.healthcare.api.exception.UserNotFoundException;
import main.healthcare.api.exception.ValidateDataException;
import main.healthcare.api.model.Pharmacist;
import main.healthcare.api.model.Users;
import main.healthcare.api.repository.PharmacistRepository;
import main.healthcare.api.repository.UserRepository;
import main.healthcare.api.security.JwtFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PharmacistService {

    static Logger logger = LoggerFactory.getLogger(PatientsService.class);

    @Autowired
    private UsersService usersService;
    @Autowired
    private PharmacistRepository pharmacistRepository;

    @Autowired
    private UserRepository userRepository;


    public Pharmacist registerPharmacist(PharmacistDTO pharmacistDTO) throws ValidateDataException {
        Pharmacist pharmacist = pharmacistRepository.findByEmail(pharmacistDTO.getEmail());
        if (pharmacist != null) {
            logger.info("Pharmacist with this email address already exists.");
            throw new ValidateDataException("Account already exists with this email address!");
        }

        pharmacist = pharmacistRepository.findByUsername(pharmacistDTO.getUsername());
        if (pharmacist != null) {
            logger.info("Pharmacist with this username already exists.");
            throw new ValidateDataException("Username is already taken! Please choose another username!");
        }

        Pharmacist newPharmacist = new Pharmacist();
        if (checkCreateValidation(pharmacistDTO)) {
            setData(newPharmacist, pharmacistDTO);

            logger.info("New pharmacist is created successfully!");
            usersService.registerUser(pharmacistDTO.getUsername(), pharmacistDTO.getPassword(), "PHARMACIST");

            return pharmacistRepository.save(newPharmacist);
        } else {
            logger.info("ERROR! Please check if pharmacist data are correct!");
            throw new ValidateDataException("Account is not created. Check if data is correct!");
        }
    }

    public Pharmacist updatePharmacistData(PharmacistDTO pharmacistDTO) throws ValidateDataException, UserNotFoundException {
        Pharmacist oldPharmacist = pharmacistRepository.findByPharmacistId(JwtFilter.id);
        if (oldPharmacist == null) {
            throw new UserNotFoundException("User with this id doesn't exist!");
        }
        Pharmacist pharmacist = pharmacistRepository.findByEmail(pharmacistDTO.getEmail());
        if (pharmacist != null && !Objects.equals(pharmacistDTO.getEmail(), oldPharmacist.getEmail())) {
            logger.info("Pharmacist with this email address already exists.");
            throw new ValidateDataException("Account already exists with this email address!");
        }

        pharmacist = pharmacistRepository.findByUsername(pharmacistDTO.getUsername());
        if (pharmacist != null && !Objects.equals(pharmacistDTO.getUsername(), oldPharmacist.getUsername())) {
            logger.info("Pharmacist with this username already exists.");
            throw new ValidateDataException("Username is already taken! Please choose another username!");
        }

        if (checkCreateValidation(pharmacistDTO)) {
            String oldUsername = oldPharmacist.getUsername();
            System.out.println("OLD is = " + oldPharmacist.getUsername());
            setData(oldPharmacist, pharmacistDTO);

            logger.info("Data is updated successfully!");
            usersService.updateUser(oldUsername, pharmacistDTO.getUsername(), pharmacistDTO.getPassword(), "PHARMACIST");
            return pharmacistRepository.save(oldPharmacist);
        } else {
            logger.info("ERROR! Please check if pharmacist data are correct!");
            throw new ValidateDataException("Data is not updated. Check if data is correct!");
        }
    }

    private void setData(Pharmacist newPharmacist, PharmacistDTO pharmacistDTO) {
        newPharmacist.setName(pharmacistDTO.getName());
        newPharmacist.setLastname(pharmacistDTO.getLastname());
        newPharmacist.setEmail(pharmacistDTO.getEmail());
        newPharmacist.setPharmacyAddress(pharmacistDTO.getPharmacyAddress());
        newPharmacist.setPhoneNumber(pharmacistDTO.getPhoneNumber());
        newPharmacist.setUsername(pharmacistDTO.getUsername());
        newPharmacist.setPassword(pharmacistDTO.getPassword());
    }
    private boolean checkCreateValidation(PharmacistDTO pharmacistDTO) {
        if (Objects.equals(pharmacistDTO.getName(), "")) {
            return false;
        } else if (Objects.equals(pharmacistDTO.getLastname(), "")) {
            return false;
        } else if (Objects.equals(pharmacistDTO.getPharmacyAddress(), "")) {
            return false;
        }  else if (Objects.equals(pharmacistDTO.getPhoneNumber(), "")) {
            return false;
        } else if (Objects.equals(pharmacistDTO.getUsername(), "")) {
            return false;
        } else if (Objects.equals(pharmacistDTO.getPassword(), "")) {
            return false;
        } else return !Objects.equals(pharmacistDTO.getEmail(), "");
    }

    public void deleteMyAccount() {
        Pharmacist pharmacist = pharmacistRepository.findByPharmacistId(JwtFilter.id);
        Users user = userRepository.findByUsername(pharmacist.getUsername());
        userRepository.delete(user);
        pharmacistRepository.delete(pharmacist);
    }
}
