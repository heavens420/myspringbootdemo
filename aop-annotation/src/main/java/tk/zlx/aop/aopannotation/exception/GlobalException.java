package tk.zlx.aop.aopannotation.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(value = AOPException.class)
    public String aopException(AOPException e){
        return e.getMessage();
    }
}
