package ix.portal.npg.security.filter;

/**
 * Created by IntelliJ IDEA.
 * User: sadeghi
 * Date: Aug 22, 2010
 * Time: 9:47:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class DataModification {

    private String string;

    public DataModification(String string) {
        this.string = trimString(string);
    }

    private String substituePersianChars(String string) {
        string = string.replace((char) 1603, (char) 1705); // for persian KE
        string = string.replace((char) 1610, (char) 1740); // for persian YE

        //convert persian digit to latin digit
        string = string.replace((char) 1776, (char) 48); //Û±
        string = string.replace((char) 1777, (char) 49); //Û²
        string = string.replace((char) 1778, (char) 50); //Û³
        string = string.replace((char) 1779, (char) 51); //Û´
        string = string.replace((char) 1780, (char) 52); //Ûµ
        string = string.replace((char) 1781, (char) 53); //Û¶
        string = string.replace((char) 1782, (char) 54); //Û·
        string = string.replace((char) 1783, (char) 55); //Û¸
        string = string.replace((char) 1784, (char) 56); //Û¹
        string = string.replace((char) 1785, (char) 57); //Û°

        //convert arabic digit to latin digit
        string = string.replace((char) 1632, (char) 48); //Ù¡
        string = string.replace((char) 1633, (char) 49); //Ù¢
        string = string.replace((char) 1634, (char) 50); //Ù£
        string = string.replace((char) 1635, (char) 51); //Ù¤
        string = string.replace((char) 1636, (char) 52); //Ù¥
        string = string.replace((char) 1637, (char) 53); //Ù¦
        string = string.replace((char) 1638, (char) 54); //Ù§
        string = string.replace((char) 1639, (char) 55); //Ù¨
        string = string.replace((char) 1640, (char) 56); //Ù©
        string = string.replace((char) 1641, (char) 57); //Ù 
        return string;
    }

    private String trimString(String string) {
        if (string != null) {
            string = substituePersianChars(string).trim();
        }
        return string;
    }

    public String toString() {
        return string;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        String strObj = obj.toString();
        strObj = substituePersianChars(strObj);
        return strObj.equals(this.string);
    }

    /**
     * This method does DataModify on input string .
     * Reason append this method is modify data without construct new instance of this class .
     *
     * @param strBeforeModify the input string for modify .
     * @return modified string .
     */
    public String doDataModification(String strBeforeModify) {
        return trimString(strBeforeModify);
    }
}


