package jp.co.tutorial.workflow.entity.trip;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * BusinessTripエンティティクラス.
 *
 * @author intra-mart
 */
@Entity
public class BusinessTrip implements Serializable {

    /** serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** tripIdプロパティ. */
    @Id
    @Column(name = "trip_id", length = 20, nullable = false, unique = true)
    public String tripId;

    /** startDateプロパティ. */
    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    public Date startDate;

    /** arrivalDateプロパティ. */
    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    public Date arrivalDate;

    /** destinationプロパティ. */
    @Column(length = 100, nullable = true)
    public String destination;

    /** reasonプロパティ. */
    @Column(length = 100, nullable = true)
    public String reason;

    /** tExpensesプロパティ. */
    @Column(precision = 10, nullable = true)
    public Long tExpenses;

    /** lExpensesプロパティ. */
    @Column(precision = 10, nullable = true)
    public Long lExpenses;

    /** userDataIdプロパティ. */
    @Column(length = 20, nullable = false, unique = true)
    public String userDataId;

    /** endFlagプロパティ. */
    @Column(length = 1, nullable = false)
    public String endFlag;
}
