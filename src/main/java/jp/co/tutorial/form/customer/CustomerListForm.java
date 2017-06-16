package jp.co.tutorial.form.customer;

import java.io.Serializable;
import java.util.List;

/**
 * 顧客情報のパラメータプロパティを持つ ActionForm.
 *
 * @author intra-mart
 */
public class CustomerListForm implements Serializable {

    /** serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** 顧客リスト. */
    public List<CustomerForm> customerList;

    /** パス. */
    public String path;

    /** 初期表示で表示したいページ番号. */
    public String page;

    /** ソートキー. */
    public String sortName;

    /** ソート順. */
    public String sortOrder;

    /** 1ページに表示する行数. */
    public String rowNum;

    /**
     * 1ページに表示する行数のリスト. 表示する行のリストをカンマ区切りで指定
     */
    public String pagerRowList;

}
