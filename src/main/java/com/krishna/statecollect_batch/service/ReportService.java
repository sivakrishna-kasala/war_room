package com.krishna.statecollect_batch.service;

import com.krishna.statecollect_batch.dto.ActorDTO;
import com.krishna.statecollect_batch.exception.ApplicationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ReportService {

    private final static Logger LOG= LogManager.getLogger(ReportService.class);

    @Value("${report.out}")
    private String reportPath;

    public void generateReport(List<ActorDTO> list) {

        LOG.info("Report path : {}",reportPath);
        LOG.info("Data received :{}",list);

        String fileName = getFileName();
        LOG.info("Report path : {}",reportPath);

        LOG.info("file name is {}",fileName);

        Path filePath= Paths.get(reportPath,fileName);

        LOG.info("file path is {}",filePath);

        try{
            try(Workbook workbook=new XSSFWorkbook()){

                Files.createDirectories(filePath.getParent());

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

                for (ActorDTO act:list){
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
        } catch (Exception e){
            throw new ApplicationException(500,e);
        }
    }

    private static @NonNull String getFileName() {
        String fileNamePrefix="actor_collection";
        LocalDateTime dateTime=LocalDateTime.now();
        DateTimeFormatter dateTimeFormat=DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss");
        String dateResult=dateTime.format(dateTimeFormat);

        LOG.info("Date now is {}",dateTime);

        StringBuilder str=new StringBuilder();
        str.append(fileNamePrefix);
        str.append("_");
        str.append("before");
        str.append("_");
        str.append(dateResult);
        str.append(".xlsx");

        return str.toString();
    }
}
