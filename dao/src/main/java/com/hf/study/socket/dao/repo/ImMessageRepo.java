package com.hf.study.socket.dao.repo;

import com.hf.study.socket.dao.entity.ImMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImMessageRepo extends JpaRepository<ImMessage, Long> {

    List<ImMessage> findAllByToUserId(@Param("toUserId") Long toUserId);

    List<ImMessage> findAllByToUserIdAndReceiveStatus(Long toUserId, Integer receiveStatus);
}
