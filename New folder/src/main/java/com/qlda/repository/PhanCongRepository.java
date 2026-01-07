package com.qlda.repository;

import com.qlda.model.DeTai;
import com.qlda.model.PhanCong;
import com.qlda.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhanCongRepository extends JpaRepository<PhanCong, Long> {
    List<PhanCong> findBySinhVien(User sinhVien);
    List<PhanCong> findByDeTai(DeTai deTai);
    List<PhanCong> findByGiangVienHuongDan(User giangVien);
    List<PhanCong> findByGiangVienPhanBien(User giangVien);
    Optional<PhanCong> findBySinhVienAndDeTai(User sinhVien, DeTai deTai);
    boolean existsBySinhVienAndDeTai(User sinhVien, DeTai deTai);
}



