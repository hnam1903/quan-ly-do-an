package com.qlda.service;

import com.qlda.model.PhanCong;
import com.qlda.model.TienDo;
import com.qlda.repository.TienDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TienDoService {
    @Autowired
    private TienDoRepository tienDoRepository;

    public List<TienDo> getTienDoByPhanCong(PhanCong phanCong) {
        return tienDoRepository.findByPhanCongOrderByTuanAsc(phanCong);
    }

    public TienDo getTienDoByPhanCongAndTuan(PhanCong phanCong, Integer tuan) {
        return tienDoRepository.findByPhanCongAndTuan(phanCong, tuan);
    }

    public TienDo saveTienDo(TienDo tienDo) {
        // Kiểm tra xem đã có tiến độ cho tuần này chưa
        TienDo existing = tienDoRepository.findByPhanCongAndTuan(
            tienDo.getPhanCong(), 
            tienDo.getTuan()
        );
        
        // Mỗi tuần chỉ có một tiến độ
        // Nếu đã có tiến độ cho tuần này và không phải là bản ghi đang chỉnh sửa
        if (existing != null && (tienDo.getId() == null || !existing.getId().equals(tienDo.getId()))) {
            // Cập nhật tiến độ đã có cho tuần này
            existing.setNoiDung(tienDo.getNoiDung());
            existing.setNhanXet(tienDo.getNhanXet());
            // Nếu đang chỉnh sửa một tiến độ khác, xóa nó đi
            if (tienDo.getId() != null && !existing.getId().equals(tienDo.getId())) {
                tienDoRepository.deleteById(tienDo.getId());
            }
            return tienDoRepository.save(existing);
        }
        
        // Nếu chưa có tiến độ cho tuần này, tạo mới hoặc cập nhật
        return tienDoRepository.save(tienDo);
    }
}



