package lms.service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public interface Helper<E> {
    static String requestParameter(String field, HttpServletRequest request) throws UnsupportedEncodingException {
        return new String(request.getParameter(field).getBytes("iso-8859-1"),
                "UTF-8");
    }

    static <E> String checkFormField(int fieldNumber, String formStr, E field, Helper<E> ff) {
        String msg = ff.check(field);
        formStr = formStr.replace("xtrellovall" + fieldNumber, "value=\"" + String.valueOf(field) + "\"");
        if (msg != null) {
            formStr = formStr.replace("group" + fieldNumber, "has-error");
            formStr = formStr.replace("<!--" + fieldNumber + "-->", msg);
        }
        return formStr;
    }

    String check(E e);
}
