package com.lombok.bean;


import java.math.BigDecimal;

/**
 * Created by LENOVO on 2016/3/18.
 */

public class LombokTypeCompiler {
        private String string_;
        private Integer integer_;
        private int int__;
        private Double Double_;
        private double double__;
        private Character character_;
        private char char__;
        private Boolean isBoolean_;
        private boolean boolean__;
        private Long Long_;
        private long long__;
        private Byte Byte_;
        private byte byte__;
        private Short Short_;
        private short short__;
        private Float Float_;
        private float float__;
        private BigDecimal bigDecimal;


    @java.beans.ConstructorProperties({"string_", "integer_", "int__", "Double_", "double__", "character_", "char__", "isBoolean_", "boolean__", "Long_", "long__", "Byte_", "byte__", "Short_", "short__", "Float_", "float__", "bigDecimal"})
    public LombokTypeCompiler(String string_, Integer integer_, int int__, Double Double_, double double__, Character character_, char char__, Boolean isBoolean_, boolean boolean__, Long Long_, long long__, Byte Byte_, byte byte__, Short Short_, short short__, Float Float_, float float__, BigDecimal bigDecimal) {
        this.string_ = string_;
        this.integer_ = integer_;
        this.int__ = int__;
        this.Double_ = Double_;
        this.double__ = double__;
        this.character_ = character_;
        this.char__ = char__;
        this.isBoolean_ = isBoolean_;
        this.boolean__ = boolean__;
        this.Long_ = Long_;
        this.long__ = long__;
        this.Byte_ = Byte_;
        this.byte__ = byte__;
        this.Short_ = Short_;
        this.short__ = short__;
        this.Float_ = Float_;
        this.float__ = float__;
        this.bigDecimal = bigDecimal;
    }

    public LombokTypeCompiler() {
    }

    public String getString_() {
        return this.string_;
    }

    public Integer getInteger_() {
        return this.integer_;
    }

    public int getInt__() {
        return this.int__;
    }

    public Double getDouble_() {
        return this.Double_;
    }

    public double getDouble__() {
        return this.double__;
    }

    public Character getCharacter_() {
        return this.character_;
    }

    public char getChar__() {
        return this.char__;
    }

    public Boolean getIsBoolean_() {
        return this.isBoolean_;
    }

    public boolean isBoolean__() {
        return this.boolean__;
    }

    public Long getLong_() {
        return this.Long_;
    }

    public long getLong__() {
        return this.long__;
    }

    public Byte getByte_() {
        return this.Byte_;
    }

    public byte getByte__() {
        return this.byte__;
    }

    public Short getShort_() {
        return this.Short_;
    }

    public short getShort__() {
        return this.short__;
    }

    public Float getFloat_() {
        return this.Float_;
    }

    public float getFloat__() {
        return this.float__;
    }

    public BigDecimal getBigDecimal() {
        return this.bigDecimal;
    }

    public void setString_(String string_) {
        this.string_ = string_;
    }

    public void setInteger_(Integer integer_) {
        this.integer_ = integer_;
    }

    public void setInt__(int int__) {
        this.int__ = int__;
    }

    public void setDouble_(Double Double_) {
        this.Double_ = Double_;
    }

    public void setDouble__(double double__) {
        this.double__ = double__;
    }

    public void setCharacter_(Character character_) {
        this.character_ = character_;
    }

    public void setChar__(char char__) {
        this.char__ = char__;
    }

    public void setIsBoolean_(Boolean isBoolean_) {
        this.isBoolean_ = isBoolean_;
    }

    public void setBoolean__(boolean boolean__) {
        this.boolean__ = boolean__;
    }

    public void setLong_(Long Long_) {
        this.Long_ = Long_;
    }

    public void setLong__(long long__) {
        this.long__ = long__;
    }

    public void setByte_(Byte Byte_) {
        this.Byte_ = Byte_;
    }

    public void setByte__(byte byte__) {
        this.byte__ = byte__;
    }

    public void setShort_(Short Short_) {
        this.Short_ = Short_;
    }

    public void setShort__(short short__) {
        this.short__ = short__;
    }

    public void setFloat_(Float Float_) {
        this.Float_ = Float_;
    }

    public void setFloat__(float float__) {
        this.float__ = float__;
    }

    public void setBigDecimal(BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof LombokTypeCompiler)) return false;
        final LombokTypeCompiler other = (LombokTypeCompiler) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$string_ = this.string_;
        final Object other$string_ = other.string_;
        if (this$string_ == null ? other$string_ != null : !this$string_.equals(other$string_)) return false;
        final Object this$integer_ = this.integer_;
        final Object other$integer_ = other.integer_;
        if (this$integer_ == null ? other$integer_ != null : !this$integer_.equals(other$integer_)) return false;
        if (this.int__ != other.int__) return false;
        final Object this$Double_ = this.Double_;
        final Object other$Double_ = other.Double_;
        if (this$Double_ == null ? other$Double_ != null : !this$Double_.equals(other$Double_)) return false;
        if (Double.compare(this.double__, other.double__) != 0) return false;
        final Object this$character_ = this.character_;
        final Object other$character_ = other.character_;
        if (this$character_ == null ? other$character_ != null : !this$character_.equals(other$character_))
            return false;
        if (this.char__ != other.char__) return false;
        final Object this$isBoolean_ = this.isBoolean_;
        final Object other$isBoolean_ = other.isBoolean_;
        if (this$isBoolean_ == null ? other$isBoolean_ != null : !this$isBoolean_.equals(other$isBoolean_))
            return false;
        if (this.boolean__ != other.boolean__) return false;
        final Object this$Long_ = this.Long_;
        final Object other$Long_ = other.Long_;
        if (this$Long_ == null ? other$Long_ != null : !this$Long_.equals(other$Long_)) return false;
        if (this.long__ != other.long__) return false;
        final Object this$Byte_ = this.Byte_;
        final Object other$Byte_ = other.Byte_;
        if (this$Byte_ == null ? other$Byte_ != null : !this$Byte_.equals(other$Byte_)) return false;
        if (this.byte__ != other.byte__) return false;
        final Object this$Short_ = this.Short_;
        final Object other$Short_ = other.Short_;
        if (this$Short_ == null ? other$Short_ != null : !this$Short_.equals(other$Short_)) return false;
        if (this.short__ != other.short__) return false;
        final Object this$Float_ = this.Float_;
        final Object other$Float_ = other.Float_;
        if (this$Float_ == null ? other$Float_ != null : !this$Float_.equals(other$Float_)) return false;
        if (Float.compare(this.float__, other.float__) != 0) return false;
        final Object this$bigDecimal = this.bigDecimal;
        final Object other$bigDecimal = other.bigDecimal;
        if (this$bigDecimal == null ? other$bigDecimal != null : !this$bigDecimal.equals(other$bigDecimal))
            return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $string_ = this.string_;
        result = result * PRIME + ($string_ == null ? 0 : $string_.hashCode());
        final Object $integer_ = this.integer_;
        result = result * PRIME + ($integer_ == null ? 0 : $integer_.hashCode());
        result = result * PRIME + this.int__;
        final Object $Double_ = this.Double_;
        result = result * PRIME + ($Double_ == null ? 0 : $Double_.hashCode());
        final long $double__ = Double.doubleToLongBits(this.double__);
        result = result * PRIME + (int) ($double__ >>> 32 ^ $double__);
        final Object $character_ = this.character_;
        result = result * PRIME + ($character_ == null ? 0 : $character_.hashCode());
        result = result * PRIME + this.char__;
        final Object $isBoolean_ = this.isBoolean_;
        result = result * PRIME + ($isBoolean_ == null ? 0 : $isBoolean_.hashCode());
        result = result * PRIME + (this.boolean__ ? 79 : 97);
        final Object $Long_ = this.Long_;
        result = result * PRIME + ($Long_ == null ? 0 : $Long_.hashCode());
        final long $long__ = this.long__;
        result = result * PRIME + (int) ($long__ >>> 32 ^ $long__);
        final Object $Byte_ = this.Byte_;
        result = result * PRIME + ($Byte_ == null ? 0 : $Byte_.hashCode());
        result = result * PRIME + this.byte__;
        final Object $Short_ = this.Short_;
        result = result * PRIME + ($Short_ == null ? 0 : $Short_.hashCode());
        result = result * PRIME + this.short__;
        final Object $Float_ = this.Float_;
        result = result * PRIME + ($Float_ == null ? 0 : $Float_.hashCode());
        result = result * PRIME + Float.floatToIntBits(this.float__);
        final Object $bigDecimal = this.bigDecimal;
        result = result * PRIME + ($bigDecimal == null ? 0 : $bigDecimal.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof LombokTypeCompiler;
    }

    public String toString() {
        return "com.lombok.bean.LombokTypeCompiler(string_=" + this.string_ + ", integer_=" + this.integer_ + ", int__=" + this.int__ + ", Double_=" + this.Double_ + ", double__=" + this.double__ + ", character_=" + this.character_ + ", char__=" + this.char__ + ", isBoolean_=" + this.isBoolean_ + ", boolean__=" + this.boolean__ + ", Long_=" + this.Long_ + ", long__=" + this.long__ + ", Byte_=" + this.Byte_ + ", byte__=" + this.byte__ + ", Short_=" + this.Short_ + ", short__=" + this.short__ + ", Float_=" + this.Float_ + ", float__=" + this.float__ + ", bigDecimal=" + this.bigDecimal + ")";
    }
}
