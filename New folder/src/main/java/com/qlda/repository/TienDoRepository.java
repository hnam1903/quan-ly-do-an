package com.qlda.repository;

import com.qlda.model.PhanCong;
import com.qlda.model.TienDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TienDoRepository extends JpaRepository<TienDo, Long> {
    List<TienDo> findByPhanCongOrderByCreatedAtDesc(PhanCong phanCong);
}



