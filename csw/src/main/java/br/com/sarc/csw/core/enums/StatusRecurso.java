package br.com.sarc.csw.core.enums;

public enum StatusRecurso {
    DISPONIVEL("Disponível"),
    EM_MANUTENCAO("em manutenção");

    private String descricao;

    StatusRecurso(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}