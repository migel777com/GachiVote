package kz.edu.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "QuestionEntity")
@Table(name = "questions")
public class Question implements Serializable {

    private int question_id;
    private String question_title;
    private String answer_one;
    private int answer_one_count;
    private String answer_two;
    private int answer_two_count;
    private String answer_three;
    private int answer_three_count;
    private String answer_four;
    private int answer_four_count;
    private String answered;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    public int getQuestion_id() {
        return question_id;
    }
    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    @Column(name = "question_title")
    public String getQuestion_title() {
        return question_title;
    }
    public void setQuestion_title(String question_title) {
        this.question_title = question_title;
    }

    @Column(name = "answer_one")
    public String getAnswer_one() {
        return answer_one;
    }
    public void setAnswer_one(String answer_one) {
        this.answer_one = answer_one;
    }

    @Column(name = "answer_one_count")
    public int getAnswer_one_count() {
        return answer_one_count;
    }
    public void setAnswer_one_count(int answer_one_count) {
        this.answer_one_count = answer_one_count;
    }

    @Column(name = "answer_two")
    public String getAnswer_two() {
        return answer_two;
    }
    public void setAnswer_two(String answer_two) {
        this.answer_two = answer_two;
    }

    @Column(name = "answer_two_count")
    public int getAnswer_two_count() {
        return answer_two_count;
    }
    public void setAnswer_two_count(int answer_two_count) {
        this.answer_two_count = answer_two_count;
    }

    @Column(name = "answer_three")
    public String getAnswer_three() {
        return answer_three;
    }
    public void setAnswer_three(String answer_three) {
        this.answer_three = answer_three;
    }

    @Column(name = "answer_three_count")
    public int getAnswer_three_count() {
        return answer_three_count;
    }
    public void setAnswer_three_count(int answer_three_count) {
        this.answer_three_count = answer_three_count;
    }

    @Column(name = "answer_four")
    public String getAnswer_four() {
        return answer_four;
    }
    public void setAnswer_four(String answer_four) {
        this.answer_four = answer_four;
    }

    @Column(name = "answer_four_count")
    public int getAnswer_four_count() {
        return answer_four_count;
    }
    public void setAnswer_four_count(int answer_four_count) {
        this.answer_four_count = answer_four_count;
    }

    @Column(name = "answered")
    public String getAnswered() {
        return answered;
    }
    public void setAnswered(String answered) {
        this.answered = answered;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question_id=" + question_id +
                ", question_title='" + question_title + '\'' +
                ", answer_one='" + answer_one + '\'' +
                ", answer_one_count=" + answer_one_count +
                ", answer_two='" + answer_two + '\'' +
                ", answer_two_count=" + answer_two_count +
                ", answer_three='" + answer_three + '\'' +
                ", answer_three_count=" + answer_three_count +
                ", answer_four='" + answer_four + '\'' +
                ", answer_four_count=" + answer_four_count +
                ", answered='" + answered + '\'' +
                '}';
    }
}
