// Generated by jextract

package com.whichlicense.metadata.identification.license.panama.internal;

import com.whichlicense.foreign.ForeignRuntimeHelper;

import java.lang.foreign.*;
import java.lang.invoke.VarHandle;

import static java.lang.foreign.ValueLayout.PathElement;

/**
 * {@snippet :
 * struct PipelineLicenseMatches {
 *     struct LicenseMatches* step_matches;
 *     uintptr_t length;
 * };
 *}
 */
public class PipelineLicenseMatches {
    static final StructLayout $struct$LAYOUT = MemoryLayout.structLayout(
            Constants$root.C_POINTER$LAYOUT.withName("step_matches"),
            Constants$root.C_LONG_LONG$LAYOUT.withName("length")
    ).withName("PipelineLicenseMatches");
    static final VarHandle step_matches$VH = $struct$LAYOUT.varHandle(PathElement.groupElement("step_matches"));
    static final VarHandle length$VH = $struct$LAYOUT.varHandle(PathElement.groupElement("length"));

    public static MemoryLayout $LAYOUT() {
        return PipelineLicenseMatches.$struct$LAYOUT;
    }

    /**
     * Getter for field:
     * {@snippet :
     * struct LicenseMatches* step_matches;
     *}
     */
    public static MemorySegment step_matches$get(MemorySegment seg) {
        return (MemorySegment) PipelineLicenseMatches.step_matches$VH.get(seg);
    }

    /**
     * Getter for field:
     * {@snippet :
     * uintptr_t length;
     *}
     */
    public static long length$get(MemorySegment seg) {
        return (long) PipelineLicenseMatches.length$VH.get(seg);
    }

    public static MemorySegment allocate(SegmentAllocator allocator) {
        return allocator.allocate($LAYOUT());
    }

    public static MemorySegment ofAddress(MemorySegment addr, SegmentScope scope) {
        return ForeignRuntimeHelper.asArray(addr, $LAYOUT(), 1, scope);
    }
}
