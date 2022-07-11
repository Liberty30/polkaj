package io.emeraldpay.polkaj.scale.reader;

import io.emeraldpay.polkaj.scale.ScaleCodecReader;
import io.emeraldpay.polkaj.scale.ScaleReader;

import java.math.BigInteger;

public class UInt128Reader extends BigIntReader implements ScaleReader<BigInteger> {

    BigIntReader bir = new BigIntReader();

    @Override
    public BigInteger read(ScaleCodecReader rdr) {
        byte[] value = rdr.readByteArray(bir.UINT128_SIZE_BYTES);
        bir.reverse(value);
        return new BigInteger(1, value);
    }
}
