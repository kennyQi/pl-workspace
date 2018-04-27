package hsl.pojo.util.enumConstants;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for EnumGuaranteeMoneyType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="EnumGuaranteeMoneyType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="FirstNightCost"/>
 *     &lt;enumeration value="FullNightCost"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "EnumGuaranteeMoneyType")
@XmlEnum
public enum EnumGuaranteeMoneyType {

    @XmlEnumValue("FirstNightCost")
    FirstNightCost("FirstNightCost"),
    @XmlEnumValue("FullNightCost")
    FullNightCost("FullNightCost");
    private final String value;

    EnumGuaranteeMoneyType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumGuaranteeMoneyType fromValue(String v) {
        for (EnumGuaranteeMoneyType c: EnumGuaranteeMoneyType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
