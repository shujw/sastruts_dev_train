package jp.co.tutorial.logic.customer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityExistsException;
import javax.servlet.http.HttpServletRequest;

import jp.co.intra_mart.common.aid.jsdk.javax.servlet.http.HTTPContext;
import jp.co.intra_mart.common.aid.jsdk.javax.servlet.http.HTTPContextManager;
import jp.co.intra_mart.common.aid.jsdk.utility.URLUtil;
import jp.co.intra_mart.common.platform.log.Logger;
import jp.co.intra_mart.foundation.context.Contexts;
import jp.co.intra_mart.foundation.context.model.AccountContext;
import jp.co.intra_mart.foundation.exception.BizApiException;
import jp.co.intra_mart.foundation.i18n.datetime.format.AccountDateTimeFormatter;
import jp.co.intra_mart.foundation.i18n.datetime.format.DateTimeFormatIds;
import jp.co.intra_mart.foundation.i18n.datetime.format.DateTimeFormatterException;
import jp.co.intra_mart.foundation.master.user.UserManager;
import jp.co.intra_mart.foundation.master.user.model.User;
import jp.co.intra_mart.foundation.master.user.model.UserBizKey;
import jp.co.intra_mart.foundation.security.exception.AccessSecurityException;
import jp.co.intra_mart.foundation.security.message.MessageManager;
import jp.co.intra_mart.foundation.service.client.file.PublicStorage;
import jp.co.intra_mart.framework.extension.seasar.struts.exception.ApplicationRuntimeException;
import jp.co.intra_mart.framework.extension.seasar.struts.exception.SystemRuntimeException;
import jp.co.intra_mart.imbox.exception.IMBoxException;
import jp.co.intra_mart.imbox.model.Entry4NoticeMessage;
import jp.co.intra_mart.imbox.service.ApplicationBoxService;
import jp.co.intra_mart.imbox.service.Services;
import jp.co.tutorial.dto.customer.CustomerDto;
import jp.co.tutorial.entity.customer.MSampleCustomer;
import jp.co.tutorial.form.customer.CustomerForm;
import jp.co.tutorial.service.customer.CustomerService;

import org.apache.http.HttpServerConnection;
import org.apache.http.protocol.HttpContext;
import org.apache.struts.upload.FormFile;
import org.seasar.framework.beans.util.Beans;

/**
 * {@link MSampleCustomer}のロジッククラス.
 *
 * @author intra-mart
 */
public class CustomerLogic {

    /** サービスコンポーネントの定義. */
    @Resource
    protected CustomerService customerService;

    /** LOGGER. */
    private static final Logger LOGGER_INFO = Logger
            .getLogger("CUSTOMER_LOG_INFO");

    /** LOGGER. */
    private static final Logger LOGGER_DEBUG = Logger
            .getLogger("CUSTOMER_LOG_DEBUG");

    /**
     * 顧客CDを条件に顧客情報を検索します.
     *
     * @param customerCd 顧客CD
     * @return 顧客情報DTO
     */
    public CustomerDto searchCustomer(final String customerCd) {

        final MSampleCustomer mSampleCustomer = customerService
                .findByCustomerCd(customerCd);
        if (mSampleCustomer != null) {
            // 顧客情報をエンティティからDTOにコピーします。
            final CustomerDto customerDto = Beans.createAndCopy(
                    CustomerDto.class, mSampleCustomer).execute();
            return customerDto;
        }
        return null;
    }

    /**
     * 登録されている顧客情報を取得します.
     *
     * @param sortIndex ソート対象
     * @param sortOrder 昇順/降順
     * @param offset 取得開始レコード
     * @param limit 取得件数
     * @return 顧客情報リスト
     */
    public List<CustomerDto> getCustomerList(final String sortIndex,
            final String sortOrder, final int offset, final int limit) {

        final List<MSampleCustomer> resultList = customerService
                .getCustomerData(sortIndex + " " + sortOrder, offset, limit);
        if (resultList == null) {
            return Collections.emptyList();
        }

        final List<CustomerDto> customerList = new ArrayList<CustomerDto>();
        // 顧客情報をエンティティからDTOにコピーします。
        for (final MSampleCustomer customer : resultList) {
            final CustomerDto customerDto = Beans.createAndCopy(
                    CustomerDto.class, customer).execute();
            customerList.add(customerDto);
        }
        return customerList;
    }

    /**
     * 登録されている顧客数を取得します.
     *
     * @return 顧客数
     */
    public int countCustomer() {
        return (int) customerService.getCount();
    }

    /**
     * 顧客情報の登録.
     *
     * @param customerDto　顧客情報
     * @param file 参考資料
     */
    public void createCustomer(final CustomerDto customerDto,
            final FormFile file) {
        // エンティティに顧客情報をコピー
        final MSampleCustomer mSampleCustomer = Beans.createAndCopy(
                MSampleCustomer.class, customerDto).execute();
        mSampleCustomer.updateDate = new Timestamp(System.currentTimeMillis());
        // 登録
        try {
            customerService.insert(mSampleCustomer);
        } catch (final EntityExistsException e) {
            // 一意制約違反
            throw new ApplicationRuntimeException(e);
        }
        // ファイル操作
        if (file.getFileName() != null && !file.getFileName().equals("")) {
            // ファイルアップロード
            uploadFile(customerDto.customerCd, file);
        }

        // IMBOX連携
        linkIMBox(customerDto);

        // LOGGER_INFOでログの出力
        // infoレベルで出力
        LOGGER_INFO.info("顧客コード:{}の顧客情報を登録しました。", customerDto.customerCd);
        // debugレベルで出力
        LOGGER_INFO.debug("エラー:なし");

        // STAFF_LOG_DEBUGでログの出力
        // infoレベルで出力
        LOGGER_DEBUG.info("顧客コード:{}の顧客情報を登録しました。", customerDto.customerCd);
        // debugレベルで出力
        LOGGER_DEBUG.debug("エラー:なし");

        return;
    }

    /**
     * 顧客情報の更新.
     *
     * @param customerDto　顧客情報
     * @param file 参考資料
     */
    public void updateCustomer(final CustomerDto customerDto,
            final FormFile file) {
        // エンティティに顧客情報をコピー
        final MSampleCustomer mSampleCustomer = Beans.createAndCopy(
                MSampleCustomer.class, customerDto).execute();
        mSampleCustomer.updateDate = new Timestamp(System.currentTimeMillis());
        // 更新
        try {
            if (file.getFileName() != null && !file.getFileName().equals("")) {
                customerService.update(mSampleCustomer);
            } else {
                customerService.updateExcludeAttachmentFile(mSampleCustomer);
            }
        } catch (final EntityExistsException e) {
            // 一意制約違反
            throw new ApplicationRuntimeException(e);
        }
        // ファイル操作
        if (file.getFileName() != null && !file.getFileName().equals("")) {
            // アップロード済みファイルの削除
            deleteFile(customerDto.customerCd);
            // ファイルアップロード
            uploadFile(customerDto.customerCd, file);
        }
        return;
    }

    /**
     * 顧客情報の削除.
     *
     * @param customerCd　顧客コード
     */
    public void deleteCustomer(final String customerCd) {
        final MSampleCustomer mSampleCustomer = new MSampleCustomer();
        mSampleCustomer.customerCd = customerCd;

        // 顧客情報削除
        customerService.delete(mSampleCustomer);

        // ファイルの削除
        deleteFile(customerCd);
    }

    /**
     * 顧客情報をDTOからFormに入れ替えます.
     *
     * @param customerDto　顧客情報
     * @param formatId　日付フォーマットID
     * @return CustomerForm　顧客情報
     */
    public CustomerForm dtoToForm(final CustomerDto customerDto,
            final String formatId) {
        final CustomerForm customerForm = Beans
                .createAndCopy(CustomerForm.class, customerDto)
                .excludes("customerBirthday", "attachmentFile").execute();

        if (customerDto.customerBirthday != null) {
            customerForm.customerBirthday = AccountDateTimeFormatter.format(
                    customerDto.customerBirthday, formatId);
        }
        if (customerDto.attachmentFile != null) {
            customerForm.uploadedFileName = customerDto.attachmentFile;
        }
        // 担当者名を取得
        customerForm.chargeStf = getChargeStfName(customerDto.chargeStfCd);
        return customerForm;
    }

    /**
            * 　顧客情報をFormからDTOに入れ替えます.
     *
     * @param customerForm 顧客情報
     * @return CustomerDto　顧客情報
     */
    public CustomerDto formToDto(final CustomerForm customerForm) {
        final CustomerDto customerDto = Beans
                .createAndCopy(CustomerDto.class, customerForm)
                .excludes("customerBirthday", "attachmentFile").execute();

        try {
            if (customerForm.customerBirthday != null
                    && !customerForm.customerBirthday.equals("")) {
                customerDto.customerBirthday = AccountDateTimeFormatter.parse(
                        customerForm.customerBirthday, Date.class,
                        DateTimeFormatIds.IM_DATETIME_FORMAT_DATE_STANDARD);
            }
            if (customerForm.attachmentFile != null) {
                customerDto.attachmentFile = customerForm.attachmentFile
                        .getFileName();
            }
        } catch (final DateTimeFormatterException e) {
            throw new ApplicationRuntimeException(e);
        }
        return customerDto;
    }

    /**
     * 参考資料をアップロードします.
     *
     * @param customerCd　顧客情報
     * @param file　参考資料
     */
    private void uploadFile(final String customerCd, final FormFile file) {
        // ファイル名取得
        final String fileName = file.getFileName();
        // ディレクトリパス
        final String dir = "customer/" + customerCd;
        try {
            final PublicStorage dirStorage = new PublicStorage(dir);
            if (!dirStorage.exists() || !dirStorage.isDirectory()) {
                if (!dirStorage.makeDirectories()) {
                    // ディレクトリ作成失敗
                    throw new ApplicationRuntimeException(
                            getMessage("IM.CST.ERR.000"));
                }
            }
            final PublicStorage fileStorage = new PublicStorage(dirStorage,
                    fileName);
            fileStorage.save(file.getFileData());

        } catch (final IOException e) {
            // ファイルアップロード処理失敗
            throw new ApplicationRuntimeException(getMessage("IM.CST.ERR.000"),
                    e);
        }
        return;
    }

    /**
     * 参考資料を削除します.
     *
     * @param customerCd　顧客コード
     */
    private void deleteFile(final String customerCd) {
        // ディレクトリパス
        final String dir = "customer/" + customerCd;
        try {
            final PublicStorage dirStorage = new PublicStorage(dir);
            if (dirStorage.exists()) {
                dirStorage.remove(true);
            }
        } catch (final IOException e) {
            // ファイル削除処理失敗
            throw new ApplicationRuntimeException(getMessage("IM.CST.ERR.000"),
                    e);
        }
        return;

    }

    /**
     * 担当者の名前を返却します.
     *
     * @param chargeStfCd 担当者コード
     * @return　担当者名
     */
    public String getChargeStfName(final String chargeStfCd) {
        String chargeStfName = "";
        if (chargeStfCd != null && !chargeStfCd.equals("")) {
            try {
                final AccountContext accountContext = Contexts
                        .get(AccountContext.class);
                final UserManager userManager = new UserManager(chargeStfCd,
                        accountContext.getLocale());
                final UserBizKey bizKey = new UserBizKey();
                bizKey.setUserCd(chargeStfCd);
                final User user = userManager.getUser(bizKey, new Date(),
                        accountContext.getLocale());
                if (user != null) {
                    chargeStfName = user.getUserName();
                }
            } catch (final BizApiException e) {
                throw new ApplicationRuntimeException(e);
            }
        }
        return chargeStfName;
    }

    /**
     * IMBOX連携.
     * 新規登録した顧客の情報をIMBOXへ送信します.
     * @param customerDto 顧客情報
     */
    public void linkIMBox(final CustomerDto customerDto) {
   	
    	final String baseUrl = getBaseUrl();
        final Entry4NoticeMessage entry4NoticeMessage = new Entry4NoticeMessage();
        entry4NoticeMessage.setMessageText("新規顧客を登録しました." + "顧客コード："
                + customerDto.customerCd);
        entry4NoticeMessage.setMessageTypeCd("MESSAGE_TYPE_MESSAGE");
        entry4NoticeMessage.setSendUserCd(Contexts.get(AccountContext.class)
                .getUserCd());
        entry4NoticeMessage.setApplicationCd("tutorial");
        entry4NoticeMessage.setUri(baseUrl + "/customer/edit/search/"
                + customerDto.customerCd);
        entry4NoticeMessage.setUriTitle("顧客詳細");
        final String[] userCds = { "ueda" };
        final ApplicationBoxService applicationBoxService = Services.get(ApplicationBoxService.class);
        try {
            applicationBoxService.sendNoticeMessage(entry4NoticeMessage, userCds);
        } catch (final IMBoxException e) {
            // System.out.println("IMBOXの登録に失敗しました");
            // System.out.println(e);
            LOGGER_DEBUG.error("IMBOXの登録に失敗しました", e);
        }
    }

    /**
     * メッセージを取得します.
     *
     * @param key
     *            メッセージキー
     * @return メッセージ
     */
    public String getMessage(final String key) {

        try {
            return MessageManager.getInstance().getMessage(key);
        } catch (final AccessSecurityException e) {
            throw new SystemRuntimeException(e);
        }
    }
    
    /**
     * ベースURLを取得します.
     *
     * @return ベースURL
     */
    public String getBaseUrl(){
        
        HTTPContext httpContext = HTTPContextManager.getInstance().getCurrentContext();
        HttpServletRequest request = httpContext.getRequest();

        try {
                URL url = URLUtil.getContextURL(request, false);
                return url.toExternalForm();
        }
        catch (MalformedURLException e) {
                return new String(request.getRequestURL());
        }
    }
}
