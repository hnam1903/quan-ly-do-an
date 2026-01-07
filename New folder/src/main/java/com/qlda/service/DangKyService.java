package com.qlda.service;

import com.qlda.model.DangKy;
import com.qlda.model.DeTai;
import com.qlda.model.PhanCong;
import com.qlda.model.TrangThaiDangKy;
import com.qlda.model.User;
import com.qlda.repository.DangKyRepository;
import com.qlda.repository.PhanCongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DangKyService {
    @Autowired
    private DangKyRepository dangKyRepository;

    @Autowired
    private PhanCongRepository phanCongRepository;

    public List<DangKy> getDangKyBySinhVien(User sinhVien) {
        return dangKyRepository.findBySinhVien(sinhVien);
    }

    public List<DangKy> getDangKyByDeTai(DeTai deTai) {
        return dangKyRepository.findByDeTai(deTai);
    }

    public List<DangKy> getDangKyByDeTaiAndTrangThai(DeTai deTai, TrangThaiDangKy trangThai) {
        return dangKyRepository.findByDeTaiAndTrangThai(deTai, trangThai);
    }

    public DangKy saveDangKy(DangKy dangKy) {
        return dangKyRepository.save(dangKy);
    }

    public void duyetDangKy(Long dangKyId) {
        DangKy dangKy = dangKyRepository.findById(dangKyId)
                .orElseThrow(() -> new RuntimeException("Đăng ký không tồn tại"));
        
        dangKy.setTrangThai(TrangThaiDangKy.DUOC_DUYET);
        dangKyRepository.save(dangKy);


        if (!phanCongRepository.existsBySinhVienAndDeTai(dangKy.getSinhVien(), dangKy.getDeTai())) {
            PhanCong phanCong = new PhanCong();
            phanCong.setSinhVien(dangKy.getSinhVien());
            phanCong.setDeTai(dangKy.getDeTai());
            phanCong.setGiangVienHuongDan(dangKy.getDeTai().getGiangVien());
            phanCongRepository.save(phanCong);
        }
    }

    public void tuChoiDangKy(Long dangKyId) {
        DangKy dangKy = dangKyRepository.findById(dangKyId)
                .orElseThrow(() -> new RuntimeException("Đăng ký không tồn tại"));
        dangKy.setTrangThai(TrangThaiDangKy.TU_CHOI);
        dangKyRepository.save(dangKy);
    }

    public boolean existsBySinhVienAndDeTai(User sinhVien, DeTai deTai) {
        return dangKyRepository.existsBySinhVienAndDeTai(sinhVien, deTai);
    }
}



