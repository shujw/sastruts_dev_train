package jp.co.tutorial.workflow.logic.trip;

import java.util.Map;

import jp.co.intra_mart.foundation.workflow.plugin.process.action.ActionProcessEventListener;
import jp.co.intra_mart.foundation.workflow.plugin.process.action.ActionProcessParameter;

import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

/**
 * アクション処理.
 *
 * このクラスは下記のような行為を行った場合に実行される処理です.<br />
 * アクションに対応したメソッドを実装することにより、処理が呼び出されます.<br />
 * 実装するメソッド名は以下の通りです.
 * <ol>
 * <li>申請: apply</li>
 * <li>再申請: reapply</li>
 * <li>申請（一時保存）: applyFromTempSave</li>
 * <li>申請（未処理）: applyFromUnapply</li>
 * <li>取止め: discontinue</li>
 * <li>引戻し: pullBack</li>
 * <li>差戻し後引戻し: sendBackToPullBack</li>
 * <li>承認: approve</li>
 * <li>承認終了: approveEnd</li>
 * <li>否認: deny</li>
 * <li>差戻し: sendBack</li>
 * <li>保留: reserve</li>
 * <li>保留解除: reserveCancel</li>
 * <li>案件操作: matterHandle</li>
 * <li>一時保存（新規登録）: tempSaveCreate</li>
 * <li>一時保存（更新）: tempSaveUpdate</li>
 * <li>一時保存（削除）: tempSaveDelete</li>
 * </ol>
 *
 * このクラスはIM-Workflowよりインスタンス化が行われます。そのため必ず空のコンストラクタが必要となります.<br />
 * このクラスは、データベーストランザクション内において実行されます.<br />
 * 各メソッド内においてデータベーストランザクションの制御は不要です.<br />
 *
 * @version $Revision$
 * @author intra-mart
 * @since 1.0
 * @see ActionProcessEventListener
 */
public class ActionProcess2 extends ActionProcessEventListener {

    /** ロジックコンポーネント. */
    private final BusinessTripLogic businessTripLogic;

    /** コンストラクタ. */
    public ActionProcess2() {
        // 自動バインディングは適用されないので、明示的に
        // ロジックコンポーネントを取得する
        this.businessTripLogic = (BusinessTripLogic) SingletonS2ContainerFactory
                .getContainer().getComponent(BusinessTripLogic.class);
    }

    /**
     * 申請処理.
     *
     * このメソッドは、ワークフローの申請が行われた場合に呼出されます.<br />
     * 申請処理では、案件番号を返却する必要があります.(ActionProcess1.javaで返却しているためここではnull返却)
     *
     * @param parameter
     *            ワークフローパラメータ情報
     * @param userParameter
     *            リクエストパラメータ
     * @return 案件番号
     * @see ActionProcessParameter
     */
    public String apply(final ActionProcessParameter parameter, final Map<String, Object> userParameter) {
        // 案件プロパティ登録（合計金額）
        businessTripLogic.createMatterProperty(parameter, userParameter);

        return null;
    }

    /**
     * 一時保存処理(新規).
     *
     * このメソッドは、ワークフローの一時保存処理(新規)が行われた場合に呼出されます.<br />
     *
     * @param parameter
     *            ワークフローパラメータ情報
     * @param userParameter
     *            リクエストパラメータ
     * @see ActionProcessParameter
     */
    public void tempSaveCreate(final ActionProcessParameter parameter, final Map<String, Object> userParameter) {
        // 案件プロパティ登録（合計金額）
        businessTripLogic.createMatterProperty(parameter, userParameter);

        return;
    }

    /**
     * 一時保存処理(更新).
     *
     * このメソッドは、ワークフローの一時保存処理(更新)が行われた場合に呼出されます.<br />
     *
     * @param parameter
     *            ワークフローパラメータ情報
     * @param userParameter
     *            リクエストパラメータ
     * @see ActionProcessParameter
     */
    public void tempSaveUpdate(final ActionProcessParameter parameter, final Map<String, Object> userParameter) {

        // 案件プロパティ更新（合計金額）
        businessTripLogic.updateMatterProperty(parameter, userParameter);

        return;
    }

    /**
     * 一時保存削除.
     *
     * このメソッドは、ワークフローの一時保存削除が行われた場合に呼出されます.<br />
     *
     * @param parameter
     *            ワークフローパラメータ情報
     * @param userParameter
     *            リクエストパラメータ
     * @see ActionProcessParameter
     */
    public void tempSaveDelete(final ActionProcessParameter parameter, final Map<String, Object> userParameter) {

        // 案件プロパティ削除（合計金額）
        businessTripLogic.deleteMatterProperty(parameter.getUserDataId());

        return;
    }

    /**
     * 一時保存申請処理.
     *
     * このメソッドは、ワークフローの一時保存案件が申請された場合に呼出されます.<br />
     * 再申請処理では、案件番号を返却する必要があります.
     *
     * @param parameter
     *            ワークフローパラメータ情報
     * @param userParameter
     *            リクエストパラメータ
     * @see ActionProcessParameter
     */
    public String applyFromTempSave(final ActionProcessParameter parameter, final Map<String, Object> userParameter) {

        // 案件プロパティ更新（合計金額）
        businessTripLogic.updateMatterProperty(parameter, userParameter);

        return null;
    }

    /**
     * 再申請処理.
     *
     * このメソッドは、ワークフローの再申請が行われた場合に呼出されます.<br />
     * 再申請処理では、案件番号を返却する必要があります.(ActionProcess1.javaで返却しているためここではnull返却)
     *
     * @param parameter
     *            ワークフローパラメータ情報
     * @param userParameter
     *            リクエストパラメータ
     * @return 案件番号
     * @see ActionProcessParameter
     */
    public String reapply(final ActionProcessParameter parameter, final Map<String, Object> userParameter) {

        // 案件プロパティ更新（合計金額）
        businessTripLogic.updateMatterProperty(parameter, userParameter);

        return null;
    }

}
