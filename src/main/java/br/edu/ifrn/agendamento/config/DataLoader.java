package br.edu.ifrn.agendamento.config;

import br.edu.ifrn.agendamento.model.Aluno;
import br.edu.ifrn.agendamento.model.Categoria;
import br.edu.ifrn.agendamento.repository.AlunoRepository;
import br.edu.ifrn.agendamento.repository.CategoriaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner carregarDados(AlunoRepository alunoRepo, CategoriaRepository catRepo) {
        return args -> {
            // Garante a inserção apenas se a tabela estiver vazia
            if (alunoRepo.count() == 0) {
                List<Aluno> alunos = Arrays.asList(
                        criarAluno("Maria Silva", "202310140400"),
                        criarAluno("João Pedro", "202310140401"),
                        criarAluno("Ana Beatriz", "202310140402"),
                        criarAluno("Carlos Eduardo", "202310140403"),
                        criarAluno("Mariana Costa", "202310140404"),
                        criarAluno("Lucas Fernandes", "202310140405"),
                        criarAluno("Júlia Alves", "202310140406"),
                        criarAluno("Rafael Souza", "202310140407"),
                        criarAluno("Beatriz Lima", "202310140408"),
                        criarAluno("Gustavo Gomes", "202310140409")
                );
                alunoRepo.saveAll(alunos);
            }

            if (catRepo.count() == 0) {
                catRepo.saveAll(Arrays.asList(
                    new Categoria("DUVIDA_AULA"),       // ID 1
                    new Categoria("REVISAO_PROVA"),     // ID 2
                    new Categoria("ORIENTACAO_PROJETO") // ID 3
                ));
            }
        };
    }

    // Método utilitário para encapsular a criação da entidade Aluno
    private Aluno criarAluno(String nome, String matricula) {
        Aluno aluno = new Aluno();
        aluno.setNome(nome);
        aluno.setMatricula(matricula);
        return aluno;
    }
}