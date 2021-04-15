package interview.league.matrix.controllers;

import interview.league.matrix.models.Matrix;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@RestController
public class MatrixOperationController {

    private static final String COMMA_DELIMITER = ",";
    private static final String CSV_EXTENSION = "csv";

    private List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(COMMA_DELIMITER);
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }

    private List<List<String>> parseInput(InputStream is) throws IOException {
        List<List<String>> records = new ArrayList<>();
        try (Scanner scanner = new Scanner(is,"UTF-8")) {
            while (scanner.hasNextLine()) {
                records.add(getRecordFromLine(scanner.nextLine()));
            }
        }
        return records;
    }

    private Matrix checkValidationAndParse(MultipartFile csvFile, HttpServletResponse response) throws IOException {
        if(csvFile == null || csvFile.getOriginalFilename().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Input file couldn't be null or empty.");
            return null;
        }
        String extension = csvFile.getOriginalFilename().split("\\.")[1];
        if(!extension.equals(CSV_EXTENSION)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Input file must be csv format.");
            return null;
        }
        boolean isValid = true;
        List<List<String>> content = parseInput(csvFile.getInputStream());
        List<List<Long>> matrix = new ArrayList<>();
        int matrixSize = content.size();
        String errorMessage = "";
        if(matrixSize > 0) {
            for (int i=0; i<matrixSize; i++){
                List<String> row = content.get(i);
                if(row.size() != matrixSize) {
                    isValid = false;
                    errorMessage = "Input matrix is not square";
                    break;
                }
                List<Long> rowInt = null;
                try {
                    rowInt = row.stream().map(n -> Long.valueOf(n)).collect(Collectors.toList());
                } catch (NumberFormatException ex) {
                    isValid = false;
                    errorMessage = "Input matrix is not valid because some elements are not integer (or exceed Long.MAX_VALUE)";
                    break;
                }
                matrix.add(rowInt);
            }
        }else {
            isValid = false;
            errorMessage = "Input matrix is empty";
        }
        if(!isValid) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, errorMessage);
            return null;
        }
        return new Matrix(matrix);
    }

    @PostMapping("/echo")
    @ApiOperation(value = "Echo operation: Return the matrix as a string in matrix format.")
    public String echo(@ApiParam(value = "A csv file that contains a matrix, has any dimension where the number of rows are equal to the number of columns (square). \n Each value is an integer, and there is no header row. \n An example likes following: \n 1,2,3\n4,5,6\n7,8,9")
                           @RequestParam("file")
                           MultipartFile file, HttpServletResponse response) throws IOException {
        Matrix matrix = checkValidationAndParse(file, response);
        if(matrix == null) {
            return null;
        }
        return matrix.echo();
    }

    @PostMapping("/invert")
    @ApiOperation(value = "Invert operation: Return the matrix as a string in matrix format where the columns and rows are inverted.")
    public String invert(@ApiParam(value = "A csv file that contains a matrix, has any dimension where the number of rows are equal to the number of columns (square). \n Each value is an integer, and there is no header row. \n An example likes following: \n 1,2,3\n4,5,6\n7,8,9")
                             @RequestParam("file") MultipartFile file, HttpServletResponse response) throws IOException {
        Matrix matrix = checkValidationAndParse(file, response);
        if(matrix == null) {
            return null;
        }
        return matrix.invert();
    }

    @PostMapping("/flatten")
    @ApiOperation(value = "Flatten operation: Return the matrix as a 1 line string, with values separated by commas.")
    public String flatten(@ApiParam(value = "A csv file that contains a matrix, has any dimension where the number of rows are equal to the number of columns (square). \n Each value is an integer, and there is no header row. \n An example likes following: \n 1,2,3\n4,5,6\n7,8,9")
                              @RequestParam("file") MultipartFile file, HttpServletResponse response) throws IOException {
        Matrix matrix = checkValidationAndParse(file, response);
        if(matrix == null) {
            return null;
        }
        return matrix.flatten();
    }

    @PostMapping("/sum")
    @ApiOperation(value = "Sum operation: Return the sum of the integers in the matrix.")
    public String sum(@ApiParam(value = "A csv file that contains a matrix, has any dimension where the number of rows are equal to the number of columns (square). \n Each value is an integer, and there is no header row. \n An example likes following: \n 1,2,3\n4,5,6\n7,8,9")
                          @RequestParam("file") MultipartFile file, HttpServletResponse response) throws IOException {
        Matrix matrix = checkValidationAndParse(file, response);
        if(matrix == null) {
            return null;
        }
        return matrix.sum();
    }

    @PostMapping("/multiply")
    @ApiOperation(value = "Multiply operation: Return the product of the integers in the matrix.")
    public String multiply(@ApiParam(value = "A csv file that contains a matrix, has any dimension where the number of rows are equal to the number of columns (square). \n Each value is an integer, and there is no header row. \n An example likes following: \n 1,2,3\n4,5,6\n7,8,9")
                               @RequestParam("file") MultipartFile file, HttpServletResponse response) throws IOException {
        Matrix matrix = checkValidationAndParse(file, response);
        if(matrix == null) {
            return null;
        }
        return matrix.multiply();
    }


    @PostMapping("/all")
    @ApiOperation(value = "All operation: Return all results of matrix operations.")
    public String all(@ApiParam(value = "A csv file that contains a matrix, has any dimension where the number of rows are equal to the number of columns (square). \n Each value is an integer, and there is no header row. \n An example likes following: \n 1,2,3\n4,5,6\n7,8,9")
                          @RequestParam("file") MultipartFile file, HttpServletResponse response) throws IOException {
        Matrix matrix = checkValidationAndParse(file, response);
        if(matrix == null) {
            return null;
        }
        return matrix.all();
    }
}
