package com.krishna.statecollect_batch.service;


import com.krishna.statecollect_batch.dto.ActorDTO;
import com.krishna.statecollect_batch.exception.ApplicationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

@Service
public class PrepareFileService {

    private final static Logger LOG= LogManager.getLogger(PrepareFileService.class);

    @Autowired
    private CopyActorReportService copyActorReportService;


    @Value("${report.input.ready}")
    private String readyDir;

    @Value("${report.input.work}")
    private String workDir;

    @Value("${report.input.error}")
    private String errorDir;

    @Value("${report.input.done}")
    private String doneDir;

    @Value("${report.output.done}")
    private String doneOutDir;

    public void validateAndPrepare() {

        Path readyPath= Paths.get(readyDir);
        Path workPath=Paths.get(workDir);
        Path errorPath=Paths.get(errorDir);
        Path donePath=Paths.get(doneDir);

        Path readyOutPath= Paths.get(readyDir);
        Path workOutPath=Paths.get(workDir);
        Path errorOUtPath=Paths.get(errorDir);
        Path doneOutPath=Paths.get(doneDir);

        List<ActorDTO> finalResult=new ArrayList<>();

        Map<Integer,String> expectedColumnHeaders=new HashMap<>();
        expectedColumnHeaders.put(0,"ACTOR_ID");
        expectedColumnHeaders.put(1,"FIRST_NAME");
        expectedColumnHeaders.put(2,"LAST_NAME");
        expectedColumnHeaders.put(3,"LAST_UPDATE");

        if (!Files.exists(readyPath)&&!Files.isDirectory(readyPath)){
            throw new ApplicationException(400,"Ready directory path is invalid");
        }

        try(Stream<Path> listFiles=Files.list(readyPath)){
            List<Path> excelFiles=listFiles.filter(Files::isRegularFile).filter(file->{
                String name=file.getFileName().toString().toLowerCase();
                return name.contains("actor_collection")&&(name.endsWith(".xls")||name.endsWith(".xlsx"));
            }).toList();

            if (excelFiles.isEmpty()){
                throw new ApplicationException(400,"No files found");
            }

            for(Path excelFile: excelFiles){
                Files.createDirectories(workPath);
                String inputExcelFilename=excelFile.getFileName().toString();
                Path fileInWork=moveFile(readyPath,workPath,inputExcelFilename,"WORK");

                try(Workbook workbook= WorkbookFactory.create(fileInWork.toFile())) {
                    Sheet sheet = workbook.getSheetAt(0);

                    //validate sheet name
                    if(sheet.getSheetName().equals("ACTOR_SHEET")){
                        moveFile(workPath,errorPath,inputExcelFilename,"ERROR");
                        throw new ApplicationException(400,"Invalid sheet name");
                    }
                    for (Row row : sheet) {
                        if (row.getRowNum()==0){

                            Map<Integer,String> inputColumnHeaderDetails=new HashMap<>();
                            inputColumnHeaderDetails.put(0,row.getCell(0).getStringCellValue());
                            inputColumnHeaderDetails.put(1,row.getCell(1).getStringCellValue());
                            inputColumnHeaderDetails.put(2,row.getCell(2).getStringCellValue());

                            boolean validationFailed=expectedColumnHeaders.entrySet().stream().allMatch(entry-> entry.getValue().equals(inputColumnHeaderDetails.get(entry.getKey())));

                            if (validationFailed){
                                moveFile(workPath,errorPath,inputExcelFilename,"ERROR");
                                throw new ApplicationException(400,"Header validation failed");
                            }

                            LOG.info("Header validation failed");

                        }else{
                            ActorDTO dto= new ActorDTO();

                            LOG.info("Row index is {}",row.getRowNum());

                            // Column 0 - Actor ID
                            Cell actorIdCell = row.getCell(0);

                            if (actorIdCell != null && actorIdCell.getCellType() == CellType.NUMERIC) {
                                dto.setActorId((int) actorIdCell.getNumericCellValue());
                            }

                            // Column 1 - First name and Last name
                            Cell nameCell = row.getCell(1);

                            if (nameCell != null && nameCell.getCellType() == CellType.STRING) {

                                String fullName = nameCell.getStringCellValue().trim();

                                String[] names = fullName.split("\\s+", 2);

                                if (names.length == 2) {
                                    dto.setFirstName(names[0]);
                                    dto.setLastName(names[1]);
                                }
                            }

                            // Column 2 - Last Update
                            Cell dateCell = row.getCell(2);
                            if (dateCell != null
                                    && dateCell.getCellType() == CellType.NUMERIC
                                    && DateUtil.isCellDateFormatted(dateCell)) {

                                dto.setLastUpdate(
                                        dateCell.getDateCellValue()
                                                .toInstant()
                                                .atZone(ZoneId.systemDefault())
                                                .toLocalDate()
                                );
                            }

                            // Add DTO to final result
                            if (dto.getLastName()!=null&&dto.getFirstName()!=null&&dto.getActorId()!=null){
                                LOG.info("Data :{}",dto);
                                finalResult.add(dto);
                            }

                        }
                    }
                }catch (Exception e){
                    moveFile(workPath,errorPath,inputExcelFilename,"ERROR");
                    throw new ApplicationException(400,e.getMessage(),e.getCause());
                }
                Path fileInDone=moveFile(workPath,donePath,inputExcelFilename,"DONE");
                LOG.info("File moved to {}",fileInDone);

                LOG.info("data received :{}",finalResult);

                // step:2
                //filename
                String fileName=filename();

                Path filePath= Paths.get(doneOutDir,fileName);

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

                        dateStyle.setDataFormat(workbook.createDataFormat().getFormat("dd/MM/yyyy"));

                        for (ActorDTO act:finalResult){
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
                            // workbook.write(outputStream);
                        }
                    }
                    LOG.info("Excel created successfully: {}", filePath);

                }catch (Exception e){
                    throw new ApplicationException(400,e.getMessage(),e);
                }
            }
        }catch (ApplicationException ae){
            LOG.error("Error occurred :{}",ae.getMessage());
        } catch (Exception e) {
            throw new ApplicationException(400,e.getMessage(),e.getCause());
        }


    }


    private Path moveFile(Path source,Path destination, String filename, String mode) throws IOException {

        Path sourceFile = source.resolve(filename);
        LOG.info("Source path: {}", sourceFile);
        Path destinationFile = destination.resolve(filename);
        LOG.info("Destination path: {}", destinationFile);

        switch (mode.toUpperCase()) {

            case "ERROR":
                LOG.info("Move the file from Work directory to ERROR directory :{} ----> {}",source,destination);
                break;

            case "DONE":
                LOG.info("Move the file from Work directory to DONE directory :{} ----> {}",source,destination);
                break;

            case "WORK":
                LOG.info("Move the file from READY directory to WORK directory :{} ----> {}",source,destination);
                break;

            default:
                LOG.error("File move mode is {}",mode);
                throw new IllegalArgumentException("Invalid status: " + mode);
        }

        Files.move(sourceFile, destinationFile);

        LOG.info("File moved successfully");

        return destinationFile;
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