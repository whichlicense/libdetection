// Generated by jextract

package com.whichlicense.metadata.identification.license.panama.internal;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.MemoryLayout;
import java.lang.invoke.MethodHandle;

final class constants$2 {
    static final FunctionDescriptor pipeline_batch_steps$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
            Constants$root.C_POINTER$LAYOUT,
            Constants$root.C_LONG_LONG$LAYOUT
    );
    static final MethodHandle pipeline_batch_steps$MH = RuntimeHelper.downcallHandle(
            "pipeline_batch_steps",
            constants$2.pipeline_batch_steps$FUNC
    );
    static final FunctionDescriptor fuzzy_pipeline_detect_license$FUNC = FunctionDescriptor.of(MemoryLayout.structLayout(
                    Constants$root.C_POINTER$LAYOUT.withName("step_matches"),
                    Constants$root.C_LONG_LONG$LAYOUT.withName("length")
            ).withName("PipelineLicenseMatches"),
            Constants$root.C_POINTER$LAYOUT,
            Constants$root.C_POINTER$LAYOUT,
            Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle fuzzy_pipeline_detect_license$MH = RuntimeHelper.downcallHandle(
            "fuzzy_pipeline_detect_license",
            constants$2.fuzzy_pipeline_detect_license$FUNC
    );
    static final FunctionDescriptor gaoya_pipeline_detect_license$FUNC = FunctionDescriptor.of(MemoryLayout.structLayout(
                    Constants$root.C_POINTER$LAYOUT.withName("step_matches"),
                    Constants$root.C_LONG_LONG$LAYOUT.withName("length")
            ).withName("PipelineLicenseMatches"),
            Constants$root.C_POINTER$LAYOUT,
            Constants$root.C_POINTER$LAYOUT,
            Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle gaoya_pipeline_detect_license$MH = RuntimeHelper.downcallHandle(
            "gaoya_pipeline_detect_license",
            constants$2.gaoya_pipeline_detect_license$FUNC
    );

    // Suppresses default constructor, ensuring non-instantiability.
    private constants$2() {
    }
}
