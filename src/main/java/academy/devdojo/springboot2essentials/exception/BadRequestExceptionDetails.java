package academy.devdojo.springboot2essentials.exception;

import java.time.LocalDateTime;

public class BadRequestExceptionDetails extends ExceptionDetails{
    private String title;
    private int status;
    private String details;
    private String developerMessage;
    private LocalDateTime timestamp;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }
    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    private BadRequestExceptionDetails(){

    }

    public static final class Builder{
        private String title;
        private int status;
        private String details;
        private LocalDateTime timestamp;
        private String developerMessage;

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

        public  BadRequestExceptionDetails build(){
            BadRequestExceptionDetails badRequestExceptionDetails = new BadRequestExceptionDetails();
            badRequestExceptionDetails.setDeveloperMessage(developerMessage);
            badRequestExceptionDetails.setTitle    (title);
            badRequestExceptionDetails.setDetails  (details);
            badRequestExceptionDetails.setTimestamp(timestamp);
            badRequestExceptionDetails.setStatus   (status);
            return badRequestExceptionDetails;
        }
    }
}
