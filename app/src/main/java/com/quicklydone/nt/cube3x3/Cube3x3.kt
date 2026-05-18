package com.quicklydone.nt.model3x3

//import kotlin.collections.indices

class Cube3x3 {

    // 54 элемента (6 граней × 9)
    // U(0–8), L(9–17), F(18–26), R(27–35), B(36–44), D(45–53)
    var state: IntArray = IntArray(54)

    init {
        reset()
    }

    fun reset() {
        for (i in 0 until 54) {
            state[i] = i / 9
        }
     }

    fun applyMove33(perm: IntArray) {
        val old = state
        val new = IntArray(54)

        for (i in 0 until 54) {
            new[i] = old[perm[i]]
        }

        state = new
    }



    // -----------------------------
    // PERM STORAGE
    // -----------------------------

    companion object {

        // ВАЖНО: пока пустые — ты их подставляешь сам

        //val U_PERM = IntArray(54)

        val U_PERM  = IntArray(54).apply {
            // identity
            for (i in indices) this[i] = i

            //  грань
            cl(this, 29, 27, 33, 35)
            cl(this, 32, 28, 30, 34)

            // --- боковые ряды ---
            cl(this, 42, 2, 53, 17)
            cl(this,  51, 11, 44, 8)
            cl(this,  43, 5, 52, 14)
        }

        val U_PERM_BACK  = IntArray(54).apply {

            for (i in indices) this[i] = i


            //  грань
            this[29] =35
            this[35] =33
            this[33] =27
            this[27] =29

            this[32] =34
            this[34] =30
            this[30] =28
            this[28] =32

            // боковые ряды

            this[42] =17
            this[17] =53
            this[53] =2
            this[2] =42

            this[51] =8
            this[8] =44
            this[44] =11
            this[11] =51

            this[43] =14
            this[14] =52
            this[52] =5
            this[5] =43


        }
        val D_PERM = IntArray(54).apply {
            for (i in indices) this[i] = i

            //  грань
            cl(this, 20, 18, 24, 26)
            cl(this, 23, 19, 21, 25)

            // --- боковые ряды ---
            cl(this, 9, 45, 6, 38)
            cl(this, 0, 36,15, 47)
            cl(this, 12, 46, 3, 37)
        }
        val D_PERM_BACK = IntArray(54).apply {
            for (i in indices) this[i] = i

            //  грань
            cl(this, 20, 26,24 , 18)
            cl(this, 23, 25, 21, 19)

            // --- боковые ряды ---
            cl(this, 9, 38,6 , 45)
            cl(this, 0, 47, 15, 36)
            cl(this, 12, 37, 3, 46)
        }

        val F_PERM = IntArray(54).apply {
            // identity
            for (i in indices) this[i] = i

            //  грань
            cl(this, 44, 38, 36, 42)
            cl(this, 43, 41, 37, 39)

            // --- боковые ряды ---
            cl(this, 27, 17, 26, 6)
            cl(this,  29, 15, 24, 8)
            cl(this,  28, 16, 25, 7)
        }
        val F_PERM_BACK = IntArray(54).apply {
            // identity
            for (i in indices) this[i] = i

            //  грань
            cl(this, 36, 38, 44, 42)
            cl(this, 43, 39, 37, 41)

            // --- боковые ряды ---
            cl(this, 17, 27, 6, 26)
            cl(this,  29, 8, 24, 15)
            cl(this,  28, 7, 25, 16)
        }

        val B_PERM = IntArray(54).apply {
            for (i in indices) this[i] = i

            //  грань
            cl(this, 51,45 ,47 ,53 )
            cl(this,52 , 48, 45, 50)

            // --- боковые ряды ---
            cl(this, 33, 0, 20, 11)
            cl(this, 2, 18, 9, 35)
            cl(this, 1, 19, 10,34)
        }
        val B_PERM_BACK = IntArray(54).apply {
            for (i in indices) this[i] = i

            //  грань
            cl(this, 51, 53, 47, 45)
            cl(this, 52, 50, 46, 48)

            // --- боковые ряды ---
            cl(this, 33, 11, 20, 0)
            cl(this, 2, 35, 9, 18)
            cl(this, 34, 10, 19, 1)
        }

        val L_PERM = IntArray(54).apply {
            for (i in indices) this[i] = i

            //  грань
            cl(this, 11, 9, 15, 17)
            cl(this, 14, 10, 12, 16)

            // --- боковые ряды ---
            cl(this, 35, 47, 26, 44)
            cl(this, 53,20,38,29)
            cl(this, 32, 50, 23, 41)
        }



        val L_PERM_BACK = IntArray(54).apply {
            for (i in indices) this[i] = i

            //  грань
            cl(this, 11, 17, 15, 9)
            cl(this, 14, 16, 12, 10)

            // --- боковые ряды ---
            cl(this, 35, 44, 26, 47)
            cl(this, 53, 29,38 , 20)
            cl(this, 32,41 ,23 , 50)
        }



        val R_PERM = IntArray(54).apply {
            for (i in indices) this[i] = i

            //  грань
            cl(this, 2, 8, 6, 0)
            cl(this, 1, 5, 7, 3)

            // --- боковые ряды ---
            cl(this, 33, 42, 24,45 )
            cl(this, 27, 36, 18, 51)
            cl(this, 30, 39,21 ,48 )
        }
        val R_PERM_BACK = IntArray(54).apply {
            for (i in indices) this[i] = i

            //  грань
            cl(this, 2, 0, 6, 8)
            cl(this, 1, 3, 7,5 )

            // --- боковые ряды ---
            cl(this, 33, 45, 24,42 )
            cl(this, 27, 51, 18,36 )
            cl(this, 30, 48, 21, 39)
        }

    }
}

fun cl(p: IntArray, a: Int, b: Int, c: Int, d: Int) {
    p[a] = b
    p[b] = c
    p[c] = d
    p[d] = a
}

