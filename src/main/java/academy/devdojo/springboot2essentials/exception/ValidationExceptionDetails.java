package academy.devdojo.springboot2essentials.exception;

import java.time.LocalDateTime;

public class ValidationExceptionDetails extends ExceptionDetails {
    private String field;
    private String fieldMessage;

    public void setField(String field){
        this.field = field;
    }
    public String getField(){
        return field;
    }

    public void setFieldMessage(String fieldMessage){
        this.fieldMessage = fieldMessage;
    }
    public String getFieldMessage(){
        return fieldMessage;
    }

    public static final class Builder{
        private String title;
        private int status;
        private String details;
        private LocalDateTime timestamp;
        private String developerMessage;
        private String field;
        private String fieldMessage;

        private Builder (){
        }

        public static Builder newBuilder(){
            return new Builder();
        }

        public Builder title(String title){
            this.title = title;
            return this;
        }

        public Builder status(int status){
            this.status = status;
            return this;
        }

        public Builder details(String details){
            this.details = details;
            return this;
        }

        public Builder timestamp(LocalDateTime timestamp){
            this.timestamp = timestamp;
            return this;
        }

        public Builder developerMessage(String developerMessage){
            this.developerMessage = developerMessage;
            return this;
        }

        public Builder field(String field){
            this.field = field;
            return this;
        }
        
        public Builder fieldMessage(String fieldMessage){
            this.fieldMessage = fieldMessage;
            return this;
        }

        public  ValidationExceptionDetails build(){
            ValidationExceptionDetails validationExceptionDetails = new ValidationExceptionDetails();
            validationExceptionDetails.setDeveloperMessage(developerMessage);
            validationExceptionDetails.setTitle       (title);
            validationExceptionDetails.setDetails     (details);
            validationExceptionDetails.setTimestamp   (timestamp);
            validationExceptionDetails.setStatus      (status);
            validationExceptionDetails.field = field;
            validationExceptionDetails.fieldMessage = fieldMessage;
            return validationExceptionDetails;
        }
    }
}
