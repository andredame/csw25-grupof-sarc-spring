package br.com.sarc.csw.modules.turma.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

import br.com.sarc.csw.modules.user.dto.AlunoResponseDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class TurmaDTO {
    private Long id;
    @NotBlank(message = "O número da turma é obrigatório")
    private String numero;

    @NotBlank(message = "O semestre é obrigatório")
    private String semestre;

    @NotNull(message = "O ID da disciplina é obrigatório")
    private Long disciplinaId;

    @NotNull(message = "O ID do professor é obrigatório")
    private UUID professorId;

    @NotBlank(message = "O horário é obrigatório")
    private String horario;

    @NotNull(message = "O número de vagas é obrigatório")
    @Min(value = 1, message = "A turma deve ter pelo menos 1 vaga")
    private Integer vagas;

    @NotNull(message = "A lista de alunos não pode ser nula")
    private List<@NotNull(message = "ID de aluno inválido") AlunoResponseDTO> alunos;
}
