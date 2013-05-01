package org.tuenti.contest.solver;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * User: robertcorujo
 */
public class IntegerFinder {

    private static final int numBitsPerPosition = 32;
    private static final int numIntBytes = (2147483647/numBitsPerPosition)+1;
    private int[] intBytes;
    private byte[] buffer = new byte[4];

    public IntegerFinder() {
        initIntegerStorage();

        processIntegers();
    }

    public Integer findNthInteger(Integer nth) {
        return findnth(nth);

    }

    private Integer findnth(Integer nth) {
        int position = 0;
        int foundBefore = 0;
        for (int b : intBytes) {
            int test = 0xffffffff;

            int count = Integer.bitCount(b ^ test);

            if (count > 0) {
                if (foundBefore + count >= nth) {
                    int offset = getNthZeroBit(nth-foundBefore,b);
                    return position * numBitsPerPosition + offset;
                }
                foundBefore += count;
            }
            position++;
        }

        return -1;
    }

    private int getNthZeroBit(Integer nth, int representation) {
        int count = 0;
        for (int i = 0; i < 32; i++) {
            if (((representation >> i) & 0x0001) == 0x0000) {
                count++;
            }
            if (count == nth) {
                return i;
            }
        }
        throw new Error("Unknown error, can't there be less than nth 0 in this representation");
    }

    private void initIntegerStorage() {
        intBytes = new int[numIntBytes];
        for (int i = 0; i < numIntBytes; i++) {
            intBytes[i] = 0;
        }
    }


    private void processIntegers() {
        BufferedInputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream("integers"));

            Integer integer = getNextInteger(inputStream);
            while (integer >= 0) {
                addIntegerAsSeen(integer);
                integer =  getNextInteger(inputStream);
            }
        } catch (IOException e) {
            throw new Error("Error reading file 'integers'",e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new Error("Error closing file 'integer'",e);
                }
            }
        }
    }

    private void addIntegerAsSeen(Integer integer) {
        int byteOrder = integer / numBitsPerPosition;
        int offset = integer % numBitsPerPosition;
        int newBit = 0x0001;
        newBit = newBit << offset;
        intBytes[byteOrder] = intBytes[byteOrder] | newBit;
    }

    private Integer getNextInteger(InputStream inputStream) throws IOException {
        int bytesRead = inputStream.read(buffer);
        if (bytesRead < 0) {
            return -1;
        }

        ByteBuffer bb = ByteBuffer.wrap(buffer);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        return bb.getInt();
    }
}
