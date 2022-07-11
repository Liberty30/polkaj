package io.emeraldpay.polkaj.scale.writer;

import io.emeraldpay.polkaj.scale.ScaleCodecWriter;
import io.emeraldpay.polkaj.scale.ScaleWriter;
import io.emeraldpay.polkaj.scale.reader.BigIntReader;

import java.io.IOException;
import java.math.BigInteger;

public class UInt128Writer implements ScaleWriter<BigInteger> {

    @Override
    public void write(ScaleCodecWriter wrt, BigInteger value) throws IOException {
        if (value.signum() < 0) {
            throw new IllegalArgumentException("Negative numbers are not supported by Uint128");
        }
        byte[] array = value.toByteArray();
        int pos = 0;
        // sometimes BigInteger gives an extra zero byte in the start of the array
        if (array[0] == 0) {
            pos++;
        }
        int len = array.length - pos;
        if (len > BigIntReader.UINT128_SIZE_BYTES) {
            throw new IllegalArgumentException("Value is to big for 128 bits. Has: " + len * 8 + " bits");
        }
        byte[] encoded = new byte[BigIntReader.UINT128_SIZE_BYTES];
        System.arraycopy(array, pos, encoded, encoded.length - len, len);
        BigIntReader.reverse(encoded);
        wrt.directWrite(encoded, 0, BigIntReader.UINT128_SIZE_BYTES);
    }
}
