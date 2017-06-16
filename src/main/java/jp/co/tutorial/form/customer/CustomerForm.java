package jp.co.tutorial.form.customer;

import java.io.Serializable;

import org.apache.struts.upload.FormFile;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;
import org.seasar.struts.annotation.DateType;
import org.seasar.struts.annotation.Maxlength;

/**
 * 顧客情報のパラメータプロパティを持つ ActionForm.
 *
 * @author intra-mart
 */
public class CustomerForm implements Serializable {

    /** serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** 顧客CD. */
    @Maxlength(maxlength = 20)
    public String customerCd;

    /** 顧客名. */
    @Maxlength(maxlength = 25)
    public String customerName;

    /** 電話番号. */
    @Maxlength(maxlength = 20)
    public String customerTelno;

    /** 住所. */
    @Maxlength(maxlength = 50)
    public String customerAddress;

    /** 性別. */
    @Maxlength(maxlength = 1)
    public String customerSex;

    /** 生年月日. */
    @DateType
    public String customerBirthday;

    /** 参考資料. */
    public FormFile attachmentFile;

    /** アップロード済みファイル. */
    public String uploadedFileName;

    /** 担当者コード. */
    @Maxlength(maxlength = 100)
    public String chargeStfCd;

    /** 担当者. */
    @Maxlength(maxlength = 100)
    public String chargeStf;

    /** 編集種別. */
    public String editType;

    /** フォーマット. */
    public String format;

    /** パス. */
    public String path;
}
