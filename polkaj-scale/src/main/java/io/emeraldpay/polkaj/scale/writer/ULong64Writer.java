package io.emeraldpay.polkaj.scale.writer;

import io.emeraldpay.polkaj.scale.ScaleWriter;
import io.emeraldpay.polkaj.scale.ScaleCodecWriter;
import io.emeraldpay.polkaj.scale.reader.UInt128Reader;

import java.nio.ByteBuffer;
import java.io.IOException;

public class ULong64Writer implements ScaleWriter<Long> {
    @Override
    public void write(ScaleCodecWriter wrt, Long value) throws IOException {
        if (value < 0) {
            throw new IllegalArgumentException("Negative numbers are not supported by Uint128");
        }
        byte[] array = ByteBuffer.allocate(Long.SIZE / Byte.SIZE).putLong(value).array();
        int pos = 0;
        // sometimes BigInteger gives an extra zero byte in the start of the array
        if (array[0] == 0) {
            pos++;
        }
        int len = array.length - pos;
        if (len > 8) {
            throw new IllegalArgumentException("Value is to big for 64 bits. Has: " + len + " bits");
        }
        byte[] encoded = new byte[8];
        System.arraycopy(array, pos, encoded, encoded.length - len, len);
        UInt128Reader.reverse(encoded);
        wrt.directWrite(encoded, 0, 8);
    }
}
