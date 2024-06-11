package com.freelance.lautarocutri.compass_challenge.entity;

import lombok.Data;
import org.apache.commons.text.similarity.LevenshteinDistance;

@Data
public class Contact {
 private final String contactId;
 private final String name;
 private final String name1;
 private final String email;
 private final String zip;
 private final String address;

 public String print(){
  return "contactId: " + contactId + "\n" +
    "name: " + name + "\n" +
    "name1: " + name1 + "\n" +
    "email: " + email + "\n" +
    "zip: " + zip + "\n" +
    "address: " + address;
 }

 public int similar(Contact ct){
  LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

  if (ct.getContactId().equalsIgnoreCase(contactId)){
   return 1;
  }
  int nameDst = levenshteinDistance.apply(ct.getName().toLowerCase(), name.toLowerCase());
  int name1Dst = levenshteinDistance.apply(ct.getName1().toLowerCase(), name1.toLowerCase());
  int emailDst = levenshteinDistance.apply(ct.getEmail().toLowerCase(), email.toLowerCase());
  int zipDst = levenshteinDistance.apply(ct.getZip().toLowerCase(), zip.toLowerCase());
  int addrDst = levenshteinDistance.apply(ct.getAddress().toLowerCase(), address.toLowerCase());

  int personalScore = (nameDst + name1Dst);
  int addrAscore = zipDst + addrDst;

  if (emailDst == 0){
   return 0;
  }
  if (personalScore == 0){
   return emailDst;
  }
  if (emailDst < 2 && personalScore < 8){
   return emailDst + personalScore;
  }
  if (addrAscore < 6){
   if (emailDst < 4 && personalScore < 16){
    return  emailDst + personalScore + addrAscore;
   }
  }
  return -1;
 }
}
