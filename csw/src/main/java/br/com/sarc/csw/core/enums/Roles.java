package br.com.sarc.csw.core.enums; // CORRIGIDO: Pacote correto para constantes/enums

/**
 * Classe que define as constantes para as roles do sistema.
 * Isso ajuda a evitar "magic strings" e garante consistência.
 */
public final class Roles { // 'final' para evitar herança

    public static final String ADMIN = "ADMIN";
    public static final String PROFESSOR = "PROFESSOR";
    public static final String ALUNO = "ALUNO";
    public static final String COORDENADOR = "COORDENADOR";

    // NOVO: Construtor privado para evitar que a classe seja instanciada.
    private Roles() {
        // Esta é uma classe de utilidade/constantes, não deve ser instanciada.
    }
}