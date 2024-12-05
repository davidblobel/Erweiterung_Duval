/**
 * Berechnet das Lyndon-Array iterative
 *
 * @param word
 * @return Paar aus dem Lyndon-Array und den benötigten Schleifendurchläufen
 */
public fun duvalIt(word: String) : Pair<Array<Int>,Long> {
    val lambda = Array<Int>(word.length){0}
    var repeats : Long = 0

    for (i in word.indices) {
        val lyndonPair = duvalFirst(word, i)
        lambda[i] = lyndonPair.first
        repeats += lyndonPair.second
    }
    return Pair(lambda, repeats)
}

private fun duvalFirst(word: String, offset: Int) : Pair<Int,Long> {
    val n = word.length
    var repeats : Long = 0

    var i = offset
    var j = offset + 1

    while (true) {
        repeats += 1
        if(j == n || word[i] > word[j]) {
            return Pair(j - i, repeats)
        }

        if(word[i] < word[j]) {
            i = offset
            j += 1
            continue
        }

        if (word[i] == word[j]) {
            i += 1
            j += 1
            continue
        }
    }
}