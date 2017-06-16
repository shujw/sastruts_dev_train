package jp.co.tutorial.workflow.logic.trip;

import java.util.HashMap;
import java.util.Map;

import jp.co.intra_mart.foundation.workflow.application.general.ActvMatterNode;
import jp.co.intra_mart.foundation.workflow.application.general.condition.ListSearchConditionNoMatterProperty;
import jp.co.intra_mart.foundation.workflow.application.model.ActvMatterExecutableUserModel;
import jp.co.intra_mart.foundation.workflow.application.model.column.ActvMatterExecutableUserType;
import jp.co.intra_mart.foundation.workflow.application.model.param.ApproveParam;
import jp.co.intra_mart.foundation.workflow.application.process.ProcessManager;
import jp.co.intra_mart.foundation.workflow.exception.WorkflowException;
import jp.co.intra_mart.foundation.workflow.plugin.process.arrive.ArriveProcessEventListener;
import jp.co.intra_mart.foundation.workflow.plugin.process.arrive.ArriveProcessParameter;
import jp.co.intra_mart.framework.extension.seasar.struts.exception.ApplicationRuntimeException;
import jp.co.tutorial.workflow.dto.trip.BusinessTripDto;

import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

/**
 * サンプル到達処理.
 *
 * このクラスは、ノードに到達した場合に実行されるクラスです.<br />
 * ノードに到達した場合は、以下の状況を指します.<br />
 * <ul>
 * <li>前ノードの処理者が、"申請"または"承認"を行って到達した場合</li>
 * <li>他のノードから、"差戻し"され到達した場合</li>
 * <li>"引戻し"を行って到達した場合</li>
 * <li>案件操作で到達した場合</li>
 * </ul>
 *
 * このクラスはIM-Workflowよりインスタンス化が行われます。そのため必ず引数なしのコンストラクタが必要となります.<br />
 * 到達処理はアクション処理や IM-Workflow の内部処理とは独立した処理（thread）となります.<br />
 * そのため、到達処理でエラーが発生した場合、直前の処理を戻す(ロールバック)することはできません.<br />
 * 直前のアクション処理とは、トランザクションも別となります.<br />
 * このクラス中で、データベースの登録／更新／削除処理を行う場合は、<br />
 * executeメソッド内においてデータベーストランザクションの制御を行う必要があります.<br />
 *
 * @version $Revision$
 * @author intra-mart
 * @since 1.0
 * @see ArriveProcessEventListener
 */
public class ArriveProcess extends ArriveProcessEventListener {

    /** ロジックコンポーネントの定義. */
    private final BusinessTripLogic businessTripLogic;

    /** コンストラクタ. */
    public ArriveProcess() {
        // 自動バインディングは適用されないので、明示的に
        // ロジックコンポーネントを取得する
        this.businessTripLogic = (BusinessTripLogic) SingletonS2ContainerFactory
                .getContainer().getComponent(BusinessTripLogic.class);
    }

    /**
     * 到達処理クラスの実行メソッド.
     *
     * @param parameter
     *            ワークフローパラメータ情報
     * @return 処理結果(true: 成功/false: 失敗)
     * @see ArriveProcessParameter
     */
    public boolean execute(final ArriveProcessParameter parameter) {

        // 合計金額の取得
        // 案件プロパティから[合計金額]を取得
        final int itemTotal = businessTripLogic.getMatterProperty(parameter.getUserDataId());

        try {
            if (itemTotal < 30000) {
                // プロセスマネージャ
                final ProcessManager processManager = new ProcessManager(parameter.getLocaleId(),
                        parameter.getSystemMatterId(), parameter.getNodeId());

                // 承認パラメータ
                final ApproveParam approveParam = createApproveParam(parameter);

                // ユーザパラメータ
                final Map<String, Object> userparam = createUserParam(parameter
                        .getUserDataId());

                // 承認処理
                processManager.approve(approveParam, userparam);
            }
        } catch (WorkflowException e) {
            throw new ApplicationRuntimeException(e);
        }
        return true;
    }

    /**
     * 承認パラメータに承認者情報を格納します.
     *
     * @param parameter
     * @return 承認パラメータ
     */
    private ApproveParam createApproveParam(final ArriveProcessParameter parameter) {
        final ApproveParam approveParam = new ApproveParam();
        try {
            // 承認者取得
            final ActvMatterNode node = new ActvMatterNode(parameter.getLocaleId(),
                    parameter.getSystemMatterId());
            final ActvMatterExecutableUserModel[] executableUserList = node
                    .getExecutableUserList(new ListSearchConditionNoMatterProperty<ActvMatterExecutableUserType>());
            if (executableUserList.length > 0) {
                // 処理権限者コード
                approveParam.setAuthUserCode(executableUserList[0]
                        .getAuthUserCode());
                // 実行者コード
                approveParam.setExecuteUserCode(approveParam.getAuthUserCode());
            }
        } catch (final WorkflowException e) {
            throw new ApplicationRuntimeException(e);
        }
        return approveParam;
    }

    /**
     * 承認対象の旅費情報を取得します.
     *
     * @param userDataId
     * @return　旅費情報
     */
    private Map<String, Object> createUserParam(final String userDataId) {
        final BusinessTripDto businessTripDto = businessTripLogic
                .searchTripData(userDataId);

        final Map<String, Object> userParam = new HashMap<String, Object>();
        // 承認対象旅費ID格納
        userParam.put("startDate", businessTripDto.startDate);
        userParam.put("arrivalDate", businessTripDto.arrivalDate);
        userParam.put("destination", businessTripDto.destination);
        userParam.put("reason", businessTripDto.reason);
        userParam.put("tExpenses", businessTripDto.tExpenses);
        userParam.put("lExpenses", businessTripDto.lExpenses);
        userParam.put("userDataId", businessTripDto.userDataId);
        userParam.put("endFlag", businessTripDto.endFlag);
        return userParam;
    }
}
