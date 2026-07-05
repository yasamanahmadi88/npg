package ix.portal.npg.service.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import jakarta.validation.constraints.*;

/**
 * A DTO for the {@link ix.portal.npg.domain.EventLogEntity} entity.
 */
public class EventLogDTO implements Serializable {

    private Long id;

    @Size(max = 50)
    private String conversationId;

    @Size(max = 50)
    private String sender;

    @Size(max = 50)
    private String receiver;

    @Size(max = 255)
    private String message;

    private String requestBody;

    private String responseBody;

    @NotNull
    private Integer flg0Ordinary1Exception;

    @NotNull
    @Size(max = 255)
    private String eventSource;

    private String exceptionBody;

    @Size(max = 50)
    private String httpStatus;

    @NotNull
    private LocalDateTime insertTimestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public Integer getFlg0Ordinary1Exception() {
        return flg0Ordinary1Exception;
    }

    public void setFlg0Ordinary1Exception(Integer flg0Ordinary1Exception) {
        this.flg0Ordinary1Exception = flg0Ordinary1Exception;
    }

    public String getEventSource() {
        return eventSource;
    }

    public void setEventSource(String eventSource) {
        this.eventSource = eventSource;
    }

    public String getExceptionBody() {
        return exceptionBody;
    }

    public void setExceptionBody(String exceptionBody) {
        this.exceptionBody = exceptionBody;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }

    public LocalDateTime getInsertTimestamp() {
        return insertTimestamp;
    }

    public void setInsertTimestamp(LocalDateTime insertTimestamp) {
        this.insertTimestamp = insertTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventLogDTO)) {
            return false;
        }

        EventLogDTO eventLogDTO = (EventLogDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, eventLogDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventLogDTO{" +
            "id=" + getId() +
            ", conversationId='" + getConversationId() + "'" +
            ", sender='" + getSender() + "'" +
            ", receiver='" + getReceiver() + "'" +
            ", message='" + getMessage() + "'" +
            ", requestBody='" + getRequestBody() + "'" +
            ", responseBody='" + getResponseBody() + "'" +
            ", flg0Ordinary1Exception=" + getFlg0Ordinary1Exception() +
            ", eventSource='" + getEventSource() + "'" +
            ", exceptionBody='" + getExceptionBody() + "'" +
            ", httpStatus='" + getHttpStatus() + "'" +
            ", insertTimestamp='" + getInsertTimestamp() + "'" +
            "}";
    }
}


