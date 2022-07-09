package io.emeraldpay.polkaj.scale.reader

import io.emeraldpay.polkaj.scale.ScaleCodecReader
import org.apache.commons.codec.binary.Hex
import spock.lang.Specification

class UInt64ReaderSpec extends Specification {

    UInt64Reader reader = new UInt64Reader()

    def "Reads"() {
        when:
        def codec = new ScaleCodecReader(Hex.decodeHex("ffffff0000000000"))
        then:
        codec.hasNext()
        codec.read(reader) == 16777215
        !codec.hasNext()
    }

    def "Reads small number"() {
        when:
        def codec = new ScaleCodecReader(Hex.decodeHex("2a00000000000000"))
        then:
        codec.hasNext()
        codec.read(reader) == 42
        !codec.hasNext()
    }

    def "Reads large number"() {
        when:
        def codec = new ScaleCodecReader(Hex.decodeHex("ffffffffffffffff"))
        then:
        codec.hasNext()
        codec.read(reader) == 18446744073709551615
        !codec.hasNext()
    }

    def "Error for short"() {
        when:
        def codec = new ScaleCodecReader(Hex.decodeHex("ffffff"))
        codec.read(reader)
        then:
        thrown(IndexOutOfBoundsException)
    }

    def "Reads with zero prefix"() {
        when:
        def codec = new ScaleCodecReader(Hex.decodeHex("ffffff0000000000"))
        then:
        codec.read(reader).toString() == "16777215"
    }

    def "Reads optional none"() {
        when:
        def codec = new ScaleCodecReader(Hex.decodeHex("00"))
        then:
        codec.hasNext()
        codec.readOptional(reader) == Optional.empty()
        !codec.hasNext()
    }
}
