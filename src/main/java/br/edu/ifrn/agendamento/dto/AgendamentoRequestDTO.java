package br.edu.ifrn.agendamento.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

public class AgendamentoRequestDTO {

    @NotNull(message = "O ID do aluno é obrigatório.")
    private Long alunoId;

    @NotEmpty(message = "Pelo menos uma categoria deve ser selecionada.")
    private List<Long> categoriasIds;

    @NotBlank
    @Size(min = 10, max = 255) // Validação obrigatória DTO
    private String motivo;

    @NotNull(message = "A data e hora são obrigatórias.")
    @Future(message = "A data deve ser no futuro.")
    private LocalDateTime dataHora;

    
    public Long getAlunoId() { return alunoId; }
    public void setAlunoId(Long alunoId) { this.alunoId = alunoId; }

    public List<Long> getCategoriasIds() { return categoriasIds; }
    public void setCategoriasIds(List<Long> categoriasIds) { this.categoriasIds = categoriasIds; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    
    //ajustando a mudança para incluir motivo
    public String getMotivo() {
        return motivo;
    }
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    
}