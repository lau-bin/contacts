package com.freelance.lautarocutri.compass_challenge;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@ShellComponent
public class Command {

 @Autowired
 private FindDuplicatesProcess findDuplicatesProcess;

 @SuppressWarnings(value = "unused")
 @ShellMethod("Encontrar contactos duplicados")
 public String findDuplicates(
   @ShellOption(help = "Ubicacion del archivo .csv con los contactos", value = "archivo")  String filePath
 ) throws IOException {
  log.info("Buscando contactos duplicados...");
  var file = validatePath(filePath);
  var result = findDuplicatesProcess.run(file);
  String out = "Contactos duplicados:\n";
  result.getMatch().sort((a, b)->{
   if (a.getDuplicates()[0].getContactId().equalsIgnoreCase(b.getDuplicates()[0].getContactId())){
    return 0;
   }
   return 1;
  });
  var matches = result.getMatch();
  out += "Encontrados " + matches.size() + " posibles contactos duplicados.\n";
  for (int i = 0; i < matches.size(); i++){
   var dups = matches.get(i).getDuplicates();
   out += i + ") Score: " + matches.get(i).getScore() + "\n" +
     "Contact ID: " + dups[0].getContactId() + "\n" +
     "Contact ID: " + dups[1].getContactId() + "\n";
  }
  return out;
 }

 private File validatePath(String filePath) throws IOException {
  // Normalize path
  Path path = Paths.get(filePath).normalize();
  if (!Files.exists(path)) {
   throw new IOException("File does not exist: " + path);
  }
  if (!Files.isReadable(path)) {
   throw new IOException("File cannot be read: " + path);
  }
  return path.toFile();
 }
}
