package ix.portal.npg.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EventLogEntity.
 */
@Entity
@Table(name = "TBL_EVENT_LOG")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EventLogEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SEQ_EVENT_LOG_ID_GENERATOR", sequenceName = "SEQ_EVENT_LOG_ID", allocationSize = 0)
    @GeneratedValue(generator = "SEQ_EVENT_LOG_ID_GENERATOR")
    private Long id;

    @Size(max = 50)
    @Column(name = "conversation_id", length = 50)
    private String conversationId;

    @Size(max = 50)
    @Column(name = "sender", length = 50)
    private String sender;

    @Size(max = 50)
    @Column(name = "receiver", length = 50)
    private String receiver;

    @Size(max = 255)
    @Column(name = "message", length = 255)
    private String message;

    @Column(name = "request_body")
    private String requestBody;

    @Column(name = "response_body")
    private String responseBody;

    @NotNull
    @Column(name = "flg_0ordinary_1exception", nullable = false)
    private Integer flg0Ordinary1Exception;

    @NotNull
    @Size(max = 255)
    @Column(name = "event_source", length = 255, nullable = false)
    private String eventSource;

    @Column(name = "exception_body")
    private String exceptionBody;

    @Size(max = 50)
    @Column(name = "http_status", length = 50)
    private String httpStatus;

    @NotNull
    @Column(name = "insert_timestamp", nullable = false)
    private LocalDateTime insertTimestamp;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventLogEntity id(Long id) {
        this.id = id;
        return this;
    }

    public String getConversationId() {
        return this.conversationId;
    }

    public EventLogEntity conversationId(String conversationId) {
        this.conversationId = conversationId;
        return this;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getSender() {
        return this.sender;
    }

    public EventLogEntity sender(String sender) {
        this.sender = sender;
        return this;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return this.receiver;
    }

    public EventLogEntity receiver(String receiver) {
        this.receiver = receiver;
        return this;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return this.message;
    }

    public EventLogEntity message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestBody() {
        return this.requestBody;
    }

    public EventLogEntity requestBody(String requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getResponseBody() {
        return this.responseBody;
    }

    public EventLogEntity responseBody(String responseBody) {
        this.responseBody = responseBody;
        return this;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public Integer getFlg0Ordinary1Exception() {
        return this.flg0Ordinary1Exception;
    }

    public EventLogEntity flg0Ordinary1Exception(Integer flg0Ordinary1Exception) {
        this.flg0Ordinary1Exception = flg0Ordinary1Exception;
        return this;
    }

    public void setFlg0Ordinary1Exception(Integer flg0Ordinary1Exception) {
        this.flg0Ordinary1Exception = flg0Ordinary1Exception;
    }

    public String getEventSource() {
        return this.eventSource;
    }

    public EventLogEntity eventSource(String eventSource) {
        this.eventSource = eventSource;
        return this;
    }

    public void setEventSource(String eventSource) {
        this.eventSource = eventSource;
    }

    public String getExceptionBody() {
        return this.exceptionBody;
    }

    public EventLogEntity exceptionBody(String exceptionBody) {
        this.exceptionBody = exceptionBody;
        return this;
    }

    public void setExceptionBody(String exceptionBody) {
        this.exceptionBody = exceptionBody;
    }

    public String getHttpStatus() {
        return this.httpStatus;
    }

    public EventLogEntity httpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }

    public LocalDateTime getInsertTimestamp() {
        return this.insertTimestamp;
    }

    public EventLogEntity insertTimestamp(LocalDateTime insertTimestamp) {
        this.insertTimestamp = insertTimestamp;
        return this;
    }

    public void setInsertTimestamp(LocalDateTime insertTimestamp) {
        this.insertTimestamp = insertTimestamp;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventLogEntity)) {
            return false;
        }
        return id != null && id.equals(((EventLogEntity) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventLogEntity{" +
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


