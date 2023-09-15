package com.hf.study.socket.dao.repo;

import com.hf.study.socket.dao.entity.ImMessage;
import com.hf.study.socket.dao.entity.ImUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImUserRepo extends JpaRepository<ImUser, Long> {
}
