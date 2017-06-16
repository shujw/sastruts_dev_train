package jp.co.tutorial.workflow.action.item;

import javax.annotation.Resource;

import jp.co.intra_mart.foundation.workflow.code.PageType;
import jp.co.tutorial.workflow.dto.item.ItemPurchaseDto;
import jp.co.tutorial.workflow.form.item.ConfirmForm;
import jp.co.tutorial.workflow.logic.item.ItemPurchaseLogic;

import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

/**
 * Confirmアクションクラス.
 *
 * @version $Revision$
 * @author intra-mart
 * @since 1.0
 */
public class ConfirmAction {

    /** 物品購入確認画面JSP. */
    private static final String INDEX = "/item/confirm/index.jsp";

    /** アクションに対するFormの定義. */
    @ActionForm
    @Resource
    public ConfirmForm confirmForm;

     /** ロジックコンポーネントの定義. */
    @Resource
    protected ItemPurchaseLogic itemPurchaseLogic;

    /**
     * indexメソッド.
     *
     * @return 遷移先
     */
    @Execute(validator = false)
    public String index() {
        if (PageType.pageTyp_Cnfm == PageType.getEnum(confirmForm.imwPageType)) {
            // 登録されている旅費データを取得します
            final ItemPurchaseDto itemPurchaseDto = itemPurchaseLogic.searchItemPurchase(confirmForm.imwUserDataId);

            // 画面に表示する項目をFormに設定します
            confirmForm = (ConfirmForm) itemPurchaseLogic.dtoToForm(itemPurchaseDto, confirmForm);
        } else {
            // TODO エラー処理を追加してください
        }
        return INDEX;
    }

}
