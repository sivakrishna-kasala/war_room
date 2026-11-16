package com.krishna.statecollect_batch.service;

import com.krishna.statecollect_batch.dto.ActorDTO;
import com.krishna.statecollect_batch.exception.ApplicationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CopyActorReportService {

    private final static Logger LOG= LogManager.getLogger(CopyActorReportService.class);


    @Value("${report.out}")
    private String reportDirectory;

    public void report(List<ActorDTO> responseActors) {

        //filename
        String fileName=filename();

        Path filePath= Paths.get(reportDirectory,fileName);

        try{

            Files.createDirectories(filePath.getParent());

            try(Workbook workbook= new XSSFWorkbook()){
                Sheet sheet=workbook.createSheet("report");

                Integer rowDataIndex=1;
                Integer rowHeadIndex=0;

                // Create header row
                Row headerRow = sheet.createRow(rowHeadIndex);

                headerRow.createCell(0).setCellValue("Actor ID");
                headerRow.createCell(1).setCellValue("Actor Name");
                headerRow.createCell(2).setCellValue("Last Update");

                // Create date style once
                CellStyle dateStyle = workbook.createCellStyle();

                dateStyle.setDataFormat(
                        workbook.createDataFormat().getFormat("dd/MM/yyyy")
                );

                for (ActorDTO act:responseActors){
                    Row header = sheet.createRow(rowDataIndex);

                    header.createCell(0, CellType.NUMERIC).setCellValue(act.getActorId());
                    header.createCell(1, CellType.STRING).setCellValue(act.getFirstName()+" "+act.getLastName());

                    Cell dateCell = header.createCell(2, CellType.NUMERIC);

                    if (act.getLastUpdate() != null) {
                        dateCell.setCellValue(
                                java.sql.Date.valueOf(act.getLastUpdate())
                        );
                        dateCell.setCellStyle(dateStyle);
                    }

                    rowDataIndex++;
                }

                try (OutputStream outputStream =
                             Files.newOutputStream(filePath)) {
                    workbook.write(outputStream);
                }
            }
            LOG.info("Excel created successfully: {}", filePath);

        }catch (Exception e){
            throw new ApplicationException(400,e.getMessage(),e);
        }
        
    }

    private static String filename() {
        StringBuilder str=new StringBuilder();
        String prefixFileName="Copy_Actor_Details";
        DateTimeFormatter format=DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss");
        String dateTime= LocalDateTime.now().format(format);

        str.append(prefixFileName);
        str.append("_");
        str.append(dateTime);
        str.append(".xlsx");

        String fileName=str.toString();

        return fileName;
    }
}
