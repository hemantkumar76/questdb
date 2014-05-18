package com.nfsdb.journal;

import com.nfsdb.journal.logging.Logger;
import com.nfsdb.journal.test.model.Band;
import com.nfsdb.journal.test.tools.AbstractTest;
import com.nfsdb.journal.test.tools.TestUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BinaryTest extends AbstractTest {

    private static final Logger LOGGER = Logger.getLogger(BinaryTest.class);

    @Test
    public void testBinaryAppend() throws Exception {
        JournalWriter<Band> writer = factory.writer(Band.class);

        Random r = new Random(System.currentTimeMillis());
        List<byte[]> bytes = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            bytes.add(TestUtils.randomBytes(r, (3 - i) * 1024));
        }

        writer.append(new Band().setName("Supertramp").setType("jazz").setImage(bytes.get(0)));
        writer.append(new Band().setName("TinieTempah").setType("rap").setImage(bytes.get(1)));
        writer.append(new Band().setName("Rihanna").setType("pop").setImage(bytes.get(2)));
        writer.commit();

        int count = 0;
        for (Band b : writer) {
            Assert.assertArrayEquals(bytes.get(count), b.getImage());
            count++;
        }
    }

    @Test
    public void testBinaryPerformance() throws Exception {

        JournalWriter<Band> writer = factory.bulkWriter(Band.class);
        final int count = 20000;
        Random r = new Random(System.currentTimeMillis());

        byte[] bytes = TestUtils.randomBytes(r, 10240);
        String[] types = new String[]{"jazz", "rap", "pop", "rock", "soul"};
        String[] bands = new String[1200];
        for (int i = 0; i < bands.length; i++) {
            bands[i] = TestUtils.randomString(r, 10);
        }

        long t = System.nanoTime();
        Band band = new Band();
        for (int i = 0; i < count; i++) {
            band.clear();
            band.setName(bands[Math.abs(r.nextInt() % bands.length)]);
            band.setType(types[Math.abs(r.nextInt() % types.length)]);
            band.setImage(bytes);
            writer.append(band);
        }
        writer.commit();
        LOGGER.info("Appended " + count + " 10k blobs in " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - t) + "ms.");
    }
}
