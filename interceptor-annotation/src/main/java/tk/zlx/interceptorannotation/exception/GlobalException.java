package tk.zlx.interceptorannotation.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(value = MyException.class)
    public String myException(MyException e){
        return e.getMessage();
    }
}
