package jp.co.tutorial.action.customer;

import javax.annotation.Resource;

import jp.co.tutorial.form.customer.CustomerListForm;

import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

/**
 * 顧客情報の一覧画面のアクションクラス.
 *
 * @author intra-mart
 */
public class ListAction {

    /** 顧客一覧画面JSP. */
    private static final String LIST = "/customer/list/index.jsp";

    /** アクションに対するFormの定義. */
    @ActionForm
    @Resource
    public CustomerListForm customerListForm;

    /**
     * indexメソッド.
     *
     * @return 遷移先
     */
    @Execute(validator = false)
    public String index() {
        // ページング・ソート情報
        customerListForm.page = "1";
        customerListForm.pagerRowList = "10,20,30";
        customerListForm.rowNum = "10";
        customerListForm.sortName = "customerCd";
        customerListForm.sortOrder = "asc";

        customerListForm.path = "customer/edit";
        return LIST;
    }
}
