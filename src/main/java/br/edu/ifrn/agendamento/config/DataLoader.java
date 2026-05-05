package br.edu.ifrn.agendamento.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.edu.ifrn.agendamento.model.Aluno;
import br.edu.ifrn.agendamento.model.Categoria;
import br.edu.ifrn.agendamento.model.TipoRole;
import br.edu.ifrn.agendamento.model.Usuario;
import br.edu.ifrn.agendamento.repository.AlunoRepository;
import br.edu.ifrn.agendamento.repository.CategoriaRepository;
import br.edu.ifrn.agendamento.repository.UsuarioRepository;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner carregarDados(
            AlunoRepository alunoRepo, 
            CategoriaRepository catRepo,
            UsuarioRepository usuarioRepo,
            PasswordEncoder passwordEncoder) {
        
        return args -> {
            // 1. Inicialização de Alunos
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

            // 2. Inicialização de Categorias
            if (catRepo.count() == 0) {
                catRepo.saveAll(Arrays.asList(
                    new Categoria("DUVIDA_AULA"),       // ID 1
                    new Categoria("REVISAO_PROVA"),     // ID 2
                    new Categoria("ORIENTACAO_PROJETO") // ID 3
                ));
            }

            // 3. Inicialização de Usuários e Autoridades de Segurança
            if (usuarioRepo.count() == 0) {
                Usuario coordenador = new Usuario();
                coordenador.setUsername("admin");
                coordenador.setPassword(passwordEncoder.encode("123"));
                coordenador.setRole(TipoRole.ROLE_COORDENADOR);

                Usuario professor = new Usuario();
                professor.setUsername("prof");
                professor.setPassword(passwordEncoder.encode("123"));
                professor.setRole(TipoRole.ROLE_PROFESSOR);

                Usuario aluno = new Usuario();
                aluno.setUsername("aluno");
                aluno.setPassword(passwordEncoder.encode("123"));
                aluno.setRole(TipoRole.ROLE_ALUNO);

                usuarioRepo.saveAll(Arrays.asList(coordenador, professor, aluno));
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