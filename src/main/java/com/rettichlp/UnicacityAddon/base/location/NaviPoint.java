// ############################################################################
//
//       /.--------------.\
//      //                \\
//     //                  \\
//    || .-..----. .-. .--. ||
//    ||( ( '-..-'|.-.||.-.|||
//    || \ \  ||  || ||||_||||
//    ||._) ) ||  \'-'/||-' ||
//     \\'-'  `'   `-' `'  //
//      \\                //
//       \\______________//
//        '--------------'
//              |_|_
//       ____ _/ _)_)
//           '  | (_)
//        .--'"\| ()
//              | |
//              | |
//              |_|
//
// This file should not be modified and is therefore listed in the .gitignore
// file.
//
// The complete list of navigation points should not be published. As a
// result, here is only an incomplete list of navigation points.
// However, the functionality is the same as in the actual, complete list.
//
// When a release is created, the full list of navi points is used.
// ############################################################################

package com.rettichlp.UnicacityAddon.base.location;

import net.minecraft.util.math.BlockPos;

public enum NaviPoint {
    STADTHALLE("Stadthalle", 110, 69, 157);

    private final String name;
    private final int x;
    private final int y;
    private final int z;

    NaviPoint(String name, int x, int y, int z) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public BlockPos getBlockPos() {
        return new BlockPos(x, y, z);
    }

    public long getDistance(BlockPos blockPos) {
        return Math.round(blockPos.getDistance(x, y, z));
    }
}