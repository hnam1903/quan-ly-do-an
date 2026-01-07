package com.qlda.repository;

import com.qlda.model.Diem;
import com.qlda.model.PhanCong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiemRepository extends JpaRepository<Diem, Long> {
    Optional<Diem> findByPhanCong(PhanCong phanCong);
}



