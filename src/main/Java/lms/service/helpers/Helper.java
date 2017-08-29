package lms.service.helpers;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public interface Helper<E> {
    String CHECK_EMAIL_ADDRESS_REGEX =
            "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    static String requestParameter(String field, HttpServletRequest request) throws UnsupportedEncodingException {
        return new String(request.getParameter(field).getBytes("iso-8859-1"),"UTF-8");
    }
    
    String check(E lambdaExp);

    static <E> String checkFormField(int fieldNumber, String profileForm, E fieldValueNew, Helper<E> cautionMessage) {
        String message = cautionMessage.check(fieldValueNew);
        profileForm = profileForm.replace("userFieldNumber" + fieldNumber,  String.valueOf(fieldValueNew));
        if (fieldValueNew.equals("admin")&& fieldNumber == 9){
            profileForm = profileForm.replace("readonly" ,"");
        }
        if (message != null) {
            profileForm = profileForm.replace("group" + fieldNumber, "has-error");
            profileForm = profileForm.replace("<!--" + fieldNumber + "-->", message);
        }
        return profileForm;
    }
}
