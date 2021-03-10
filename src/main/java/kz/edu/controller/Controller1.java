package kz.edu.controller;

import kz.edu.dao.QuestionDAO;
import kz.edu.dao.UserDAO;
import kz.edu.model.Question;
import kz.edu.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;


@Controller
@RequestMapping("/questions")
public class Controller1 {
    LinkedList<String> answered = new LinkedList<>();
    Boolean executed = false;

    private final QuestionDAO questionDAO;
    private final UserDAO userDAO;
    @Autowired
    public Controller1(QuestionDAO questionDAO, UserDAO userDAO) {
        this.questionDAO = questionDAO;
        this.userDAO = userDAO;
    }

    @GetMapping()
    public String helloPage(Model model) throws InterruptedException {
        model.addAttribute("questionList", questionDAO.getQuestionList());

        if (!executed) {
            String currentUserName = null;
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                currentUserName = authentication.getName();
            }
            User user = userDAO.findByUserName(currentUserName);

            List<Question> list = questionDAO.getQuestionList();

            First fnew = new First();

            answered.clear();

            for (int i = 0; i<list.size(); i++) {
                Second ss = new Second(fnew, list.get(i).getAnswered(), (int) user.getId(), list.get(i).getQuestion_id());
                ss.join();
                System.out.println("Result: " + ss.result);
                if (ss.result!=null) answered.add(ss.result);
            }
        }

        executed = true;
        return "questions";
    }

    @GetMapping("/{id}")
    public String question(@PathVariable("id") int id, Model model){
        model.addAttribute("question", questionDAO.getQuestion(id));

        System.out.println(answered);
        model.addAttribute("answered", answered);
        return "question";
    }
    @PatchMapping("/{option}/{id}")
    public String votePatch(@PathVariable("id") int id, @PathVariable("option") int option) {
        answered.add(""+id);

        String currentUserName = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = authentication.getName();
        }
        User user = userDAO.findByUserName(currentUserName);

        questionDAO.vote(id, option, (int) user.getId());
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


class First
{
    synchronized public String check(String msg, int id, int qid)
    {
        String[] row;

        if (msg.equals("1")) {
            row = new String[1];
            row[0] = msg;
        } else {
            row = msg.split(",");
        }

        for (int i=0; i<row.length; i++) {
            if (row[i].equals(Integer.toString(id))) {
                return ""+qid;
            }
        }

        try
        {
            Thread.sleep(1000);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }


        return null;
    }
}

class Second extends Thread
{
    String msg;
    int id;
    int qid;
    First fobj;
    String result;
    Second (First fp,String str, int id, int qid)
    {
        fobj = fp;
        msg = str;
        this.id = id;
        this.qid = qid;
        start();
    }
    public void run()
    {
        result = fobj.check(msg, id, qid);
    }
}
