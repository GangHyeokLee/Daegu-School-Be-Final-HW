package com.example.finalhomework.controller;

import com.example.finalhomework.domain.Question;
import com.example.finalhomework.service.QuestionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

//@RequestMapping("/question")
@Controller
public class QuestionController {
    private final QuestionService questionService;

    @Value("${cloud.aws.s3.endpoint}")
    private String awsEndPoint;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }


    @GetMapping("/")
    public String index() {
        return "redirect:/readlist";
    }


    // create
    @GetMapping("/create")
    public String create() {
        return "create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Question question,
                         @RequestParam("file") MultipartFile file
    ) throws IOException {
        questionService.create(question, file);
        return "redirect:/readlist";
    }

    // readlist
    @GetMapping("/readlist")
    public String readlist(Model model) {
        model.addAttribute("questions", questionService.readlist());
        model.addAttribute("awsEndPoint", awsEndPoint);

        return "readlist";
    }

    // readdetail
    @GetMapping("/readdetail/{id}")
    public String read(@PathVariable("id") int id, Model model) {
        model.addAttribute("question", questionService.readdetail(id));
        model.addAttribute("awsEndPoint", awsEndPoint);
        return "readdetail";
    }

    // update
    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") int id, Model model) {
        model.addAttribute("question", questionService.readdetail(id));
        return "update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Question question, @RequestParam("file") MultipartFile file) throws IOException {

        questionService.update(question, file);
        return "redirect:/readdetail/" + question.getId();
    }

    // delete
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        questionService.delete(id);
        return "redirect:/readlist";
    }
}
