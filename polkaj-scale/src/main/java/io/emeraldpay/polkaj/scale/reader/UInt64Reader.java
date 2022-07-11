package io.emeraldpay.polkaj.scale.reader;

import io.emeraldpay.polkaj.scale.ScaleReader;
import io.emeraldpay.polkaj.scale.ScaleCodecReader;
import java.math.BigInteger;

public class UInt64Reader extends BigIntReader implements ScaleReader<BigInteger> {

    BigIntReader bir = new BigIntReader();

    @Override
    public BigInteger read(ScaleCodecReader rdr) {
        byte[] value = rdr.readByteArray(bir.UINT64_SIZE_BYTES);
        bir.reverse(value);
        return new BigInteger(1, value);
    }
}
