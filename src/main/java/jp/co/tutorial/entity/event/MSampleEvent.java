package jp.co.tutorial.entity.event;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * MSampleCustomerエンティティクラス.
 *
 * @author intra-mart
 */
@Entity
public class MSampleEvent implements Serializable {

    /** serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** eventIdプロパティ. */
    @Id
    @Column(name = "event_id", length = 20, nullable = false, unique = true)
    public String eventId;

    /** eventNameプロパティ. */
    @Column(length = 50, nullable = true)
    public String eventName;

    /** eventDetailプロパティ. */
    @Column(length = 100, nullable = true)
    public String eventDetail;

    /** eventDateプロパティ. */
    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    public Date eventDate;

    /** entryUserプロパティ. */
    @Column(length = 100, nullable = true)
    public String entryUser;
}
