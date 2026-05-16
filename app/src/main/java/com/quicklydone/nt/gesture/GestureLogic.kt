package com.quicklydone.nt.gesture

import androidx.compose.ui.geometry.Offset
import com.quicklydone.nt.math.pointInPolygon
import com.quicklydone.nt.model.Face
import com.quicklydone.nt.model.Vec3
import com.quicklydone.nt.render.VisibleFace
import kotlin.math.abs
import kotlin.math.sqrt



data class HitFace(

    val normal: Vec3,

    val cubePos: Vec3,
    val side: Side,

    // world axes
    val uAxis: Vec3,
    val vAxis: Vec3,

     //screen-space axes
    val screenU: Offset,
   val screenV: Offset
)

enum class Side {
    FRONT, BACK, LEFT, RIGHT, TOP, BOTTOM
}

class GestureLogic {

    data class ProjectedDrag(
        val u: Float,
        val v: Float
    )

    // -------------------------------------------------------
    // PROJECT DRAG INTO FACE SCREEN SPACE
    // -------------------------------------------------------

    fun projectDrag(
        dx: Float,
        dy: Float,
        face: HitFace
    ): ProjectedDrag {

        val drag = Offset(dx, dy)

        // projection onto screen-space face axes

        val u =
            drag.x * face.screenU.x +
                    drag.y * face.screenU.y

        val v =
            drag.x * face.screenV.x +
                    drag.y * face.screenV.y

        return ProjectedDrag(
            u = u,
            v = v
        )
    }

    // -------------------------------------------------------
    // HIT TEST
    // -------------------------------------------------------

    fun detectFaceHit(
        pos: Offset,
        visibleFaces: List<VisibleFace>
    ): HitFace? {

        val hit =
            visibleFaces
                .asSequence()
                .filter {
                    pointInPolygon(
                        pos,
                        it.polygon
                    )
                }
                .maxByOrNull { it.depth }
                ?: return null

        return HitFace(

            normal = normalize(hit.normal),

            cubePos = hit.cubePos,

            side = hit.side,

            uAxis = normalize(hit.uAxis),
            vAxis = normalize(hit.vAxis),

            screenU = normalize2D(hit.screenU),
            screenV = normalize2D(hit.screenV)
        )
    }

    // -------------------------------------------------------
    // 3D NORMALIZE
    // -------------------------------------------------------

    private fun normalize(
        v: Vec3
    ): Vec3 {

        val len =
            sqrt(
                v.x * v.x +
                        v.y * v.y +
                        v.z * v.z
            )

        if (len < 1e-6f)
            return Vec3(0f, 0f, 0f)

        return Vec3(
            v.x / len,
            v.y / len,
            v.z / len
        )
    }

    // -------------------------------------------------------
    // 2D NORMALIZE
    // -------------------------------------------------------

    private fun normalize2D(
        o: Offset
    ): Offset {

        val len =
            sqrt(
                o.x * o.x +
                        o.y * o.y
            )

        if (len < 1e-6f)
            return Offset.Zero

        return Offset(
            o.x / len,
            o.y / len
        )
    }
}

fun snapAxis(v: Vec3): Vec3 {

    val ax = kotlin.math.abs(v.x)
    val ay = kotlin.math.abs(v.y)
    val az = kotlin.math.abs(v.z)

    return when {

        ax > ay && ax > az -> {

            Vec3(
                if (v.x > 0f) 1f else -1f,
                0f,
                0f
            )
        }

        ay > ax && ay > az -> {

            Vec3(
                0f,
                if (v.y > 0f) 1f else -1f,
                0f
            )
        }

        else -> {

            Vec3(
                0f,
                0f,
                if (v.z > 0f) 1f else -1f
            )
        }
    }

}


