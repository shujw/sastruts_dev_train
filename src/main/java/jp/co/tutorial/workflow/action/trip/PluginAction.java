package jp.co.tutorial.workflow.action.trip;

import javax.annotation.Resource;

import jp.co.intra_mart.foundation.security.exception.AccessSecurityException;
import jp.co.intra_mart.foundation.security.message.MessageManager;
import jp.co.tutorial.workflow.form.trip.PluginForm;

import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

/** 処理対象者プラグイン用アクションクラス. */
public class PluginAction {

    /** 処理対象者設定画面JSP. */
    private static final String INDEX = "/trip/plugin/index.jsp";

    /** アクションに対するFormの定義. */
    @ActionForm
    @Resource
    public PluginForm pluginForm;

    /**
     * indexメソッド.
     *
     * @return 遷移先
     * @throws AccessSecurityException
     */
    @Execute(validator = false)
    public String index() throws AccessSecurityException {

        // ActionFormにパラメータセット
        pluginForm.pluginName = getMessage(pluginForm.tripName);
        pluginForm.displayName = getMessage(pluginForm.tripName);
        pluginForm.parameter = getMessage(pluginForm.tripName);
        return INDEX;

    }

    /**
     * メッセージを取得します.
     *
     * @param key メッセージキー
     * @param args パラメータ
     * @return message
     * @throws AccessSecurityException
     */
    protected String getMessage(final String key)
            throws AccessSecurityException {

        try {
            return MessageManager.getInstance().getMessage(key);
        } catch (final AccessSecurityException e) {
            throw new AccessSecurityException(e);
        }
    }
}
