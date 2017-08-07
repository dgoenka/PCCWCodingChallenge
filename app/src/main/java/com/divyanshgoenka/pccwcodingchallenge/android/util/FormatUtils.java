package com.divyanshgoenka.pccwcodingchallenge.android.util;

import com.divyanshgoenka.pccwcodingchallenge.PCCWCodingChallengeApplication;
import com.divyanshgoenka.pccwcodingchallenge.R;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.HttpException;

/**
 * Created by divyanshgoenka on 08/08/17.
 */

public class FormatUtils {
    public static String parseForList(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date = sdf.parse(dateStr);
        PrettyTime p = new PrettyTime();
        return p.format(date);
    }

    public static String titleForException(Throwable e) {
        if(e instanceof HttpException)
            return PCCWCodingChallengeApplication.getInstance().getString(R.string.connection_error);
        return e!=null?e.getClass().toString():null;
    }

    public static String contentForException(Throwable e) {
        if(e instanceof HttpException)
            return PCCWCodingChallengeApplication.getInstance().getString(R.string.connection_error_message);
        return e!=null?e.getClass().toString():null;    }
}
