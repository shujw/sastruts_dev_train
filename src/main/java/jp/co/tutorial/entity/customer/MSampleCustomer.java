package jp.co.tutorial.entity.customer;

import java.io.Serializable;
import java.sql.Timestamp;
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
public class MSampleCustomer implements Serializable {

    /** serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** customerCdプロパティ. */
    @Id
    @Column(name = "customer_cd", length = 20, nullable = false, unique = true)
    public String customerCd;

    /** customerNameプロパティ. */
    @Column(length = 50, nullable = true)
    public String customerName;

    /** customerTelnoプロパティ. */
    @Column(length = 20, nullable = true)
    public String customerTelno;

    /** customerAddressプロパティ. */
    @Column(length = 100, nullable = true)
    public String customerAddress;

    /** customerSexプロパティ. */
    @Column(length = 1, nullable = true)
    public String customerSex;

    /** customerBirthdayプロパティ. */
    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    public Date customerBirthday;

    /** attachmentFileプロパティ.*/
    @Column(length = 500, nullable = true)
    public String attachmentFile;

    /** chargeStfCdプロパティ. */
    @Column(length = 100, nullable = true)
    public String chargeStfCd;

    /** updateDateプロパティ. */
    @Column(nullable = true)
    public Timestamp updateDate;
}
