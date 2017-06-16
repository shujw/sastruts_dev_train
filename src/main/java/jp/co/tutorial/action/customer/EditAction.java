package jp.co.tutorial.action.customer;

import java.util.Map;
import java.io.IOException;
import java.net.URLEncoder;

import javax.annotation.Resource;

import jp.co.intra_mart.foundation.i18n.datetime.format.DateTimeFormatIds;
import jp.co.intra_mart.foundation.service.client.file.PublicStorage;
import jp.co.intra_mart.framework.extension.seasar.struts.exception.ApplicationRuntimeException;
import jp.co.intra_mart.foundation.context.Contexts;
import jp.co.intra_mart.foundation.context.model.AccountContext;
import jp.co.intra_mart.framework.extension.seasar.struts.util.IMResponseUtil;
import jp.co.tutorial.dto.customer.CustomerDto;
import jp.co.tutorial.form.customer.CustomerForm;
import jp.co.tutorial.logic.customer.CustomerLogic;
import jp.co.tutorial.logic.customer.EditType;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

/**
 * 顧客情報の編集画面のアクションクラス.
 *
 * @author intra-mart
 */
public class EditAction {

    /** 顧客編集画面JSP. */
    private static final String EDIT = "/customer/edit/index.jsp";

    /** 顧客一覧. */
    private static final String LIST = "/customer/list";

    /** アクションに対するFormの定義. */
    @ActionForm
    @Resource
    public CustomerForm customerForm;

    /** ロジックコンポーネントの定義. */
    @Resource
    protected CustomerLogic customerLogic;

    /**
     * indexメソッド.
     *
     * @return 遷移先
     */
    @Execute(validator = false)
    public String index() {
        // 編集種別を設定
        customerForm.editType = "0";
        customerForm.path = "/customer/edit/edit";
	//日付フォーマットを設定
	AccountContext accountContext = Contexts.get(AccountContext.class);
	final Map<String,String> format = accountContext.getDateTimeFormats();
	customerForm.format = format.get("IM_DATETIME_FORMAT_DATE_STANDARD");

        return EDIT;
    }

    /**
     * searchメソッド.
     *
     * @return 遷移先
     */
    @Execute(validator = false, urlPattern = "search/{customerCd}")
    public String search() {

        // 顧客情報を取得する
        final CustomerDto customerDto = customerLogic
                .searchCustomer(customerForm.customerCd);
        if (customerDto == null) {
            // 取得件数が0の場合
            throw new ApplicationRuntimeException(
                    customerLogic.getMessage("IM.CST.ERR.003"));
        }
        // Formに顧客情報設定
        customerForm = customerLogic.dtoToForm(customerDto,
                DateTimeFormatIds.IM_DATETIME_FORMAT_DATE_INPUT);

        // 編集種別を設定
        customerForm.editType = "1";
        customerForm.path = "/customer/edit/edit";
	//日付フォーマットを設定
	AccountContext accountContext = Contexts.get(AccountContext.class);
	final Map<String,String> format = accountContext.getDateTimeFormats();
	customerForm.format = format.get("IM_DATETIME_FORMAT_DATE_STANDARD");

        return EDIT;
    }

    /**
     * editメソッド.
     * 顧客情報の編集
     * @return 遷移先
     */
    @Execute(validator = true, validate = "validateEdit", input = EDIT)
    public String edit() {

        // 顧客情報のコピー
        final CustomerDto customerDto = customerLogic.formToDto(customerForm);

        // 編集モードによる分岐
        switch (EditType.getEnum(customerForm.editType)) {
        // 登録
        case editTyp_Create:
            customerLogic.createCustomer(customerDto,
                    customerForm.attachmentFile);
            break;
        // 更新
        case editTyp_Update:
            customerLogic.updateCustomer(customerDto,
                    customerForm.attachmentFile);
            break;
        // 削除
        case editTyp_Delete:
            customerLogic.deleteCustomer(customerDto.customerCd);
            break;
        default:
            break;
        }
        return LIST;
    }

    /**
     * 入力値チェック （ActionFormのアノテーションでチェックできない内容）.
     *
     * @return　エラー
     */
    public ActionMessages validateEdit() {

        final ActionMessages errors = new ActionMessages();
        if (EditType.editTyp_Create.equals(EditType
                .getEnum(customerForm.editType))) {
            final CustomerDto customerDto = customerLogic
                    .searchCustomer(customerForm.customerCd);

            // 顧客CD重複
            if (customerDto != null) {
                errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                        "IM.CST.ERR.004"));
            }
        }
        return errors;
    }

    /**
     * 添付ファイルのダウンロード.
     *
     * @return　遷移しないので、null返却します
     * @throws IOException
     */
    @Execute(validator = false, urlPattern = "download/{customerCd}")
    public String download() throws IOException {
        final CustomerDto customerDto = customerLogic
                .searchCustomer(customerForm.customerCd);

        if (customerDto.attachmentFile != null
                && !customerDto.attachmentFile.equals("")) {

            final PublicStorage publicStorage = new PublicStorage("customer/"
                    + customerForm.customerCd + "/"
                    + customerDto.attachmentFile);
            if (publicStorage.isFile()) {
                // レスポンスにファイルデータを書き出します
                IMResponseUtil.download(customerDto.attachmentFile, publicStorage.open());
            }
        }
        return null;
    }

}
