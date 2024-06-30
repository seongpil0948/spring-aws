package com.sixplus.server.api.user.service;

import com.sixplus.server.api.user.domain.TB_QUEUE;
import com.sixplus.server.api.user.repository.QueueRepository;
import com.sixplus.server.api.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QueueRepository queueRepository;

    public String registerUser(String id, String password, String gender, String userName, String displayName, String email, String phone, String avatar) {
        if (userRepository.existsById(id)) {
            return "회원가입 실패: 이미 존재하는 사용자입니다.";
        }

        if (queueRepository.existsById(id)) {
            long queuePosition = queueRepository.countByGender(gender) + 1;
            return String.format("당신은 %s 대기열 %d 번째 대기열에 위치합니다", gender.equals("male") ? "남자" : "여자", queuePosition);
        }

        // Add to queue
        TB_QUEUE newQueueEntry = new TB_QUEUE();
        newQueueEntry.setId(id);
        newQueueEntry.setPassword(password);
        newQueueEntry.setGender(gender);
        queueRepository.save(newQueueEntry);

        long queuePosition = queueRepository.countByGender(gender);
        return String.format("당신은 %s 대기열 %d 번째 대기열에 위치합니다", gender.equals("male") ? "남자" : "여자", queuePosition);
    }
}