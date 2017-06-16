package jp.co.tutorial.workflow.form.trip;

import org.seasar.struts.annotation.Maxlength;
import org.seasar.struts.annotation.Required;

/**
 * ApiApplyフォームクラス.
 *
 * @version $Revision$
 * @author intra-mart
 * @since 1.0
 */
public class ApiapplyForm extends TripForm {

    /** serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** 案件名. */
    @Required
    @Maxlength(maxlength = 200)
    public String mattername;

    /** コメント. */
    @Maxlength(maxlength = 2000)
    public String comment;

    /** パス. */
    public String path;

}
