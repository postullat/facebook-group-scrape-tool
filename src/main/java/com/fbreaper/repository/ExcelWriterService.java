package com.fbreaper.repository;

import com.fbreaper.domain.PostDto;
import com.fbreaper.fbuiapi.entities.PostUiRepresentation;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelWriterService {

    private static String[] columns = {"Author", "Date", "Content"};


    public void write(List<PostDto> posts) {
        // Create a Workbook
        Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

        /* CreationHelper helps us create instances of various things like DataFormat,
           Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
        CreationHelper createHelper = workbook.getCreationHelper();

        // Create a Sheet
        Sheet sheet = workbook.createSheet("Employee");

        CellStyle hlinkstyle = workbook.createCellStyle();
        Font hlinkfont = workbook.createFont();
        hlinkfont.setUnderline(XSSFFont.U_SINGLE);
        hlinkfont.setColor(HSSFColor.BLUE.index);
        hlinkstyle.setFont(hlinkfont);

        // Create a Font for styling header cells
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Create a Row
        Row headerRow = sheet.createRow(0);

        // Create cells
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        // Create Cell Style for formatting Date
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

        //TODO: "Author Name", "Author Link", "Date", "Content"
        // Create Other rows and cells with employees data
        int rowNum = 1;
        for (PostDto post : posts) {
            Row row = sheet.createRow(rowNum++);


            //create hyperlink
            XSSFHyperlink link = (XSSFHyperlink) createHelper.createHyperlink(HyperlinkType.URL);
            link.setAddress(post.getPostAuthorUrl());


            //author name + link
            Cell authorCell = row.createCell(0);
            authorCell.setCellValue(post.getPostAuthor());
            authorCell.setHyperlink((XSSFHyperlink) link);
            authorCell.setCellStyle(hlinkstyle);

            Cell dateOfBirthCell = row.createCell(1);
            dateOfBirthCell.setCellValue(post.getPostTimeStamp());
            dateOfBirthCell.setCellStyle(dateCellStyle);

            row.createCell(2).setCellValue(post.getPostText());
        }

        // Resize all columns to fit the content size
       /* for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }*/

        FileOutputStream fileOut = null;
        try {
            // Write the output to a file
            fileOut = new FileOutputStream("poi-generated-file.xlsx");
            workbook.write(fileOut);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Closing the workbook
            try {
                fileOut.close();
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
