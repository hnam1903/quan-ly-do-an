package com.qlda.service;

import com.qlda.model.DeTai;
import com.qlda.model.PhanCong;
import com.qlda.model.User;
import com.qlda.repository.PhanCongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PhanCongService {
    @Autowired
    private PhanCongRepository phanCongRepository;

    public List<PhanCong> getPhanCongBySinhVien(User sinhVien) {
        return phanCongRepository.findBySinhVien(sinhVien);
    }

    public List<PhanCong> getPhanCongByDeTai(DeTai deTai) {
        return phanCongRepository.findByDeTai(deTai);
    }

    public List<PhanCong> getPhanCongByGiangVienHuongDan(User giangVien) {
        return phanCongRepository.findByGiangVienHuongDan(giangVien);
    }

    public PhanCong savePhanCong(PhanCong phanCong) {
        return phanCongRepository.save(phanCong);
    }

    public PhanCong updatePhanCong(PhanCong phanCong) {
        return phanCongRepository.save(phanCong);
    }

    public Optional<PhanCong> getPhanCongBySinhVienAndDeTai(User sinhVien, DeTai deTai) {
        return phanCongRepository.findBySinhVienAndDeTai(sinhVien, deTai);
    }

    public List<PhanCong> getAllPhanCong() {
        return phanCongRepository.findAll();
    }

    public PhanCong getPhanCongById(Long id) {
        return phanCongRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Phân công không tồn tại"));
    }
}

