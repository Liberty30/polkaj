package io.emeraldpay.polkaj.scale.writer;

import io.emeraldpay.polkaj.scale.reader.BigIntReader;

public class UInt128Writer extends BigIntWriter {

    public UInt128Writer() {
        super(BigIntReader.UINT128_SIZE_BYTES);
    }
}
