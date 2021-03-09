package kz.edu.controller;

import kz.edu.dao.QuestionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public class Controller1 {

    private final QuestionDAO questionDAO;
    @Autowired
    public Controller1(QuestionDAO questionDAO)
    {
        this.questionDAO = questionDAO;
    }

    @GetMapping()
    public String helloPage(Model model)
    {
        return "questions";
    }

    @GetMapping("/{id}")
    public String book(@PathVariable("id") int id, Model model)
    {
        //model.addAttribute("candidate", candidateDAO.getCandidate(id));
        return "question";
    }

}
