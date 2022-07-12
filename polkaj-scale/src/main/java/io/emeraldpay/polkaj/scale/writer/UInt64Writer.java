package io.emeraldpay.polkaj.scale.writer;

import io.emeraldpay.polkaj.scale.reader.BigIntReader;

public class UInt64Writer extends BigIntWriter {
    
    public UInt64Writer() {
        super(BigIntReader.UINT64_SIZE_BYTES);
    }
}
