package com.zeyza.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.zeyza.auth.entity.Base;

@NoRepositoryBean
public interface BaseRepository<T extends Base, ID> extends JpaRepository<T, ID> {
    Optional<T> findById(ID id);
}
