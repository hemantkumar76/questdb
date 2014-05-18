package org.nfsdb.examples.support;

import com.nfsdb.journal.JournalWriter;
import com.nfsdb.journal.exceptions.JournalException;
import org.nfsdb.examples.model.Quote;

import java.util.Random;

public class QuoteGenerator {
    public static void generateQuoteData(JournalWriter<Quote> w, int count) throws JournalException {
        generateQuoteData(w, count, 30);
    }

    public static void generateQuoteData(JournalWriter<Quote> w, int count, int days) throws JournalException {
        String symbols[] = {"AGK.L", "BP.L", "TLW.L", "ABF.L", "LLOY.L", "BT-A.L", "WTB.L", "RRS.L", "ADM.L", "GKN.L", "HSBA.L"};
        generateQuoteData(w, count, days, symbols);
    }

    public static void generateQuoteData(JournalWriter<Quote> w, int count, int days, String[] symbols) throws JournalException {
        long lo = System.currentTimeMillis();
        long hi = lo + ((long) days) * 24 * 60 * 60 * 1000L;
        long delta = (hi - lo) / count;

        Quote q = new Quote();
        Random r = new Random(System.currentTimeMillis());
        for (int i = 0; i < count; i++) {
            q.clear();
            q.setSym(symbols[Math.abs(r.nextInt() % (symbols.length))]);
            q.setAsk(Math.abs(r.nextDouble()));
            q.setBid(Math.abs(r.nextDouble()));
            q.setAskSize(Math.abs(r.nextInt() % 10000));
            q.setBidSize(Math.abs(r.nextInt() % 10000));
            q.setEx("LXE");
            q.setMode("Fast trading");
            // timestamp must always be in milliseconds
            q.setTimestamp(lo);
            w.append(q);
            lo += delta;
        }
        w.commit();
    }

    public static String[] randomSymbols(int count) {
        Random r = new Random(System.currentTimeMillis());
        String[] result = new String[count];
        for (int i = 0; i < result.length; i++) {
            result[i] = randomString(r, 4) + ".L";
        }
        return result;
    }

    public static String randomString(Random random, int len) {
        char chars[] = new char[len];
        for (int i = 0; i < len; i++) {
            chars[i] = (char) (Math.abs(random.nextInt() % 25) + 66);
        }
        return new String(chars);
    }
}
