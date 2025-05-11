package br.com.sarc.csw.core.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MensagemErroDTO {
    private String mensagem;
    
}
