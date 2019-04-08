package com.zejian.emotionkeyboard.utils;

import android.content.Context;

import com.zejian.emotionkeyboard.R;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataUtils {

    public static List<ChatMsgType> getChatOptionDataListByType(Context cx, boolean isSingleChat) {
        List<ChatMsgType> list = new ArrayList<ChatMsgType>();

        ChatMsgType picLocal = new ChatMsgType();
        picLocal.setId(UUID.randomUUID().toString());
        picLocal.setContent(cx.getString(R.string.chat_msgtype_piclocal));
        picLocal.setRid(R.drawable.chat_icon_photo);
        picLocal.setType(ChatMsgType.CHAT_OPTION_PHOTO_FROMLOCAL);
        list.add(picLocal);

        ChatMsgType videoLocal = new ChatMsgType();
        videoLocal.setId(UUID.randomUUID().toString());
        videoLocal.setContent(cx.getString(R.string.chat_msgtype_videolocal));
        videoLocal.setRid(R.drawable.chat_icon_video);
        videoLocal.setType(ChatMsgType.CHAT_OPTION_VEDIO_FROMLOCAL);
        list.add(videoLocal);



        ChatMsgType picCamera = new ChatMsgType();
        picCamera.setId(UUID.randomUUID().toString());
        picCamera.setContent(cx.getString(R.string.chat_msgtype_piccamera));
        picCamera.setRid(R.drawable.chat_icon_camera);
        picCamera.setType(ChatMsgType.CHAT_OPTION_PHOTO_FROMCAMARA);
        list.add(picCamera);



//        ChatMsgType videoCamera = new ChatMsgType();
//        videoCamera.setId(UUID.randomUUID().toString());
//        videoCamera.setContent(cx.getString(R.string.chat_msgtype_videocamera));
//        videoCamera.setRid(R.drawable.chat_icon_videotape);
//        videoCamera.setType(ChatMsgType.CHAT_OPTION_VEDIO_FROMCAMARA);
//        list.add(videoCamera);


        ChatMsgType audio = new ChatMsgType();
        audio.setId(UUID.randomUUID().toString());
        audio.setContent(cx.getString(R.string.chat_msgtype_audio));
        audio.setRid(R.drawable.chat_icon_voice_call);
        audio.setType(ChatMsgType.CHAT_OPTION_VOICECALL);
        list.add(audio);
        ChatMsgType location = new ChatMsgType();
        location.setId(UUID.randomUUID().toString());
        location.setContent(cx.getString(R.string.chat_msgtype_location));
        location.setRid(R.drawable.chat_icon_position);
        location.setType(ChatMsgType.CHAT_OPTION_LOCATION);
        list.add(location);

        ChatMsgType contact = new ChatMsgType();
        contact.setId(UUID.randomUUID().toString());
        contact.setContent(cx.getString(R.string.chat_msgtype_contact));
        contact.setRid(R.drawable.chat_icon_contact);
        contact.setType(ChatMsgType.CHAT_OPTION_CONTACT);
        list.add(contact);

        ChatMsgType link = new ChatMsgType();
        link.setId(UUID.randomUUID().toString());
        link.setContent("link");
        link.setRid(R.drawable.chat_icon_link);
        link.setType(ChatMsgType.CHAT_OPTION_LINK);
        list.add(link);

        if (isSingleChat) {
            ChatMsgType optionDestrct = new ChatMsgType();
            optionDestrct.setId(UUID.randomUUID().toString());
            optionDestrct.setContent(cx.getString(R.string.chat_option_destrct));
            optionDestrct.setRid(R.drawable.chat_icon_private_chat);
            optionDestrct.setType(ChatMsgType.CHAT_OPTION_DESTRUCT_CHAT);
            list.add(optionDestrct);

//            ChatMsgType optionLocation = new ChatMsgType();
//            optionLocation.setId(UUID.randomUUID().toString());
//            optionLocation.setContent(cx.getString(R.string.chat_option_location));
//            optionLocation.setRid(R.drawable.chat_icon_band_position);
//            optionLocation.setType(ChatMsgType.CHAT_OPTION_LOCATION_CHAT);
//            list.add(optionLocation);
        }
        return list;
    }
}
