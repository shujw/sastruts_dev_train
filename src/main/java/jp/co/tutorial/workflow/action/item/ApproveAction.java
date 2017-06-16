package jp.co.tutorial.workflow.action.item;

import javax.annotation.Resource;

import jp.co.intra_mart.foundation.workflow.code.PageType;
import jp.co.tutorial.workflow.dto.item.ItemPurchaseDto;
import jp.co.tutorial.workflow.form.item.ApproveForm;
import jp.co.tutorial.workflow.logic.item.ItemPurchaseLogic;

import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

/**
 * Approveアクションクラス.
 *
 * @version $Revision$
 * @author intra-mart
 * @since 1.0
 */
public class ApproveAction {

    /** 物品購入承認画面JSP. */
    private static final String INDEX = "/item/approve/index.jsp";

    /** アクションに対するFormの定義. */
    @ActionForm
    @Resource
    public ApproveForm approveForm;

    /** 物品購入情報. */
    public ItemPurchaseDto itemPurchaseDto;

    /** ロジックコンポーネントの定義. */
    @Resource
    protected ItemPurchaseLogic itemPurchaseLogic;

    /**
     * indexメソッド.
     *
     * @return 遷移先
     */
    @Execute(validator = false)
    public String index(){
        if (PageType.pageTyp_Proc == PageType.getEnum(approveForm.imwPageType)) {
            // 旅費情報取得
            itemPurchaseDto = itemPurchaseLogic.searchItemPurchase(approveForm.imwUserDataId);
            // Formに旅費情報を設定
            approveForm = (ApproveForm) itemPurchaseLogic.dtoToForm(itemPurchaseDto, approveForm);
        } else {
            // TODO エラー処理を追加してください
        }

        return INDEX;
    }
}
