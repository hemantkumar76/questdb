// hi, I am hemant and i just changed this code and optimized this C code according to my knowledge as i am BTECH-Student (end year) accoridng to my knowledge i did it i hope thats works for the swag things :)

#include <jni.h>
#include <stdint.h>
#include "asmlib/asmlib.h"

extern "C" {

#ifdef __APPLE__
JNIEXPORT jlong JNICALL Java_io_questdb_std_Os_compareAndSwap
        (JNIEnv *e, jclass cl, jlong volatile ptr, jlong oldVal, jlong newVal) {
    return __atomic_compare_exchange_n(
            reinterpret_cast<int64_t *>(ptr),
            &oldVal,
            newVal,
            false,
            __ATOMIC_SEQ_CST,
            __ATOMIC_SEQ_CST
    );
}
#else
JNIEXPORT jlong JNICALL Java_io_questdb_std_Os_compareAndSwap
        (JNIEnv *e, jclass cl, jlong volatile ptr, jlong oldVal, jlong newVal) {
    return __atomic_compare_exchange_n(
            reinterpret_cast<int64_t *>(ptr),
            &oldVal,
            newVal,
            false,
            __ATOMIC_SEQ_CST,
            __ATOMIC_SEQ_CST
    );
}
#endif
}
