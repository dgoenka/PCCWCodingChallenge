package com.divyanshgoenka.pccwcodingchallenge.android.util;

import com.divyanshgoenka.pccwcodingchallenge.PCCWCodingChallengeApplication;
import com.divyanshgoenka.pccwcodingchallenge.R;

import org.ocpsoft.prettytime.PrettyTime;

import java.net.ConnectException;
import java.net.UnknownHostException;
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

        if(e instanceof HttpException){
            HttpException httpException = (HttpException) e;
            if(httpException.code()>300)
                return PCCWCodingChallengeApplication.getInstance().getString(R.string.connection_error);
            else
                return ""+httpException.code();
        }else if(e instanceof ConnectException || e instanceof UnknownHostException){
            return PCCWCodingChallengeApplication.getInstance().getString(R.string.connection_error);
        }
        return e!=null?e.getClass().toString():null;
    }

    public static String contentForException(Throwable e) {

        if(e instanceof HttpException){
            HttpException httpException = (HttpException) e;
            return httpException.message();
        }else if(e instanceof ConnectException || e instanceof UnknownHostException){
            return e.getLocalizedMessage();
        }

        return e!=null?e.getClass().toString():null;
    }
}
