/**
 * Berechnet das Lyndon-Array rekursiv
 *
 * @param word
 * @return Paar aus dem Lyndon-Array und den benötigten Schleifendurchläufen
 */
public fun duvalRec(word: String) : Pair<Array<Int>, Long> {
    val lambda = Array<Int>(word.length) { 0 }
    val comparisons = duvalRec(word, 0, word.length, lambda)
    return Pair(lambda, comparisons)
}

private fun duvalRec(word: String, offset: Int, length: Int, lambda: Array<Int>) : Long {

    var repeats : Long = 0

    val f = Array<Int>((length+1)/2 + 2){0}

    var k = offset - 1
    var j = offset + 1
    f[2] = 1

    while (k < length - 1 + offset) {
        var i = k + f[j - k]

        while (true) {
            repeats += 1
            if (j == length + offset  || word[i] > word[j]) {
                val deltaK = j - i

                val recStart = k+2
                val recLength = deltaK - 1

                if (deltaK > 1) {
                    val recComparisons = duvalRec(word, recStart, recLength, lambda)

                    repeats += recComparisons
                }

                //Optimierung
                lambda[k+1] = deltaK
                k += deltaK

                while (k < i) {
                    lambda[k+1] = deltaK
                    if (deltaK > 1) {
                        for (p in 0..<recLength) {
                            lambda[k+p+2] = lambda[recStart+p]
                        }
                    }

                    k += deltaK
                }

                if (k == j - 1) {
                    j += 1
                }
                break
            }
            if (word[i] < word[j]) {
                i = k + 1
                j += 1

                if (j-k <= (length + 1)/2) {
                    f[j-k] = i - k
                }

                continue
            }

            if (word[i] == word[j]) {
                i += 1
                j += 1

                if (j-k <= (length+1)/2) {
                    f[j-k] = i - k
                }
            }
        }
    }

    return repeats
}