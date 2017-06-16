package jp.co.tutorial.dto.customer;

import java.util.Date;

/**
 * 顧客情報DTOクラス.
 *
 * @author intra-mart
 */
public class CustomerDto {

    /** 顧客CD. */
    public String customerCd;

    /** 顧客名. */
    public String customerName;

    /** 電話番号. */
    public String customerTelno;

    /** 住所. */
    public String customerAddress;

    /** 性別. */
    public String customerSex;

    /** 生年月日. */
    public Date customerBirthday;

    /** 参考資料. */
    public String attachmentFile;

    /** 担当者. */
    public String chargeStfCd;
}
