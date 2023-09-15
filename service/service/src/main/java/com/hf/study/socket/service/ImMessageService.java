package com.hf.study.socket.service;

import com.hf.study.socket.dao.entity.ImMessage;
import com.hf.study.socket.dao.repo.ImMessageRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImMessageService {

    private final ImMessageRepo messageRepo;

    public void save(ImMessage imMessage) {
        messageRepo.save(imMessage);
        log.info("消息保存成功:{}", imMessage);
    }

    public List<ImMessage> findOfflineMessage(Long toUserId) {
        return messageRepo.findAllByToUserIdAndReceiveStatus(toUserId, 0);
    }
}
