package jp.co.tutorial.logic.event;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

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
import jp.co.intra_mart.foundation.service.client.information.Identifier;
import jp.co.intra_mart.framework.extension.seasar.struts.exception.ApplicationRuntimeException;
import jp.co.intra_mart.framework.extension.seasar.struts.exception.SystemRuntimeException;
import jp.co.intra_mart.imbox.exception.IMBoxException;
import jp.co.intra_mart.imbox.model.Entry4NoticeMessage;
import jp.co.intra_mart.imbox.service.ApplicationBoxService;
import jp.co.intra_mart.imbox.service.Services;
import jp.co.tutorial.dto.event.EventDto;
import jp.co.tutorial.entity.event.MSampleEvent;
import jp.co.tutorial.form.event.EventForm;
import jp.co.tutorial.service.event.EventService;

import org.seasar.framework.beans.util.Beans;
import org.seasar.util.exception.IORuntimeException;

/**
 * {@link MSampleEvent}のロジッククラス.
 *
 * @author intra-mart
 */
public class EventLogic {

    /** サービスコンポーネントの定義. */
    @Resource
    protected EventService eventService;

    /** LOGGER. */
    private static final Logger LOGGER_DEBUG = Logger
            .getLogger("EVENT_LOG_DEBUG");

    /**
     * イベントIDを条件にイベント情報を検索します.
     *
     * @param eventId イベントID
     * @return イベント情報DTO
     */
    public EventDto searchEvent(final String eventId) {

        final MSampleEvent mSampleEvent = eventService
                .findByEventId(eventId);
        if (mSampleEvent != null) {
            // 顧客情報をエンティティからDTOにコピーします。
            final EventDto eventDto = Beans.createAndCopy(
                    EventDto.class, mSampleEvent).execute();
            return eventDto;
        }
        return null;
    }

    /**
     * イベント情報の登録.
     *
     * @param eventDto　イベント情報
     */
    public void createEvent(final EventDto eventDto) {
        // エンティティにイベント情報をコピー
        final MSampleEvent mSampleEvent = Beans.createAndCopy(
                MSampleEvent.class, eventDto).execute();
        // 登録
        try {
            eventService.insert(mSampleEvent);
        } catch (final EntityExistsException e) {
            // 一意制約違反
            throw new ApplicationRuntimeException(e);
        }

        // IMBOX連携
        if (eventDto.entryUser != null && !eventDto.entryUser.equals("")) {
            linkIMBox(eventDto);
        }

        return;
    }

    /**
  　       * 　イベント情報をDTOからFormに入れ替えます.
     *
     * @param eventDto　イベント情報
     * @return EventForm　イベント情報
     */
    public EventForm dtoToForm(final EventDto eventDto) {
        final EventForm eventForm = Beans
                .createAndCopy(EventForm.class, eventDto)
                .excludes("eventDate").execute();

        if (eventDto.eventDate != null) {
            eventForm.eventDate = AccountDateTimeFormatter.format(
                    eventDto.eventDate, DateTimeFormatIds.IM_DATETIME_FORMAT_DATE_STANDARD);
        }

        // 参加者者名を取得
        eventForm.entryUser = getEntryUserName(eventDto.entryUser);
        return eventForm;
    }

    /**
            * 　イベント情報をFormからDTOに入れ替えます.
     *
     * @param eventForm イベント情報
     * @return EvnentDto　イベント情報
     */
    public EventDto formToDto(final EventForm eventForm) {
        final EventDto eventDto = Beans
                .createAndCopy(EventDto.class, eventForm)
                .excludes("eventDate").execute();

        try {
            if (eventForm.eventDate != null
                    && !eventForm.eventDate.equals("")) {
                eventDto.eventDate = AccountDateTimeFormatter.parse(
                        eventForm.eventDate, Date.class,
                        DateTimeFormatIds.IM_DATETIME_FORMAT_DATE_STANDARD);
            }
        } catch (final DateTimeFormatterException e) {
            throw new ApplicationRuntimeException(e);
        }
        return eventDto;
    }

    /**
     * 参加者の名前を返却します.
     *
     * @param entryUser 参加者
     * @return　参加者者名
     */
    public String getEntryUserName(final String entryUser) {
        StringBuffer entryUserName = new StringBuffer();
        if (entryUser != null && !entryUser.equals("")) {
            try {
                String[] entryUserList = entryUser.split(",");
                for (int i = 0; i < entryUserList.length; i++) {
                    final AccountContext accountContext = Contexts
                            .get(AccountContext.class);
                    final UserManager userManager = new UserManager(entryUserList[i],
                            accountContext.getLocale());
                    final UserBizKey bizKey = new UserBizKey();
                    bizKey.setUserCd(entryUserList[i]);
                    final User user = userManager.getUser(bizKey, new Date(),
                            accountContext.getLocale());
                    if (user != null) {
                        entryUserName.append(user.getUserName());
                    }
                    if (i < entryUserList.length -1) {
                        entryUserName.append(",");
                    }
                }
            } catch (final BizApiException e) {
                throw new ApplicationRuntimeException(e);
            }
        }
        return entryUserName.toString();
    }

    /**
     * IMBox連携.
     * 新規登録したイベントの情報をIMBOXへ送信します.
     * @param eventDto イベント情報
     */
    public void linkIMBox(final EventDto eventDto) {   	
    	final String baseUrl = getBaseUrl();
        final Entry4NoticeMessage entry4NoticeMessage = new Entry4NoticeMessage();
        entry4NoticeMessage.setMessageText("新規イベントを登録しました." + "イベント名："
                + eventDto.eventName);
        entry4NoticeMessage.setMessageTypeCd("MESSAGE_TYPE_MESSAGE");
        entry4NoticeMessage.setSendUserCd(Contexts.get(AccountContext.class)
                .getUserCd());
        entry4NoticeMessage.setApplicationCd("tutorial");
        entry4NoticeMessage.setUri(baseUrl + "/event/confirm/"
                + eventDto.eventId);
        entry4NoticeMessage.setUriTitle("イベント詳細");
        String[] entryUserList = eventDto.entryUser.split(",");

        final ApplicationBoxService applicationBoxService = Services.get(ApplicationBoxService.class);
        try {
            applicationBoxService.sendNoticeMessage(entry4NoticeMessage,
                    entryUserList);
        } catch (final IMBoxException e) {
            LOGGER_DEBUG.error("IMBoxの登録に失敗しました", e);
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
