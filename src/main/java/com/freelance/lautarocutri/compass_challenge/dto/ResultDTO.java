package com.freelance.lautarocutri.compass_challenge.dto;

import com.freelance.lautarocutri.compass_challenge.entity.Contact;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

import java.util.List;

@Data
@AllArgsConstructor
public class ResultDTO {
 private List<Match> match;

 @Data
 @AllArgsConstructor
 public static class Match{
  private Contact[] duplicates;
  private int score;
 }
}
