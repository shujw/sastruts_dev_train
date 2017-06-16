package jp.co.tutorial.workflow.form.trip;

import jp.co.tutorial.workflow.form.ImWorkflowForm;

import org.seasar.struts.annotation.DateType;
import org.seasar.struts.annotation.IntRange;
import org.seasar.struts.annotation.IntegerType;
import org.seasar.struts.annotation.Maxlength;
import org.seasar.struts.annotation.Required;

/**
 * 旅費フォームクラス.
 *
 * @version $Revision$
 * @author intra-mart
 * @since 1.0
 */
public class TripForm extends ImWorkflowForm {

    /** serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** 出発日. */
    public String startDate;

    /** 到着日. */
    public String arrivalDate;

    /** 出張先. */
    public String destination;

    /** 出張理由. */
    public String reason;

    /** 交通費. */
    public String tExpenses;

    /** 宿泊費. */
    public String lExpenses;

}
