package jp.co.tutorial.workflow.entity.item;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * SampleImwItemエンティティクラス.
 *
 * @author intra-mart
 */
@Entity
public class SampleImwItem implements Serializable {

    /** serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** userDataIdプロパティ. */
    @Id
    @Column(length = 20, nullable = false, unique = true)
    public String userDataId;

    /** itemNameプロパティ. */
    @Column(length = 100, nullable = false)
    public String itemName;

    /** itemPriceプロパティ. */
    @Column(precision = 10, nullable = false)
    public Long itemPrice;

    /** itemDateプロパティ. */
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    public Date itemDate;

    /** itemReasonプロパティ. */
    @Column(length = 500)
    public String itemReason;

}
