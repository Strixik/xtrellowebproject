package lms.service.helpers;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public interface Helper<E> {
    String CHECK_EMAIL_REGEX = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    String check(E e);

    static <E> String checkFormField(int fieldNumber, String formStr, E field, Helper<E> ff) {
        String msg = ff.check(field);
        formStr = formStr.replace("xtrellovall" + fieldNumber, "value=\"" + String.valueOf(field) + "\"");
        if (msg != null) {
            formStr = formStr.replace("group" + fieldNumber, "has-error");
            formStr = formStr.replace("<!--" + fieldNumber + "-->", msg);
        }
        return formStr;
    }

    static String requestParameter(String field, HttpServletRequest request) throws UnsupportedEncodingException {
        return new String(request.getParameter(field).getBytes("UTF-8"));
    }
}
