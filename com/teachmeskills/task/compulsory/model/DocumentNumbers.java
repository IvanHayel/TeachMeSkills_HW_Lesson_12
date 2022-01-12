package com.teachmeskills.task.compulsory.model;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

public class DocumentNumbers {
    private final int MAX_PATHS = 100;

    private LinkedList<Path> listOfPaths;
    private Map<String, Set<String>> correctNumbers;
    private Map<String, Set<String>> wrongNumbers;
    private Path reportPath = Paths.get("src/report");

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_PURPLE = "\u001B[35m";

    private static int nextReportID = 0;

    public DocumentNumbers() {
        listOfPaths = new LinkedList<>();
        correctNumbers = new HashMap<>();
        wrongNumbers = new HashMap<>();
    }

    public void enterPaths() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(ANSI_CYAN + "Enter paths for files ('confirm' to terminate)" + ANSI_RESET);
        int counter = 0;
        while (true) {
            System.out.print(ANSI_PURPLE + ++counter + " -> " + ANSI_RESET);
            String line = scanner.nextLine();
            if (line.equals("confirm") || counter > MAX_PATHS) {
                return;
            } else if (line.isBlank() || line.isEmpty()) {
                continue;
            }
            listOfPaths.add(Paths.get(line.strip()));
        }
    }

    public void clear() {
        correctNumbers = new HashMap<>();
        wrongNumbers = new HashMap<>();
    }

    public static boolean isDocumentNumber(String documentNumber) {
        return documentNumber.matches("contract\\w{7}") || documentNumber.matches("docnum\\w{9}");
    }

    public void generateReport() {
        try {
            obtainDocumentNumbers();
            Path reportDirectory = createTodayReportDirectory();
            List<Path> reportFiles = createReportFiles(reportDirectory);
            fillReportFiles(reportFiles);
            clear();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void obtainDocumentNumbers() throws IOException {
        while (!listOfPaths.isEmpty()) {
            Path path = listOfPaths.removeFirst();
            if (Files.exists(path)) {
                List<String> fileLines = Files.readAllLines(path);
                for (String line : fileLines) {
                    obtainDocumentNumber(line, path);
                }
            } else {
                System.out.println(ANSI_GREEN + "[INFO]: File with path '" + path + "' doesn't exist." + ANSI_RESET);
            }
        }
    }

    private void obtainDocumentNumber(String documentNumber, Path path) {
        if (isDocumentNumber(documentNumber)) {
            if (correctNumbers.containsKey(documentNumber)) {
                correctNumbers.get(documentNumber).add(path.getFileName().toString());
            } else {
                correctNumbers.put(documentNumber, new HashSet<>());
                correctNumbers.get(documentNumber).add(path.getFileName().toString());
            }
        } else {
            if (wrongNumbers.containsKey(documentNumber)) {
                wrongNumbers.get(documentNumber).add(path.getFileName().toString());
            } else {
                wrongNumbers.put(documentNumber, new HashSet<>());
                wrongNumbers.get(documentNumber).add(path.getFileName().toString());
            }
        }
    }

    private Path createTodayReportDirectory() throws IOException {
        Path todayReportDirectory = reportPath.resolve(LocalDate.now().toString());
        if (Files.notExists(todayReportDirectory)) {
            try {
                Files.createDirectory(todayReportDirectory);
            } catch (IOException e) {
                throw new IOException(ANSI_RED + "[ERROR]: Error with creating report folder." + ANSI_RESET);
            }
        }
        return todayReportDirectory;
    }

    private List<Path> createReportFiles(Path reportDirectory) throws IOException {
        List<Path> reportFiles = new ArrayList<>();
        Path pathCorrectDocumentNumbers = reportDirectory.resolve("correct_" + ++nextReportID);
        Path pathWrongDocumentNumbers = reportDirectory.resolve("wrong_" + nextReportID);
        try {
            if (Files.notExists(pathCorrectDocumentNumbers) || Files.notExists(pathWrongDocumentNumbers)) {
                Files.createFile(pathCorrectDocumentNumbers);
                Files.createFile(pathWrongDocumentNumbers);
                reportFiles.add(pathCorrectDocumentNumbers);
                reportFiles.add(pathWrongDocumentNumbers);
                return reportFiles;
            }
        } catch (IOException e) {
            throw new IOException(ANSI_RED + "[ERROR]: Error with creating report files." + ANSI_RESET);
        }
        System.out.println(ANSI_GREEN + "[INFO]: Report files already exist." + ANSI_RESET);
        return reportFiles;
    }

    private void fillReportFiles(List<Path> reportFiles) throws IOException {
        for (Path path : reportFiles) {
            if (path.getFileName().toString().startsWith("correct")) {
                for (Map.Entry<String, Set<String>> entry : correctNumbers.entrySet()) {
                    String reportLine = getReportLineCorrect(entry.getKey(), entry.getValue());
                    try {
                        Files.writeString(path, reportLine, StandardOpenOption.APPEND);
                    } catch (IOException e) {
                        throw new IOException(ANSI_RED +
                                "[ERROR]: Error with filling report file with correct numbers." + ANSI_RESET);
                    }
                }
                System.out.println(ANSI_GREEN + "[INFO]: Report file '" + path.getFileName() +
                        "' successfully generated." + ANSI_RESET);
            } else {
                for (Map.Entry<String, Set<String>> entry : wrongNumbers.entrySet()) {
                    String reportLine = getReportLineWrong(entry.getKey(), entry.getValue());
                    try {
                        Files.writeString(path, reportLine, StandardOpenOption.APPEND);
                    } catch (IOException e) {
                        throw new IOException(ANSI_RED +
                                "[ERROR]: Error with filling report file with wrong numbers." + ANSI_RESET);
                    }
                }
                System.out.println(ANSI_GREEN + "[INFO]: Report file '" + path.getFileName() +
                        "' successfully generated." + ANSI_RESET);
            }
        }
    }

    private String getReportLineCorrect(String documentNumber, Set<String> fileNames) {
        return String.format("%-30s\tDocument number from file: %s\n", documentNumber, fileNames);
    }

    private String getReportLineWrong(String wrongDocumentNumber, Set<String> fileNames) {
        return String.format("%-40s\t%-65s\tDocument number from file: %s\n",
                wrongDocumentNumber, getDocumentNumberProblem(wrongDocumentNumber), fileNames);
    }

    private String getDocumentNumberProblem(String wrongDocumentNumber) {
        if (wrongDocumentNumber.length() != 15) {
            return "The document number must be 15 characters long!";
        } else if (!wrongDocumentNumber.startsWith("contract") && !wrongDocumentNumber.startsWith("docnum")) {
            return "Document number must start with sequences 'contract' or 'docnum'!";
        } else {
            return "Document number contains invalid characters!";
        }
    }
}