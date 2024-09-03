package com.example.finalhomework.service;

import com.example.finalhomework.domain.Question;
import com.example.finalhomework.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    private final S3Service s3Service;

    public QuestionService(QuestionRepository questionRepository, S3Service s3Service) {
        this.questionRepository = questionRepository;
        this.s3Service = s3Service;
    }


    // create
    public void create(Question question, MultipartFile file) throws IOException {

        // 기본 사진이름을 uuid로 처리하기
        UUID uuid = UUID.randomUUID();
        String filename = uuid + "_" + file.getOriginalFilename();
        s3Service.uploadFile(file, filename);

        // 객체에 저장
        question.setImg(filename);
        question.setDate(LocalDateTime.now());

        questionRepository.save(question);
    }

    // readlist
    public List<Question> readlist() {
        return questionRepository.findAll();
    }

    // readdetail
    public Question readdetail(Integer id) {
        Optional<Question> question = questionRepository.findById(id);
        return question.orElse(null);
    }

    // update
    public void update(Question question, MultipartFile file) throws IOException {

        if (file.isEmpty()) {// 사진을 새로 넣지 않음
            question.setImg(question.getImg());
        } else { //기존 사진을 그대로 쓰지 않고 새 사진을 넣은 경우
            UUID uuid = UUID.randomUUID();
            String filename = uuid + "_" + file.getOriginalFilename();
            s3Service.uploadFile(file, filename);
            question.setImg(filename);
        }
        // create도 save 사용하지만 question의 id가 이미 저장되어 있어서 그걸로 구분됨
        questionRepository.save(question);
    }

    // delete
    public void delete(Integer id) {
        questionRepository.deleteById(id);
    }
}
