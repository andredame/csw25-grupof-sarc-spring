package br.com.sarc.csw.core.enums;

public enum StatusRecurso {
    DISPONIVEL("Disponível"),
    RESERVADO("Reservado"),
    EM_MANUTENCAO("em manutenção");

    private String descricao;

    StatusRecurso(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}