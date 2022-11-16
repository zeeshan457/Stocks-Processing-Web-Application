package com.example.app.Data.Repository;

import com.example.app.Data.Entity.StocksEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StocksRepository extends JpaRepository<StocksEntity, Integer> {

    // Getting all symbols from GPA table
    @Query("SELECT s.Symbol FROM StocksEntity s")
    Page<StocksEntity> getSymbols(Pageable pageable);

}
