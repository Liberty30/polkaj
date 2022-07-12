package io.emeraldpay.polkaj.scale.writer

import io.emeraldpay.polkaj.scale.ScaleCodecWriter
import org.apache.commons.codec.binary.Hex
import spock.lang.Specification

class UInt64WriterSpec extends Specification {

    UInt64Writer writer = new UInt64Writer()
    ByteArrayOutputStream buf = new ByteArrayOutputStream()
    ScaleCodecWriter codec = new ScaleCodecWriter(buf)

    def "Writes small number"() {
        when:
        def BigInteger x = 42
        codec.write(writer, x)
        def act = buf.toByteArray()
        then:
        Hex.encodeHexString(act) == "2a00000000000000"
    }

    def "Writes larger number"() {
        when:
        def BigInteger x = 16777215
        codec.write(writer, x)
        def act = buf.toByteArray()
        then:
        Hex.encodeHexString(act) == "ffffff0000000000"
    }

        def "Writes max number"() {
        when:
        def BigInteger x = 18446744073709551615
        codec.write(writer, x)
        def act = buf.toByteArray()
        then:
        Hex.encodeHexString(act) == "ffffffffffffffff"
    }

    def "Writes optional existing"() {
        when:
        def BigInteger x = 16777215
        codec.writeOptional(writer, x)
        def act = buf.toByteArray()
        then:
        Hex.encodeHexString(act) == "01ffffff0000000000"
    }

    def "Writes optional empty"() {
        when:
        codec.writeOptional(writer, Optional.empty())
        def act = buf.toByteArray()
        then:
        Hex.encodeHexString(act) == "00"
    }

    def "Writes optional null"() {
        when:
        codec.writeOptional(writer, (Integer)null)
        def act = buf.toByteArray()
        then:
        Hex.encodeHexString(act) == "00"
    }

    def "Writes all cases"() {
        expect:
        ByteArrayOutputStream buf = new ByteArrayOutputStream()
        new ScaleCodecWriter(buf).write(writer, value)
        Hex.encodeHexString(buf.toByteArray()) == encoded
        where:
        encoded     | value
        "0000000000000000"  | new BigInteger(0x00_00_00_00_00_00_00_00)
        "000000000000ff00"  | new BigInteger(0x00_ff_00_00_00_00_00_00)
        "0000000000ff0000"  | new BigInteger(0x00_00_ff_00_00_00_00_00)
        "00000000ff000000"  | new BigInteger(0x00_00_00_ff_00_00_00_00)
        "000000000f0f0f0f"  | new BigInteger(0x0f_0f_0f_0f_00_00_00_00)

        "00000000ffffff00"  | new BigInteger(0x00_ff_ff_ff_00_00_00_00)
        "0000000000060000"  | new BigInteger(0x00_00_06_00_00_00_00_00)
        "0000000000030000"  | new BigInteger(0x00_00_03_00_00_00_00_00)
        "000000007d010000"  | new BigInteger(0x00_00_01_7d_00_00_00_00)
        "ffffffffffffffff"  | 0xff_ff_ff_ff_ff_ff_ff_ff
    }

    def "Error for negative number"() {
        when:
        def BigInteger x = -1
        codec.write(writer, x)
        then:
        thrown(IllegalArgumentException)
    }

}
