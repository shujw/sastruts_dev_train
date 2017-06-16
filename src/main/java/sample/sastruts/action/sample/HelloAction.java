package sample.sastruts.action.sample;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

import sample.sastruts.dto.sample.HelloDto;
import sample.sastruts.form.sample.HelloForm;

/**
 * Hello　Worldの処理を行うActionクラス
 * @author intra-mart
 */
public class HelloAction {

    @Resource
    @ActionForm
    public HelloForm helloForm;      // 入力情報を保持するFormクラス

    @Resource
    public HelloDto helloDto;        // 出力情報を保持するDTOクラス(publicにすること)

    /**
     * 入力ページのパスを返却します。
     * @return 入力ページのパス
     */
    @Execute(validator = false)
    public String index() {
        return "/sample/sa/hello/index.jsp";
    }

    /**
     * 結果表示画面のパスを返却します。
     * @return 結果表示画面のパス
     */
    @Execute(validator = false)
    public String output() {
        helloDto.outputString = "Hello, " + helloForm.name;     // 結果の情報を格納
        return "/sample/sa/hello/output.jsp";
    }
}