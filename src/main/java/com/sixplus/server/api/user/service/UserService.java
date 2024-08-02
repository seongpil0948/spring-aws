package com.sixplus.server.api.user.service;

import com.sixplus.server.api.user.model.QueueEntity;
import com.sixplus.server.api.user.model.UserEntity;
import com.sixplus.server.api.user.repository.QueueRepository;
import com.sixplus.server.api.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final QueueRepository queueRepository;

    public UserService(QueueRepository queueRepository, UserRepository userRepository) {
        this.queueRepository = queueRepository;
        this.userRepository = userRepository;
    }

    public String registerUser(String id, String password, String gender, String username, String displayName, String email, String phone, String avatar) {
        if (userRepository.existsById(id)) {
            return "hi";
        }

        if (queueRepository.existsById(id)) {
            long queuePosition = queueRepository.countByGender(gender) + 1;
            return String.format("bye", gender.equals("male") ? "Male" : "Female", queuePosition);
        }

        // Add to queue
        QueueEntity newQueueEntry = new QueueEntity();
        newQueueEntry.setPassword(password);
        newQueueEntry.setGender(gender);
        queueRepository.save(newQueueEntry);

        long queuePosition = queueRepository.countByGender(gender);
        return String.format("����� %s ��⿭ %d ��° ��⿭�� ��ġ �մϴ�", gender.equals("male") ? "����" : "����", queuePosition);
    }
}