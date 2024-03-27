package com.example.splitwise.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CustomProblem> handleNotFoundException(NotFoundException ex) {
        CustomProblem problem = new CustomProblem("Not Found", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<CustomProblem> handleGenericException(Exception ex) {
        CustomProblem problem = new CustomProblem("Internal Server Error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problem);
    }

    @Getter
    static class CustomProblem {
        private final String title;
        private final String detail;

        public CustomProblem(String title, String detail) {
            this.title = title;
            this.detail = detail;
        }
    }
}
