package com.quicklydone.nt.render3x3

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import com.quicklydone.nt.model3x3.Cube3x3
import com.quicklydone.nt.render3x3.Cube3x3Renderer.faces

@Composable
fun Cube3x3View(
    cube: Cube3x3, yaw: Float, pitch: Float, modifier: Modifier = Modifier
) {
    Canvas(modifier) {

        val w = size.width
        val h = size.height

        val rotated = Cube3x3Renderer.cubePoints.map {
            Cube3x3Renderer.rotate(it, yaw, pitch)
        }

        val projected = rotated.map {
            Cube3x3Renderer.project(it, w, h)
        }


        val faceData = faces.mapIndexed { faceId, pair ->

            val indices = pair.second

            val pts = indices.map { projected[it] }
            val depth = indices.map { rotated[it].z }.average()

            Triple(faceId, pts, depth)
        }.sortedBy { it.third }

        // 🔥 Paint для текста
        val textPaint = Paint().apply {
            color = android.graphics.Color.BLACK
            textSize = size.minDimension / 15f
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }

        faceData.forEach { (faceId, pts, _) ->

            val cells = subdivideFace(
                pts[0], pts[1], pts[2], pts[3]


            )

            cells.forEachIndexed { cellIndex, cell ->

                val path = Path().apply {
                    moveTo(cell[0].x, cell[0].y)
                    lineTo(cell[1].x, cell[1].y)
                    lineTo(cell[2].x, cell[2].y)
                    lineTo(cell[3].x, cell[3].y)
                    close()
                }

                val globalIndex = faceId * 9 + cellIndex

                //val globalIndex = faceMap[faceId] * 9 + cellIndex


                val colorValue = cube.state[globalIndex]
                val color = colorOf(colorValue)

                // заливка
                drawPath(path, color)

                // границы
                drawPath(
                    path = path, color = Color.Black, style = Stroke(width = 1.5f)
                )

                // 🔥 центр ячейки
                val cx = (cell[0].x + cell[1].x + cell[2].x + cell[3].x) / 4f
                val cy = (cell[0].y + cell[1].y + cell[2].y + cell[3].y) / 4f

                // 🔥 контрастный цвет текста
                textPaint.color = if (color == Color.Black) android.graphics.Color.WHITE
                else android.graphics.Color.BLACK


                /*

                               val label = fixedLabels[globalIndex] ?: globalIndex.toString()

                               // 🔥 рисуем номер
                               drawContext.canvas.nativeCanvas.drawText(
                                   //globalIndex.toString(),
                                   label,
                                   cx,
                                   cy + textPaint.textSize / 3,
                                   textPaint
                               )

                */


                val label = fixedLabels[globalIndex]

                if (label != null) {
                    drawContext.canvas.nativeCanvas.drawText(
                        label, cx, cy + textPaint.textSize / 3, textPaint
                    )
                }


            }
        }
    }
}

val fixedLabels = mapOf(
    13 to "L", 31 to "U", 40 to "F", 4 to "R", 22 to "D", 49 to "B"
)

private fun subdivideFace(
    p0: Offset, p1: Offset, p2: Offset, p3: Offset
): List<List<Offset>> {

    val cells = mutableListOf<List<Offset>>()

    fun lerp(a: Offset, b: Offset, t: Float) = Offset(
        a.x + (b.x - a.x) * t, a.y + (b.y - a.y) * t
    )

    for (i in 0 until 3) {
        for (j in 0 until 3) {

            val u0 = i / 3f
            val u1 = (i + 1) / 3f
            val v0 = j / 3f
            val v1 = (j + 1) / 3f

            val a = lerp(lerp(p0, p1, u0), lerp(p3, p2, u0), v0)
            val b = lerp(lerp(p0, p1, u1), lerp(p3, p2, u1), v0)
            val c = lerp(lerp(p0, p1, u1), lerp(p3, p2, u1), v1)
            val d = lerp(lerp(p0, p1, u0), lerp(p3, p2, u0), v1)

            cells.add(listOf(a, b, c, d))
        }
    }

    return cells
}

fun colorOf(v: Int): Color = when (v) {
    0 -> Color.Green
    1 -> Color(0xFF1C5CF0) //Color.Blue
    2 -> Color(0xFFFFA500) // ← оранжевый
    3 -> Color.Red
    4 -> Color.White
    5 -> Color.Yellow
    else -> Color.Gray
}

