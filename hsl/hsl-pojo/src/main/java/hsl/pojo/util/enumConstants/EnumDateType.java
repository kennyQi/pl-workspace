package hsl.pojo.util.enumConstants;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for EnumDateType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="EnumDateType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CheckInDay"/>
 *     &lt;enumeration value="StayDay"/>
 *     &lt;enumeration value="BookDay"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "EnumDateType")
@XmlEnum
public enum EnumDateType {

    @XmlEnumValue("CheckInDay")
    CheckInDay("CheckInDay"),
    @XmlEnumValue("StayDay")
    StayDay("StayDay"),
    @XmlEnumValue("BookDay")
    BookDay("BookDay");
    private final String value;

    EnumDateType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumDateType fromValue(String v) {
        for (EnumDateType c: EnumDateType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
