package io.emeraldpay.polkaj.scale.reader;

import io.emeraldpay.polkaj.scale.ScaleReader;
import io.emeraldpay.polkaj.scale.ScaleCodecReader;
import java.math.BigInteger;

public class BigIntReader implements ScaleReader<BigInteger> {
    private int size;
    public static final int UINT64_SIZE_BYTES = 8;
    public static final int UINT128_SIZE_BYTES = 16;

    public BigIntReader(int size) {
        this.size = size;
    }

    public static void reverse(byte[] value) {
        for (int i = 0; i < value.length / 2; i++) {
            int other = value.length - i - 1;
            byte tmp = value[other];
            value[other] = value[i];
            value[i] = tmp;
        }
    }

    @Override
    public BigInteger read(ScaleCodecReader rdr) {
        byte[] value = rdr.readByteArray(this.size);
        reverse(value);
        return new BigInteger(1, value);
    }
}
