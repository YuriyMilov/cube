package com.quicklydone.nt.cube_new

import com.quicklydone.nt.common.Vec3
import kotlin.math.abs

object CubeLogic222 {

    // ---------------------------------------------------------
    // VECTOR
    // ---------------------------------------------------------

    private fun dot(a: Vec3, b: Vec3): Float =
        a.x * b.x + a.y * b.y + a.z * b.z

    private fun neg(v: Vec3) =
        Vec3(-v.x, -v.y, -v.z)

    // ---------------------------------------------------------
    // ROOT
    // ---------------------------------------------------------

    fun root(cubelets: List<CubeletNew>): CubeletNew =
        cubelets.first { it.id == 0 }

    fun toRootSpace(
        v: Vec3,
        root: CubeletNew
    ): Vec3 =
        Vec3(
            dot(v, root.axisX),
            dot(v, root.axisY),
            dot(v, root.axisZ)
        )

    // ---------------------------------------------------------
    // SIDE
    // ---------------------------------------------------------

    fun logicalSide(
        worldNormal: Vec3,
        root: CubeletNew
    ): SideNew {

        val n = toRootSpace(worldNormal, root)

        return when {
            n.x > 0.9f -> SideNew.RIGHT
            n.x < -0.9f -> SideNew.LEFT
            n.y > 0.9f -> SideNew.TOP
            n.y < -0.9f -> SideNew.BOTTOM
            n.z > 0.9f -> SideNew.FRONT
            else -> SideNew.BACK
        }
    }

    // ---------------------------------------------------------
    // WORLD AXIS FOR LOGICAL SIDE
    // ---------------------------------------------------------

    fun worldAxis(
        side: SideNew,
        root: CubeletNew
    ): Vec3 =
        when (side) {
            SideNew.RIGHT -> root.axisX
            SideNew.LEFT -> neg(root.axisX)

            SideNew.TOP -> root.axisY
            SideNew.BOTTOM -> neg(root.axisY)

            SideNew.FRONT -> root.axisZ
            SideNew.BACK -> neg(root.axisZ)
        }

    // ---------------------------------------------------------
    // LAYER TEST
    // ---------------------------------------------------------

    fun isOnLogicalSide(
        cube: CubeletNew,
        side: SideNew,
        root: CubeletNew
    ): Boolean {

        val p = toRootSpace(cube.pos, root)

        return when (side) {
            SideNew.RIGHT -> p.x > 0f
            SideNew.LEFT -> p.x < 0f
            SideNew.TOP -> p.y > 0f
            SideNew.BOTTOM -> p.y < 0f
            SideNew.FRONT -> p.z > 0f
            SideNew.BACK -> p.z < 0f
        }
    }
}

object HintBuilder222 {

    private data class RingHint(
        val side: SideNew,
        val arrow: ArrowDirNew
    )

    fun showMoveHint(
        cubelets: List<CubeletNew>,
        markers: MutableList<FaceMarkerNew>,
        move: LogicalMove
    ) {
        markers.clear()

        val root = CubeLogic222.root(cubelets)

        when (move) {

            LogicalMove.R ->
                markRing(
                    cubelets,
                    markers,
                    SideNew.RIGHT,
                    true,
                    root
                )

            LogicalMove.R_PRIME ->
                markRing(
                    cubelets,
                    markers,
                    SideNew.RIGHT,
                    false,
                    root
                )

            LogicalMove.U ->
                markRing(
                    cubelets,
                    markers,
                    SideNew.TOP,
                    true,
                    root
                )

            LogicalMove.U_PRIME ->
                markRing(
                    cubelets,
                    markers,
                    SideNew.TOP,
                    false,
                    root
                )

            else -> {}
        }
    }

    private fun markRing(
        cubelets: List<CubeletNew>,
        markers: MutableList<FaceMarkerNew>,
        rotatingSide: SideNew,
        clockwise: Boolean,
        root: CubeletNew
    ) {

        val hints = when (rotatingSide) {

            SideNew.RIGHT ->
                if (clockwise)
                    listOf(
                        RingHint(SideNew.TOP, ArrowDirNew.NEG_U),
                        RingHint(SideNew.FRONT, ArrowDirNew.POS_V),
                        RingHint(SideNew.BOTTOM, ArrowDirNew.POS_V),
                        RingHint(SideNew.BACK, ArrowDirNew.NEG_U)
                    )
                else
                    listOf(
                        RingHint(SideNew.TOP, ArrowDirNew.POS_U),
                        RingHint(SideNew.FRONT, ArrowDirNew.NEG_V),
                        RingHint(SideNew.BOTTOM, ArrowDirNew.NEG_V),
                        RingHint(SideNew.BACK, ArrowDirNew.POS_U)
                    )

            SideNew.TOP ->
                if (clockwise)
                    listOf(
                        RingHint(SideNew.FRONT, ArrowDirNew.NEG_U),
                        RingHint(SideNew.RIGHT, ArrowDirNew.POS_V),
                        RingHint(SideNew.BACK, ArrowDirNew.POS_V),
                        RingHint(SideNew.LEFT, ArrowDirNew.NEG_U)
                    )
                else
                    listOf(
                        RingHint(SideNew.FRONT, ArrowDirNew.POS_U),
                        RingHint(SideNew.RIGHT, ArrowDirNew.NEG_U),
                        RingHint(SideNew.BACK, ArrowDirNew.POS_U),
                        RingHint(SideNew.LEFT, ArrowDirNew.NEG_U)
                    )

            else -> emptyList()
        }

        hints.forEach { hint ->

            cubelets
                .filter {
                    touchesLayer(
                        it,
                        rotatingSide,
                        root
                    )
                }
                .forEach { cube ->

                    markers += FaceMarkerNew(
                        side = hint.side,
                        cubePos = cube.pos,
                        arrow = hint.arrow
                    )
                }
        }
    }

    private fun touchesLayer(
        cube: CubeletNew,
        side: SideNew,
        root: CubeletNew
    ): Boolean {

        val p = CubeLogic222.toRootSpace(
            cube.pos,
            root
        )

        return when (side) {
            SideNew.RIGHT -> p.x > 0f
            SideNew.LEFT -> p.x < 0f
            SideNew.TOP -> p.y > 0f
            SideNew.BOTTOM -> p.y < 0f
            SideNew.FRONT -> p.z > 0f
            SideNew.BACK -> p.z < 0f
        }
    }
}

fun solveOneStep_R(
    cubelets: List<CubeletNew>,
    markers: MutableList<FaceMarkerNew>
) {
    HintBuilder222.showMoveHint(
        cubelets,
        markers,
        LogicalMove.R
    )
}

fun solveOneStep_R_PRIME(
    cubelets: List<CubeletNew>,
    markers: MutableList<FaceMarkerNew>
) {
    HintBuilder222.showMoveHint(
        cubelets,
        markers,
        LogicalMove.R_PRIME
    )
}

fun solveOneStep_U(
    cubelets: List<CubeletNew>,
    markers: MutableList<FaceMarkerNew>
) {
    HintBuilder222.showMoveHint(
        cubelets,
        markers,
        LogicalMove.U
    )
}

fun solveOneStep_U_PRIME(
    cubelets: List<CubeletNew>,
    markers: MutableList<FaceMarkerNew>
) {
    HintBuilder222.showMoveHint(
        cubelets,
        markers,
        LogicalMove.U_PRIME
    )
}

enum class LogicalMove {

    R,
    R_PRIME,

    U,
    U_PRIME,

    L,
    L_PRIME,

    D,
    D_PRIME,

    F,
    F_PRIME,

    B,
    B_PRIME
}