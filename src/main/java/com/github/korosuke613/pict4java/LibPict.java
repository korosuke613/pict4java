package com.github.korosuke613.pict4java;

import com.sun.jna.*;
import com.sun.jna.ptr.IntByReference;
import java.io.File;
import java.io.IOException;
import java.lang.System;
import java.util.Arrays;
import java.util.List;
import org.scijava.nativelib.NativeLoader;

public class LibPict {
  // loadLibraryの第一引数はあとで作成するlib***.soの***と一致させる
  interface PictLib extends Library {
    PictLib INSTANCE =
        (PictLib)Native.load(Platform.isMac() ? "pict" : "aaaa", PictLib.class);

    // Cの関数名と一致させる
    Pointer PictCreateTask();
    Pointer PictCreateModel();
    void PictSetRootModel(Pointer task, Pointer model);
    Pointer PictAddParameter(Pointer model, int valueCount, int order,
                             int valueWeights[]);
    Pointer PictAddParameter(Pointer model, int valueCount, int order);
    int PictAddExclusion(Pointer task,
                         PICT_EXCLUSION_ITEM.ByReference exclusionItems,
                         int exclusionItemCount);
    int PictAddSeed(Pointer task, PICT_EXCLUSION_ITEM.ByReference seedItems,
                    int seedItemCount);
    int PictGenerate(Pointer task);
    IntByReference PictAllocateResultBuffer(Pointer task);
    int PictGetTotalParameterCount(Pointer task);
    int PictGetNextResultRow(Pointer task, IntByReference resultRow);
    void PictResetResultFetching(Pointer task);
  }

  public static class PICT_EXCLUSION_ITEM extends Structure {
    public Pointer Parameter;
    public int ValueIndex;
    public static class ByReference
        extends PICT_EXCLUSION_ITEM implements Structure.ByReference {}

    protected List<String> getFieldOrder() {
      return Arrays.asList("Parameter", "ValueIndex");
    }
  }
}
