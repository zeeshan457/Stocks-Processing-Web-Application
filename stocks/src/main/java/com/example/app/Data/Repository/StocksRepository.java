package com.example.app.Data.Repository;

import com.example.app.Data.Entity.StocksEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StocksRepository extends JpaRepository<StocksEntity, Integer> {

    StocksRepository findAllByIdBetween(Integer id, Integer id2);
}
