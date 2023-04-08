package main.healthcare.api.repository;

import main.healthcare.api.model.Pharmacist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacistRepository extends JpaRepository<Pharmacist, Long> {

    public Pharmacist findByEmail(String email);
    public Pharmacist findByUsername(String username);
    public Pharmacist findByPharmacistId(Long id);
}
