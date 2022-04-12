package academy.devdojo.springboot2essentials.exception;

import java.time.LocalDateTime;

public class ExceptionDetails {
    protected String title;
    protected int status;
    protected String details;
    protected String developerMessage;
    protected LocalDateTime timestamp;

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

    public ExceptionDetails(){

    }

    @Override
    public String toString() {
        return "ExceptionsDetails " +
		"title='" + title + '\'' +
		", status='" + status + '\'' +
        ", details='" + details + '\'' +
        ", developerMessage='" + developerMessage + '\'' +
        ", timestamp='" + timestamp + '\'' +
		'}';
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

        public ExceptionDetails build(){
            ExceptionDetails exceptionsDetails = new ExceptionDetails();
            exceptionsDetails.setDeveloperMessage(developerMessage);
            exceptionsDetails.setTitle    (title);
            exceptionsDetails.setDetails  (details);
            exceptionsDetails.setTimestamp(timestamp);
            exceptionsDetails.setStatus   (status);
            return exceptionsDetails;
        }
    }
}
