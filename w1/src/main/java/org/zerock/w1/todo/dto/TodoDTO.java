package org.zerock.w1.todo.dto;

import java.time.LocalDate;

//dto 멤버변수와 getter/setter, toString()을 구현함.
//DTO(Data Transfer Object) :
// 단순히 여러 개의 데이터를 묶어서 하나의 객체를 구성하는 용도로 사용, 주로 서비스 객체 메소드들의 파라미터나 리턴 타입으로 사용됨.
public class TodoDTO {

    private Long tno;
    private String title;
    private LocalDate dueDate;
    private boolean finished;

    public Long getTno(){
        return tno;
    }
    public void setTno(Long tno){
        this.tno = tno;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public LocalDate getDueDate(){
        return dueDate;
    }
    public void setDueDate(LocalDate dueDate){
        this.dueDate = dueDate;
    }
    public boolean isFinished(){
        return finished;
    }
    public void setFinished(boolean finished){
        this.finished = finished;
    }

    public String toString(){
        return "TodoDTO{" +
                "tno=" + tno +
                ", title='" +title + '\''+
                ", dueDate=" + dueDate +
                ", finished=" + finished +
                '}';
    }
}
