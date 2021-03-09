package kz.edu.controller;

import kz.edu.dao.QuestionDAO;
import kz.edu.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/questions")
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
        model.addAttribute("questionList", questionDAO.getQuestionList());
        return "questions";
    }

    @GetMapping("/{id}")
    public String question(@PathVariable("id") int id, Model model)
    {
        model.addAttribute("question", questionDAO.getQuestion(id));
        return "question";
    }
    @PatchMapping("/{option}/{id}")
    public String votePatch(@PathVariable("id") int id, @PathVariable("option") int option)
    {
        questionDAO.vote(id, option);
        return "redirect:/questions/"+id;
    }

    @GetMapping("/new")
    public String addQuestionGet(Model model)
    {
        model.addAttribute("question", new Question());
        return "new-question";
    }
    @PostMapping()
    public String addQuestionPost(@ModelAttribute("question") Question question)
    {
        questionDAO.addQuestion(question);
        return "redirect:/questions/"+question.getQuestion_id();
    }

    @GetMapping("/edit/{id}")
    public String updateQuestion(@PathVariable("id") int id, Model model)
    {
        model.addAttribute("question", questionDAO.getQuestion(id));
        return "edit-question";
    }
    @PatchMapping("/{id}")
    public String updateQuestionPatch(@ModelAttribute("question") Question question, @PathVariable("id") int id)
    {
        questionDAO.updateQuestion(question, id);
        return "redirect:/questions/"+id;
    }

    @GetMapping("/delete/{id}")
    public String deleteQuestion(@PathVariable("id") int id, Model model)
    {
        model.addAttribute("question", questionDAO.getQuestion(id));
        return "delete-question";
    }
    @DeleteMapping("/{id}")
    public String deleteQuestionPatch(@PathVariable("id") int id)
    {
        questionDAO.deleteQuestion(id);
        return "redirect:/questions";
    }
}
