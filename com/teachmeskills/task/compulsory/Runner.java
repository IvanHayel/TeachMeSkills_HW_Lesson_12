package com.teachmeskills.task.compulsory;

import com.teachmeskills.task.compulsory.model.DocumentNumbers;

/**
 * Let's go back to the homework for lesson 11 and modify it.
 * The program should receive file names with document numbers in the console:
 * each new line is the path to the file and the name of the file.
 * Enter 0 to complete the list of input files.
 * After receiving the list of documents, the program must process each document:
 * subtract the document numbers from the file and the crash.
 * At the end of the work, create one report file with the output information:
 * document number - comment (valid or not valid and for what reason).
 * Let each file contain each document number with a new line and in the
 * line of any other information, only the document number.
 * Valid document number Must consist of 15 characters and begin with the sequence
 * docnum (hereinafter any sequence of letters / numbers) or contract (hereinafter any sequence of letters / numbers).
 * Take into account that the document numbers are repeated within the same file, and
 * different documents are also drawn up within the same document number.
 * If the document numbers are repeated, then the repeated document numbers are not checked.
 */

public class Runner {
    public static void main(String[] args) {
        DocumentNumbers numbers = new DocumentNumbers();
        /*
         * ~ Test input ~
         * 1 -> src/resources/document_numbers/doc_nums_from_Simon.txt
         * 2 -> src/resources/document_numbers/numbers.txt
         * 3 -> src/resources/document_numbers/SomeDocNums.txt
         * 4 -> src/resources/document_numbers/template_doc_nums.txt
         * 5 -> confirm
         */
        numbers.enterPaths();
        numbers.generateReport();
        /*
         * ~ Test input ~
         * 1 -> src/resources/document_numbers/doc_nums_from_Simon.txt
         * 2 -> src/resources/document_numbers/numbers.txt
         * 3 -> confirm
         */
        numbers.enterPaths();
        numbers.generateReport();
    }
}