package jp.co.tutorial.workflow.logic.trip;

import jp.co.intra_mart.foundation.workflow.plugin.process.matter_end.MatterEndProcessEventListener;
import jp.co.intra_mart.foundation.workflow.plugin.process.matter_end.MatterEndProcessParameter;

import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

/**
 * 案件終了処理.
 *
 * このクラスはワークフロー案件が終了した際に、一度だけ実行されます.<br />
 * ワークフローが終了した場合は、以下の状況を指します.<br />
 * <ul>
 * <li>最後の承認者が"承認"を行なった場合</li>
 * <li>承認者が"承認終了"を行なった場合</li>
 * <li>承認者が"否認"を行なった場合</li>
 * <li>申請者が"取止め"を行なった場合</li>
 * <li>案件操作で終了ノードに到達した場合</li>
 * </ul>
 *
 * このクラスはIM-Workflowよりインスタンス化が行われます。そのため必ず空のコンストラクタが必要となります.<br />
 * このクラスは、データベーストランザクション内において実行されます.<br />
 * executeメソッド内においてデータベーストランザクションの制御は不要です.<br />
 * 案件終了処理は、直前のアクション処理や到達処理とは独立した処理(トランザクション)となります.<br />
 * そのため、案件終了処理でエラーが発生した場合、直前の処理を戻す(ロールバック)することはできません.
 *
 * @version $Revision$
 * @author intra-mart
 * @since 1.0
 * @see MatterEndProcessEventListener
 */
public class MatterEndProcess extends MatterEndProcessEventListener {

    /** ロジックコンポーネント. */
    private final BusinessTripLogic businessTripLogic;

    /** コンストラクタ. */
    public MatterEndProcess() {
        // 自動バインディングは適用されないので、明示的に
        // ロジックコンポーネントを取得する
        this.businessTripLogic = (BusinessTripLogic) SingletonS2ContainerFactory
                .getContainer().getComponent(BusinessTripLogic.class);
    }

    /**
     * 案件終了処理クラスの実行メソッド.
     *
     * @param parameter
     *            ワークフローパラメータ情報
     * @return 処理結果(true: 成功/false: 失敗)
     * @see MatterEndProcessParameter
     */
    public boolean execute(final MatterEndProcessParameter parameter) {

        // 案件終了
        businessTripLogic.updateFlg(parameter.getSystemMatterId());

        return true;
    }
}
