package jp.co.tutorial.workflow.form.item;

import jp.co.tutorial.workflow.form.ImWorkflowForm;

import org.seasar.struts.annotation.DateType;
import org.seasar.struts.annotation.IntRange;
import org.seasar.struts.annotation.IntegerType;
import org.seasar.struts.annotation.Maxlength;
import org.seasar.struts.annotation.Required;

/**
 * 物品購入フォームクラス.
 *
 * @version $Revision$
 * @author intra-mart
 * @since 1.0
 */
public class ItemPurchaseForm extends ImWorkflowForm {

    /** serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** 品名. */
    public String itemName;

    /** 金額. */
    public String itemPrice;

    /** 納入日. */
    public String itemDate;

    /** 購入理由. */
    public String itemReason;
}
