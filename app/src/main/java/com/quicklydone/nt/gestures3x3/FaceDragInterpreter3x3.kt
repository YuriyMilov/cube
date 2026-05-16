package com.quicklydone.nt.gestures3x3


import com.quicklydone.nt.model3x3.Move3x3
import com.quicklydone.nt.render3x3.Face

//import com.quicklydone.nt.common.Face

object FaceDragInterpreter3x3 {
    fun toMove(
        face: Face, startRow: Int, startCol: Int, endRow: Int, endCol: Int
    ): Move3x3 {
        return when (face) {
            Face.FRONT -> mapFRONT(startRow, startCol, endRow, endCol)
            Face.BACK -> mapBACK(startRow, startCol, endRow, endCol)
            Face.LEFT -> mapLEFT(startRow, startCol, endRow, endCol)
            Face.RIGHT -> mapRIGHT(startRow, startCol, endRow, endCol)
            Face.TOP -> mapTOP(startRow, startCol, endRow, endCol)
            Face.BOTTOM -> mapBOTTOM(startRow, startCol, endRow, endCol)
        }
    }
}

//   0 -> Color.Green
//   1 -> Color(0xFF1C5CF0) //Color.Blue
//   2 -> Color(0xFFFFA500) // ← оранжевый
//   3 -> Color.Red
//  4 -> Color.White
//  5 -> Color.Yellow
fun mapFRONT(
    startRow: Int, startCol: Int, endRow: Int, endCol: Int
): Move3x3 {

    return when {

        // 👉 строка 0 (нижняя)
        startRow == 0 && startCol == 0 && endRow == 2 && endCol == 0 -> Move3x3(2, 0, 1)
        startRow == 2 && startCol == 0 && endRow == 0 && endCol == 0 -> Move3x3(2, 0, -1)
        startRow == 1 && startCol == 0 && endRow == 2 && endCol == 0 -> Move3x3(2, 0, 1)
        startRow == 2 && startCol == 0 && endRow == 1 && endCol == 0 -> Move3x3(2, 0, -1)
        startRow == 0 && startCol == 0 && endRow == 1 && endCol == 0 -> Move3x3(2, 0, 1)
        startRow == 1 && startCol == 0 && endRow == 0 && endCol == 0 -> Move3x3(2, 0, -1)

        // 👉 строка 2 (верхняя)
        startRow == 0 && startCol == 2 && endRow == 2 && endCol == 2 -> Move3x3(3, 0, -1)
        startRow == 2 && startCol == 2 && endRow == 0 && endCol == 2 -> Move3x3(3, 0, 1)
        startRow == 1 && startCol == 2 && endRow == 2 && endCol == 2 -> Move3x3(3, 0, -1)
        startRow == 2 && startCol == 2 && endRow == 1 && endCol == 2 -> Move3x3(3, 0, 1)
        startRow == 0 && startCol == 2 && endRow == 1 && endCol == 2 -> Move3x3(3, 0, -1)
        startRow == 1 && startCol == 2 && endRow == 0 && endCol == 2 -> Move3x3(3, 0, 1)


        // 👉 колонка 0
        startCol == 0 && startRow == 0 && endCol == 2 && endRow == 0 -> Move3x3(1, 0, -1)
        startCol == 2 && startRow == 0 && endCol == 0 && endRow == 0 -> Move3x3(1, 0, 1)
        startCol == 1 && startRow == 0 && endCol == 2 && endRow == 0 -> Move3x3(1, 0, -1)
        startCol == 2 && startRow == 0 && endCol == 1 && endRow == 0 -> Move3x3(1, 0, 1)
        startCol == 0 && startRow == 0 && endCol == 1 && endRow == 0 -> Move3x3(1, 0, -1)
        startCol == 1 && startRow == 0 && endCol == 0 && endRow == 0 -> Move3x3(1, 0, 1)


        // 👉 колонка 2
        startCol == 0 && startRow == 2 && endCol == 2 && endRow == 2 -> Move3x3(0, 0, 1)
        startCol == 2 && startRow == 2 && endCol == 0 && endRow == 2 -> Move3x3(0, 0, -1)
        startCol == 1 && startRow == 2 && endCol == 2 && endRow == 2 -> Move3x3(0, 0, 1)
        startCol == 2 && startRow == 2 && endCol == 1 && endRow == 2 -> Move3x3(0, 0, -1)
        startCol == 0 && startRow == 2 && endCol == 1 && endRow == 2 -> Move3x3(0, 0, 1)
        startCol == 1 && startRow == 2 && endCol == 0 && endRow == 2 -> Move3x3(0, 0, -1)

        else -> Move3x3(-1, 0, 0)
    }

}

fun mapBACK(
    startRow: Int, startCol: Int, endRow: Int, endCol: Int
): Move3x3 {
    return when {

        // 👉 строка 0 (нижняя)
        startRow == 0 && startCol == 0 && endRow == 2 && endCol == 0 -> Move3x3(3, 0, 1)
        startRow == 2 && startCol == 0 && endRow == 0 && endCol == 0 -> Move3x3(3, 0, -1)
        startRow == 1 && startCol == 0 && endRow == 2 && endCol == 0 -> Move3x3(3, 0, 1)
        startRow == 2 && startCol == 0 && endRow == 1 && endCol == 0 -> Move3x3(3, 0, -1)
        startRow == 0 && startCol == 0 && endRow == 1 && endCol == 0 -> Move3x3(3, 0, 1)
        startRow == 1 && startCol == 0 && endRow == 0 && endCol == 0 -> Move3x3(3, 0, -1)

        // 👉 строка 2 (верхняя)
        startRow == 0 && startCol == 2 && endRow == 2 && endCol == 2 -> Move3x3(2, 0, -1)
        startRow == 2 && startCol == 2 && endRow == 0 && endCol == 2 -> Move3x3(2, 0, 1)
        startRow == 1 && startCol == 2 && endRow == 2 && endCol == 2 -> Move3x3(2, 0, -1)
        startRow == 2 && startCol == 2 && endRow == 1 && endCol == 2 -> Move3x3(2, 0, 1)
        startRow == 0 && startCol == 2 && endRow == 1 && endCol == 2 -> Move3x3(2, 0, -1)
        startRow == 1 && startCol == 2 && endRow == 0 && endCol == 2 -> Move3x3(2, 0, 1)


        // 👉 колонка 0
        startCol == 0 && startRow == 0 && endCol == 2 && endRow == 0 -> Move3x3(1, 0, -1)
        startCol == 2 && startRow == 0 && endCol == 0 && endRow == 0 -> Move3x3(1, 0, 1)
        startCol == 1 && startRow == 0 && endCol == 2 && endRow == 0 -> Move3x3(1, 0, -1)
        startCol == 2 && startRow == 0 && endCol == 1 && endRow == 0 -> Move3x3(1, 0, 1)
        startCol == 0 && startRow == 0 && endCol == 1 && endRow == 0 -> Move3x3(1, 0, -1)
        startCol == 1 && startRow == 0 && endCol == 0 && endRow == 0 -> Move3x3(1, 0, 1)


        // 👉 колонка 2
        startCol == 0 && startRow == 2 && endCol == 2 && endRow == 2 -> Move3x3(0, 0, 1)
        startCol == 2 && startRow == 2 && endCol == 0 && endRow == 2 -> Move3x3(0, 0, -1)
        startCol == 1 && startRow == 2 && endCol == 2 && endRow == 2 -> Move3x3(0, 0, 1)
        startCol == 2 && startRow == 2 && endCol == 1 && endRow == 2 -> Move3x3(0, 0, -1)
        startCol == 0 && startRow == 2 && endCol == 1 && endRow == 2 -> Move3x3(0, 0, 1)
        startCol == 1 && startRow == 2 && endCol == 0 && endRow == 2 -> Move3x3(0, 0, -1)


        else -> Move3x3(-1, 0, 0)
    }
}

fun mapLEFT(
    startRow: Int, startCol: Int, endRow: Int, endCol: Int
): Move3x3 {
    return when {
        // 👉 строка 0 (нижняя)
        startRow == 0 && startCol == 0 && endRow == 2 && endCol == 0 -> Move3x3(2, 0, -1)
        startRow == 2 && startCol == 0 && endRow == 0 && endCol == 0 -> Move3x3(2, 0, 1)
        startRow == 1 && startCol == 0 && endRow == 2 && endCol == 0 -> Move3x3(2, 0, -1)
        startRow == 2 && startCol == 0 && endRow == 1 && endCol == 0 -> Move3x3(2, 0, 1)
        startRow == 0 && startCol == 0 && endRow == 1 && endCol == 0 -> Move3x3(2, 0, -1)
        startRow == 1 && startCol == 0 && endRow == 0 && endCol == 0 -> Move3x3(2, 0, 1)

        // 👉 строка 2 (верхняя)
        startRow == 0 && startCol == 2 && endRow == 2 && endCol == 2 -> Move3x3(3, 0, 1)
        startRow == 2 && startCol == 2 && endRow == 0 && endCol == 2 -> Move3x3(3, 0, -1)
        startRow == 1 && startCol == 2 && endRow == 2 && endCol == 2 -> Move3x3(3, 0, 1)
        startRow == 2 && startCol == 2 && endRow == 1 && endCol == 2 -> Move3x3(3, 0, -1)
        startRow == 0 && startCol == 2 && endRow == 1 && endCol == 2 -> Move3x3(3, 0, 1)
        startRow == 1 && startCol == 2 && endRow == 0 && endCol == 2 -> Move3x3(3, 0, -1)


        // 👉 колонка 0
        startCol == 0 && startRow == 0 && endCol == 2 && endRow == 0 -> Move3x3(5, 0, 1)
        startCol == 2 && startRow == 0 && endCol == 0 && endRow == 0 -> Move3x3(5, 0, -1)
        startCol == 1 && startRow == 0 && endCol == 2 && endRow == 0 -> Move3x3(5, 0, 1)
        startCol == 2 && startRow == 0 && endCol == 1 && endRow == 0 -> Move3x3(5, 0, -1)
        startCol == 0 && startRow == 0 && endCol == 1 && endRow == 0 -> Move3x3(5, 0, 1)
        startCol == 1 && startRow == 0 && endCol == 0 && endRow == 0 -> Move3x3(5, 0, -1)


        // 👉 колонка 2
        startCol == 0 && startRow == 2 && endCol == 2 && endRow == 2 -> Move3x3(4, 0, -1)
        startCol == 2 && startRow == 2 && endCol == 0 && endRow == 2 -> Move3x3(4, 0, 1)
        startCol == 1 && startRow == 2 && endCol == 2 && endRow == 2 -> Move3x3(4, 0, -1)
        startCol == 2 && startRow == 2 && endCol == 1 && endRow == 2 -> Move3x3(4, 0, 1)
        startCol == 0 && startRow == 2 && endCol == 1 && endRow == 2 -> Move3x3(4, 0, -1)
        startCol == 1 && startRow == 2 && endCol == 0 && endRow == 2 -> Move3x3(4, 0, 1)


        else -> Move3x3(-1, 0, 0)
    }
}

fun mapRIGHT(
    startRow: Int, startCol: Int, endRow: Int, endCol: Int
): Move3x3 {
    return when {

        // 👉 строка 0 (нижняя)
        startRow == 0 && startCol == 0 && endRow == 2 && endCol == 0 -> Move3x3(3, 0, 1)
        startRow == 2 && startCol == 0 && endRow == 0 && endCol == 0 -> Move3x3(3, 0, -1)
        startRow == 1 && startCol == 0 && endRow == 2 && endCol == 0 -> Move3x3(3, 0, 1)
        startRow == 2 && startCol == 0 && endRow == 1 && endCol == 0 -> Move3x3(3, 0, -1)
        startRow == 0 && startCol == 0 && endRow == 1 && endCol == 0 -> Move3x3(3, 0, 1)
        startRow == 1 && startCol == 0 && endRow == 0 && endCol == 0 -> Move3x3(3, 0, -1)

        // 👉 строка 2 (верхняя)
        startRow == 0 && startCol == 2 && endRow == 2 && endCol == 2 -> Move3x3(2, 0, -1)
        startRow == 2 && startCol == 2 && endRow == 0 && endCol == 2 -> Move3x3(2, 0, 1)
        startRow == 1 && startCol == 2 && endRow == 2 && endCol == 2 -> Move3x3(2, 0, -1)
        startRow == 2 && startCol == 2 && endRow == 1 && endCol == 2 -> Move3x3(2, 0, 1)
        startRow == 0 && startCol == 2 && endRow == 1 && endCol == 2 -> Move3x3(2, 0, -1)
        startRow == 1 && startCol == 2 && endRow == 0 && endCol == 2 -> Move3x3(2, 0, 1)


        // 👉 колонка 0
        startCol == 0 && startRow == 0 && endCol == 2 && endRow == 0 -> Move3x3(4, 0, -1)
        startCol == 2 && startRow == 0 && endCol == 0 && endRow == 0 -> Move3x3(4, 0, 1)
        startCol == 1 && startRow == 0 && endCol == 2 && endRow == 0 -> Move3x3(4, 0, -1)
        startCol == 2 && startRow == 0 && endCol == 1 && endRow == 0 -> Move3x3(4, 0, 1)
        startCol == 0 && startRow == 0 && endCol == 1 && endRow == 0 -> Move3x3(4, 0, -1)
        startCol == 1 && startRow == 0 && endCol == 0 && endRow == 0 -> Move3x3(4, 0, 1)


        // 👉 колонка 2
        startCol == 0 && startRow == 2 && endCol == 2 && endRow == 2 -> Move3x3(5, 0, 1)
        startCol == 2 && startRow == 2 && endCol == 0 && endRow == 2 -> Move3x3(5, 0, -1)
        startCol == 1 && startRow == 2 && endCol == 2 && endRow == 2 -> Move3x3(5, 0, 1)
        startCol == 2 && startRow == 2 && endCol == 1 && endRow == 2 -> Move3x3(5, 0, -1)
        startCol == 0 && startRow == 2 && endCol == 1 && endRow == 2 -> Move3x3(5, 0, 1)
        startCol == 1 && startRow == 2 && endCol == 0 && endRow == 2 -> Move3x3(5, 0, -1)


        else -> Move3x3(-1, 0, 0)
    }
}

fun mapTOP(
    startRow: Int, startCol: Int, endRow: Int, endCol: Int
): Move3x3 {
    return when {

        // 👉 строка 0 (нижняя)
        startRow == 0 && startCol == 0 && endRow == 2 && endCol == 0 -> Move3x3(4, 0, -1)
        startRow == 2 && startCol == 0 && endRow == 0 && endCol == 0 -> Move3x3(4, 0, 1)
        startRow == 1 && startCol == 0 && endRow == 2 && endCol == 0 -> Move3x3(4, 0, -1)
        startRow == 2 && startCol == 0 && endRow == 1 && endCol == 0 -> Move3x3(4, 0, 1)
        startRow == 0 && startCol == 0 && endRow == 1 && endCol == 0 -> Move3x3(4, 0, -1)
        startRow == 1 && startCol == 0 && endRow == 0 && endCol == 0 -> Move3x3(4, 0, 1)

        // 👉 строка 2 (верхняя)
        startRow == 0 && startCol == 2 && endRow == 2 && endCol == 2 -> Move3x3(5, 0, 1)
        startRow == 2 && startCol == 2 && endRow == 0 && endCol == 2 -> Move3x3(5, 0, -1)
        startRow == 1 && startCol == 2 && endRow == 2 && endCol == 2 -> Move3x3(5, 0, 1)
        startRow == 2 && startCol == 2 && endRow == 1 && endCol == 2 -> Move3x3(5, 0, -1)
        startRow == 0 && startCol == 2 && endRow == 1 && endCol == 2 -> Move3x3(5, 0, 1)
        startRow == 1 && startCol == 2 && endRow == 0 && endCol == 2 -> Move3x3(5, 0, -1)


        // 👉 колонка 0
        startCol == 0 && startRow == 0 && endCol == 2 && endRow == 0 -> Move3x3(0, 0, 1)
        startCol == 2 && startRow == 0 && endCol == 0 && endRow == 0 -> Move3x3(0, 0, -1)
        startCol == 1 && startRow == 0 && endCol == 2 && endRow == 0 -> Move3x3(0, 0, 1)
        startCol == 2 && startRow == 0 && endCol == 1 && endRow == 0 -> Move3x3(0, 0, -1)
        startCol == 0 && startRow == 0 && endCol == 1 && endRow == 0 -> Move3x3(0, 0, 1)
        startCol == 1 && startRow == 0 && endCol == 0 && endRow == 0 -> Move3x3(0, 0, -1)


        // 👉 колонка 2
        startCol == 0 && startRow == 2 && endCol == 2 && endRow == 2 -> Move3x3(1, 0, -1)
        startCol == 2 && startRow == 2 && endCol == 0 && endRow == 2 -> Move3x3(1, 0, 1)
        startCol == 1 && startRow == 2 && endCol == 2 && endRow == 2 -> Move3x3(1, 0, -1)
        startCol == 2 && startRow == 2 && endCol == 1 && endRow == 2 -> Move3x3(1, 0, 1)
        startCol == 0 && startRow == 2 && endCol == 1 && endRow == 2 -> Move3x3(1, 0, -1)
        startCol == 1 && startRow == 2 && endCol == 0 && endRow == 2 -> Move3x3(1, 0, 1)


        else -> Move3x3(-1, 0, 0)
    }
}

fun mapBOTTOM(
    startRow: Int, startCol: Int, endRow: Int, endCol: Int
): Move3x3 {
    return when {
        // 👉 строка 0 (нижняя)
        startRow == 0 && startCol == 0 && endRow == 2 && endCol == 0 -> Move3x3(5, 0, 1)
        startRow == 2 && startCol == 0 && endRow == 0 && endCol == 0 -> Move3x3(5, 0, -1)
        startRow == 1 && startCol == 0 && endRow == 2 && endCol == 0 -> Move3x3(5, 0, 1)
        startRow == 2 && startCol == 0 && endRow == 1 && endCol == 0 -> Move3x3(5, 0, -1)
        startRow == 0 && startCol == 0 && endRow == 1 && endCol == 0 -> Move3x3(5, 0, 1)
        startRow == 1 && startCol == 0 && endRow == 0 && endCol == 0 -> Move3x3(5, 0, -1)

        // 👉 строка 2 (верхняя)
        startRow == 0 && startCol == 2 && endRow == 2 && endCol == 2 -> Move3x3(4, 0, -1)
        startRow == 2 && startCol == 2 && endRow == 0 && endCol == 2 -> Move3x3(4, 0, 1)
        startRow == 1 && startCol == 2 && endRow == 2 && endCol == 2 -> Move3x3(4, 0, -1)
        startRow == 2 && startCol == 2 && endRow == 1 && endCol == 2 -> Move3x3(4, 0, 1)
        startRow == 0 && startCol == 2 && endRow == 1 && endCol == 2 -> Move3x3(4, 0, -1)
        startRow == 1 && startCol == 2 && endRow == 0 && endCol == 2 -> Move3x3(4, 0, 1)

        // 👉 колонка 0
        startCol == 0 && startRow == 0 && endCol == 2 && endRow == 0 -> Move3x3(1, 0, -1)
        startCol == 2 && startRow == 0 && endCol == 0 && endRow == 0 -> Move3x3(1, 0, 1)
        startCol == 1 && startRow == 0 && endCol == 2 && endRow == 0 -> Move3x3(1, 0, -1)
        startCol == 2 && startRow == 0 && endCol == 1 && endRow == 0 -> Move3x3(1, 0, 1)
        startCol == 0 && startRow == 0 && endCol == 1 && endRow == 0 -> Move3x3(1, 0, -1)
        startCol == 1 && startRow == 0 && endCol == 0 && endRow == 0 -> Move3x3(1, 0, 1)


        // 👉 колонка 2
        startCol == 0 && startRow == 2 && endCol == 2 && endRow == 2 -> Move3x3(0, 0, 1)
        startCol == 2 && startRow == 2 && endCol == 0 && endRow == 2 -> Move3x3(0, 0, -1)
        startCol == 1 && startRow == 2 && endCol == 2 && endRow == 2 -> Move3x3(0, 0, 1)
        startCol == 2 && startRow == 2 && endCol == 1 && endRow == 2 -> Move3x3(0, 0, -1)
        startCol == 0 && startRow == 2 && endCol == 1 && endRow == 2 -> Move3x3(0, 0, 1)
        startCol == 1 && startRow == 2 && endCol == 0 && endRow == 2 -> Move3x3(0, 0, -1)


        else -> Move3x3(-1, 0, 0)
    }
}



