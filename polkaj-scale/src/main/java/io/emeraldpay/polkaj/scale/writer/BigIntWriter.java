package io.emeraldpay.polkaj.scale.writer;

import io.emeraldpay.polkaj.scale.ScaleWriter;
import io.emeraldpay.polkaj.scale.ScaleCodecWriter;
import io.emeraldpay.polkaj.scale.reader.BigIntReader;

import java.io.IOException;
import java.math.BigInteger;

public class BigIntWriter implements ScaleWriter<BigInteger> {
    
    private int size;

    public BigIntWriter(int size) {
        this.size = size;
    }

    @Override
    public void write(ScaleCodecWriter wrt, BigInteger value) throws IOException {
        if (value.signum() < 0) {
            throw new IllegalArgumentException("Negative numbers are not supported by Uint64");
        }
        byte[] array = value.toByteArray();
        int pos = 0;
        // sometimes BigInteger gives an extra zero byte in the start of the array
        if (array[0] == 0) {
            pos++;
        }
        int len = array.length - pos;
        if (len > this.size) {
            throw new IllegalArgumentException("Value is to big for 64 bits. Has: " + len * 8 + " bits");
        }
        byte[] encoded = new byte[this.size];
        System.arraycopy(array, pos, encoded, encoded.length - len, len);
        BigIntReader.reverse(encoded);
        wrt.directWrite(encoded, 0, this.size);
    }
}
