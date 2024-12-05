import java.io.File
import java.io.IOException

/**
 * Generiert einen zufälligen String mit der Länge [length], mit den Zeichen aus [charPool]
 *
 * @param charPool
 * @param length
 * @return Zufällig generierter String
 */
fun randomString(length: Int, charPool: List<Char>) : String {

    val randomString = (1..length).map { charPool.random() }.joinToString("")
    return randomString
}

/**
 * Testet die rekursive und iterative Berechnung des Lyndon-Arrays, mittels [duvalIt] und [duvalRec], mit zufällig generierten Strings, welche mit den Zeichen in [charPool] gebildet werden.
 * Die benötigten Schleifendurchläufe werden, abhängig von der Stringlänge und dem verwendeten Algorithmus, als CSV-Datei im [outputPath] gespeichert.
 *
 * @param charPool
 * @param outputPath
 * @see duvalIt
 * @see duvalRec
 */
@Throws(IOException::class)
fun testAlphabet(charPool: List<Char>, outputPath: String) {
    val file = File(outputPath)
    val writer = file.bufferedWriter()

    val header = listOf("Wortlänge", "Rekursiv", "Iterativ", "Valide").joinToString(separator = ",")

    try {
        writer.write(header)
        writer.newLine()
    } catch (exception: Exception) {
        println(exception.message)
        return
    }

    for (length in 50..2000 step 50) {

        var valid = true

        var recComparisonSum : Long = 0
        var itComparisonSum : Long = 0

        val repeats = 1000
        repeat(repeats) {
            val word = randomString(length, charPool)

            val lynArrayRec = duvalRec(word)
            val lynArrayIt = duvalIt(word)

            for (i in lynArrayIt.first.indices) {
                if (lynArrayRec.first[i] != lynArrayIt.first[i]) {
                    valid = false
                    println("Analysis of $word faulty at index $i")
                    break
                }
            }

            recComparisonSum += lynArrayRec.second
            itComparisonSum += lynArrayIt.second

        }

        val recAvgComparisons = recComparisonSum / repeats
        val itAvgComparisons = itComparisonSum / repeats

        val line = listOf(length, recAvgComparisons, itAvgComparisons, valid).joinToString(separator = ",")

        try {
            writer.write(line)
            writer.newLine()
        } catch (exception: Exception) {
            println(exception.message)
            return
        }

    }

    try {
        writer.close()
    } catch (exception: Exception) {
        println(exception.message)
        return
    }

}

/**
 * Testet die rekursive und iterative Berechnung des Lyndon-Arrays, mittels [duvalIt] und [duvalRec], mit der Datei, welche im [inputPath] gespeichert ist.
 * Die benötigten Schleifendurchläufe werden als CSV-Datei im [outputPath] gespeichert.
 *
 * @param inputPath
 * @param outputPath
 * @see duvalIt
 * @see duvalRec
 */
fun testFile(inputPath: String, outputPath: String) {
    val outputFile = File(outputPath)
    val writer = outputFile.bufferedWriter()

    val inputString : String

    try {
        inputString = File(inputPath).readText(Charsets.UTF_8)

    } catch (exception: Exception) {
        println(exception.message)
        return
    }


    val header = listOf("Rekursiv", "Iterativ", "Valide").joinToString(separator = ",")

    try {
        writer.write(header)
        writer.newLine()
    } catch (exception: Exception) {
        println(exception.message)
        return
    }


    var valid = true

    val lynArrayRec = duvalRec(inputString)
    val lynArrayIt = duvalIt(inputString)

    for (i in lynArrayIt.first.indices) {
        if (lynArrayRec.first[i] != lynArrayIt.first[i]) {
            valid = false
            println("Analysis of file faulty at index $i")
            break
        }
    }

    val line = listOf(lynArrayRec.second, lynArrayIt.second, valid).joinToString(separator = ",")

    try {
        writer.write(line)
        writer.newLine()

        writer.close()
    } catch (exception: Exception) {
        println(exception.message)
        return
    }

}