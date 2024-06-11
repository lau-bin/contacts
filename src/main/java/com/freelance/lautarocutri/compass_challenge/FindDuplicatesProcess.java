package com.freelance.lautarocutri.compass_challenge;

import com.freelance.lautarocutri.compass_challenge.dto.ResultDTO;
import com.freelance.lautarocutri.compass_challenge.entity.Contact;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class FindDuplicatesProcess {
 public ResultDTO run(File file) throws IOException {
  try (CSVParser parser = readData(file)) {
   var dups = findDuplicates(parser);
   return new ResultDTO(dups);
  } catch (IOException e) {
   log.debug("Error in FindDuplicatesProcess", e);
   throw e;
  }
 }

 private List<ResultDTO.Match> findDuplicates(Iterable<CSVRecord> iterator){
  List<Contact> contacts = new ArrayList<>();
  List<ResultDTO.Match> matches = new ArrayList<>();
  for (var record : iterator) {
   Contact ct = new Contact(
     record.get("contactID"),
     record.get("name"),
     record.get("name1"),
     record.get("email"),
     record.get("postalZip"),
     record.get("address")
   );

   contacts.forEach(el->{
    int score = el.similar(ct);
    if (score > 0){
     matches.add(new ResultDTO.Match(new Contact[]{ct, el}, score));
    }
   });
   contacts.add(ct);
  }
  return matches;
 }
 private CSVParser readData(File file) throws IOException {
  CSVFormat format = CSVFormat.DEFAULT
    .builder()
    .setHeader()
    .setSkipHeaderRecord(true)
    .setDelimiter(",")
    .setQuote('"')
    .build();
  Reader reader = new FileReader(file);
  return new CSVParser(reader, format);
 }
}
